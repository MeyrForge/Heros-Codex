package com.meyrforge.heroscodex.feature_saved_heroes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meyrforge.heroscodex.core.domain.model.SavedHero
import com.meyrforge.heroscodex.feature_saved_heroes.domain.usecase.DeleteSavedHeroUseCase
import com.meyrforge.heroscodex.feature_saved_heroes.domain.usecase.GetSavedHeroesUseCase
import com.meyrforge.heroscodex.feature_saved_heroes.domain.usecase.UpdateSavedHeroUseCase
import com.meyrforge.heroscodex.feature_name_generator.domain.usecase.GenerateNameUseCase
import com.meyrforge.heroscodex.core.domain.repository.TokensRepository
import com.meyrforge.heroscodex.core.domain.model.Background
import com.meyrforge.heroscodex.core.domain.model.Gender
import com.meyrforge.heroscodex.core.domain.model.Race
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SavedHeroesViewModel @Inject constructor(
    private val getSavedHeroesUseCase: GetSavedHeroesUseCase,
    private val deleteSavedHeroUseCase: DeleteSavedHeroUseCase,
    private val updateSavedHeroUseCase: UpdateSavedHeroUseCase,
    private val generateNameUseCase: GenerateNameUseCase,
    private val tokensRepository: TokensRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SavedHeroesUiState())
    val uiState: StateFlow<SavedHeroesUiState> = _uiState.asStateFlow()

    init {
        loadHeroes()
    }

    init {
        viewModelScope.launch {
            tokensRepository.currentTokensFlow().collect { current ->
                _uiState.update { it.copy(currentTokens = current) }
            }
        }

        viewModelScope.launch {
            tokensRepository.maxTokensFlow().collect { max ->
                _uiState.update { it.copy(maxTokens = max) }
            }
        }

        viewModelScope.launch {
            tokensRepository.nextRegenRemainingFlow().collect { remaining ->
                _uiState.update { it.copy(nextRegenRemainingMs = remaining) }
            }
        }
    }

    private fun loadHeroes() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = getSavedHeroesUseCase()
            result.fold(
                onSuccess = { heroes ->
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    heroes = heroes,
                                    error = null
                                )
                            }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "An unknown error occurred"
                        )
                    }
                }
            )
        }
    }

    fun onHeroClicked(heroId: UUID) {
        _uiState.update {
            if (it.expandedHeroId == heroId) {
                it.copy(expandedHeroId = null)
            } else {
                it.copy(expandedHeroId = heroId)
            }
        }
    }

    fun onDeleteHeroClicked(heroId: UUID) {
        viewModelScope.launch {
            val result = deleteSavedHeroUseCase(heroId)
            result.fold(
                onSuccess = {
                    loadHeroes() // Reload heroes after deletion
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            error = error.message ?: "An unknown error occurred"
                        )
                    }
                }
            )
        }
    }

    fun onGenerateNameForHero(heroId: UUID) {
        viewModelScope.launch {
            // find hero
            val hero = _uiState.value.heroes.find { it.id == heroId } ?: return@launch

            // consume token
            val consumed = tokensRepository.consume(1).getOrElse { false }
            if (!consumed) {
                _uiState.update { it.copy(error = "No tienes tokens suficientes") }
                return@launch
            }

            // show saving overlay (reuse isSaving semantics)
            _uiState.update { it.copy(isSaving = true) }

            // keep same delay as NameGenerator
            kotlinx.coroutines.delay(1220L)

            val result = generateNameUseCase(
                gender = hero.gender,
                race = hero.race,
                background = hero.background
            )

            result.fold(
                onSuccess = { heroName ->
                    // update saved hero with new name
                    val oldName = hero.name
                    val updated = hero.copy(name = heroName.name)
                    val updateResult = updateSavedHeroUseCase(updated)
                    updateResult.fold(
                        onSuccess = {
                            loadHeroes()
                            _uiState.update { st -> st.copy(expandedHeroId = updated.id, lastGenerationMessage = "$oldName ahora se llama ${updated.name}", lastUpdatedHeroId = updated.id) }
                        },
                        onFailure = { err -> _uiState.update { it.copy(error = err.message ?: "Error al actualizar héroe") } }
                    )
                },
                onFailure = { err -> _uiState.update { it.copy(error = err.message ?: "Error al generar nombre") } }
            )

            _uiState.update { it.copy(isSaving = false) }
        }
    }

    fun onUpdateHeroTraits(heroId: UUID, race: Race, gender: Gender, background: Background) {
        viewModelScope.launch {
            val hero = _uiState.value.heroes.find { it.id == heroId } ?: return@launch

            // if no change, skip
            if (hero.race == race && hero.gender == gender && hero.background == background) return@launch

            val consumed = tokensRepository.consume(1).getOrElse { false }
            if (!consumed) {
                _uiState.update { it.copy(error = "No tienes tokens suficientes") }
                return@launch
            }

            _uiState.update { it.copy(isLoading = true) }

            val updated = hero.copy(race = race, gender = gender, background = background)
            val result = updateSavedHeroUseCase(updated)
            result.fold(
                onSuccess = {
                    loadHeroes()
                    _uiState.update { it.copy(expandedHeroId = updated.id, lastUpdatedHeroId = updated.id) }
                },
                onFailure = { err -> _uiState.update { it.copy(error = err.message ?: "Error al actualizar héroe") } }
            )

            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun requestTokensNow() {
        viewModelScope.launch {
            tokensRepository.currentTokensFlow().collect()
            tokensRepository.maxTokensFlow().collect()
            tokensRepository.nextRegenRemainingFlow().collect()
        }
    }

    fun clearLastGenerationMessage() {
        _uiState.update { it.copy(lastGenerationMessage = null) }
    }

    fun clearLastUpdatedHero() {
        _uiState.update { it.copy(lastUpdatedHeroId = null) }
    }
}

data class SavedHeroesUiState(
    val isLoading: Boolean = false,
    val heroes: List<SavedHero> = emptyList(),
    val error: String? = null,
    val expandedHeroId: UUID? = null,
    val currentTokens: Int = -1,
    val maxTokens: Int = -1,
    val nextRegenRemainingMs: Long = -1L,
    val isSaving: Boolean = false,
    val lastGenerationMessage: String? = null,
    val lastUpdatedHeroId: UUID? = null
)
