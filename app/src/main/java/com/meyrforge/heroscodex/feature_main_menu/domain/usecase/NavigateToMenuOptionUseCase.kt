package com.meyrforge.heroscodex.feature_main_menu.domain.usecase

import com.meyrforge.heroscodex.navigation.Screen
import com.meyrforge.heroscodex.feature_main_menu.domain.model.MenuOption
import javax.inject.Inject

class NavigateToMenuOptionUseCase @Inject constructor() {

  operator fun invoke(option: MenuOption): Screen? {
    return when (option) {
      MenuOption.NAME_GENERATOR -> Screen.NameGenerator
      MenuOption.HERO_GENERATOR -> null // TODO: Implement when hero generator feature is ready
      MenuOption.MY_HEROES -> null // TODO: Implement when my heroes feature is ready
    }
  }
}
