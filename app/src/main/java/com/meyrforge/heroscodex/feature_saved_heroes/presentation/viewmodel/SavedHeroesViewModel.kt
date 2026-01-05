package com.meyrforge.heroscodex.feature_saved_heroes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meyrforge.heroscodex.core.domain.model.SavedHero
import com.meyrforge.heroscodex.feature_saved_heroes.domain.usecase.GetSavedHeroesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SavedHeroesViewModel @Inject constructor(
    private val getSavedHeroesUseCase: GetSavedHeroesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SavedHeroesUiState())
    val uiState: StateFlow<SavedHeroesUiState> = _uiState.asStateFlow()

    init {
        loadHeroes()
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
                            heroes = heroes
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
}

data class SavedHeroesUiState(
    val isLoading: Boolean = false,
    val heroes: List<SavedHero> = emptyList(),
    val error: String? = null,
    val expandedHeroId: UUID? = null
)
