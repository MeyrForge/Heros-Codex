package com.meyrforge.heroscodex.navigation

sealed class Screen(val route: String) {
  object MainMenu : Screen("main_menu")
  object NameGenerator : Screen("name_generator")
}
