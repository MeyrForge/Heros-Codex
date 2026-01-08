package com.meyrforge.heroscodex.feature_name_generator.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meyrforge.heroscodex.core.domain.model.Background
import com.meyrforge.heroscodex.core.domain.model.Gender
import com.meyrforge.heroscodex.core.domain.usecase.SaveNameUseCase
import com.meyrforge.heroscodex.feature_name_generator.domain.model.HeroName
import com.meyrforge.heroscodex.core.domain.model.Race
import com.meyrforge.heroscodex.feature_name_generator.domain.usecase.GenerateNameUseCase
import com.meyrforge.heroscodex.core.domain.repository.TokensRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class NameGeneratorViewModel @Inject constructor(
  private val generateNameUseCase: GenerateNameUseCase,
  private val saveNameUseCase: SaveNameUseCase,
  private val tokensRepository: TokensRepository
) : ViewModel() {

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

  private val _uiState = MutableStateFlow(NameGeneratorUiState())
  val uiState: StateFlow<NameGeneratorUiState> = _uiState.asStateFlow()

  fun onRaceSelected(race: Race) {
    _uiState.update { it.copy(selectedRace = race) }
  }

  fun onGenderSelected(gender: Gender) {
    _uiState.update { it.copy(selectedGender = gender) }
  }

  fun onBackgroundSelected(background: Background) {
    _uiState.update { it.copy(selectedBackground = background) }
  }

  fun generateName() {
    viewModelScope.launch {
      // try to consume a token from repository
      val consumeResult = tokensRepository.consume(1)
      val consumed = consumeResult.getOrElse { false }
      if (!consumed) {
        _uiState.update { it.copy(error = "No tienes tokens suficientes") }
        return@launch
      }

      _uiState.update { it.copy(isLoading = true) }

      delay(1220L)
      val result = generateNameUseCase(
        gender = _uiState.value.selectedGender,
        race = _uiState.value.selectedRace,
        background = _uiState.value.selectedBackground
      )

      result.fold(
        onSuccess = { heroName ->
          _uiState.update {
            it.copy(
              generatedName = heroName,
              isLoading = false,
              error = null
            )
          }
        },
        onFailure = { exception ->
          _uiState.update {
            it.copy(
              isLoading = false,
              error = exception.message ?: "Error desconocido"
            )
          }
        }
      )
    }
  }

  fun saveGeneratedName() {
    val nameToSave = _uiState.value.generatedName ?: return

    _uiState.update { it.copy(isSaving = true) }

    viewModelScope.launch {
      delay(1220L)
      val result = saveNameUseCase(nameToSave)

      result.fold(
        onSuccess = {
          _uiState.update {
            it.copy(
              generatedName = null,
              isSaving = false,
              error = null
            )
          }
        },
        onFailure = { exception ->
          _uiState.update {
            it.copy(
              isSaving = false,
              error = exception.message ?: "Error al guardar el nombre"
            )
          }
        }
      )
    }
  }

  fun clearError() {
    _uiState.update { it.copy(error = null) }
  }

  fun requestTokensNow() {
    viewModelScope.launch {
      // collect first emissions to force repository computations while UI shows loader
      tokensRepository.currentTokensFlow().first()
      tokensRepository.maxTokensFlow().first()
      tokensRepository.nextRegenRemainingFlow().first()
    }
  }
}

data class NameGeneratorUiState(
  val selectedRace: Race = Race.DRAGONBORN,
  val selectedGender: Gender = Gender.MALE,
  val selectedBackground: Background = Background.ACOLYTE,
  val generatedName: HeroName? = null,
  val currentTokens: Int = -1,
  val maxTokens: Int = -1,
  val nextRegenRemainingMs: Long = -1L,
  val isLoading: Boolean = false,
  val isSaving: Boolean = false,
  val error: String? = null
)
