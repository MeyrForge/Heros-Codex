package com.meyrforge.heroscodex.feature_name_generator.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meyrforge.heroscodex.feature_name_generator.domain.model.Background
import com.meyrforge.heroscodex.feature_name_generator.domain.model.Gender
import com.meyrforge.heroscodex.feature_name_generator.domain.model.HeroName
import com.meyrforge.heroscodex.feature_name_generator.domain.model.Race
import com.meyrforge.heroscodex.feature_name_generator.domain.usecase.GenerateNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NameGeneratorViewModel @Inject constructor(
  private val generateNameUseCase: GenerateNameUseCase
) : ViewModel() {

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
    if (_uiState.value.currentTokens <= 0) return

    _uiState.update { it.copy(isLoading = true) }

    viewModelScope.launch {
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
              currentTokens = it.currentTokens - 1,
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

  fun clearError() {
    _uiState.update { it.copy(error = null) }
  }
}

data class NameGeneratorUiState(
  val selectedRace: Race = Race.HUMAN,
  val selectedGender: Gender = Gender.MALE,
  val selectedBackground: Background = Background.ACOLYTE,
  val generatedName: HeroName? = null,
  val currentTokens: Int = 10,
  val maxTokens: Int = 10,
  val isLoading: Boolean = false,
  val error: String? = null
)
