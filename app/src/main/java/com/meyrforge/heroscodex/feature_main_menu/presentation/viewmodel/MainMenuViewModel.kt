package com.meyrforge.heroscodex.feature_main_menu.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meyrforge.heroscodex.navigation.Screen
import com.meyrforge.heroscodex.feature_main_menu.domain.model.MenuOption
import com.meyrforge.heroscodex.feature_main_menu.domain.usecase.NavigateToMenuOptionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor(
  private val navigateToMenuOptionUseCase: NavigateToMenuOptionUseCase
) : ViewModel() {

  private val _navigationEvent = MutableSharedFlow<Screen>()
  val navigationEvent = _navigationEvent.asSharedFlow()

  fun onMenuOptionSelected(option: MenuOption) {
    viewModelScope.launch {
      navigateToMenuOptionUseCase(option)?.let {
        _navigationEvent.emit(it)
      }
    }
  }
}
