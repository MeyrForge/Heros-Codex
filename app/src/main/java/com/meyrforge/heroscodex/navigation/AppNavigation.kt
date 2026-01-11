package com.meyrforge.heroscodex.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.meyrforge.heroscodex.feature_main_menu.presentation.screen.MainMenuScreen
import com.meyrforge.heroscodex.feature_name_generator.presentation.screen.NameGeneratorScreen
import com.meyrforge.heroscodex.feature_saved_heroes.presentation.screen.SavedHeroesScreen

@Composable
fun AppNavigation(innerPadding: PaddingValues) {
  val navController = rememberNavController()
  NavHost(
    navController = navController, 
    startDestination = Screen.MainMenu.route,
    modifier = Modifier.padding(innerPadding)
    ) {
    composable(Screen.MainMenu.route) {
      MainMenuScreen(navController = navController)
    }
    composable(Screen.NameGenerator.route) {
      NameGeneratorScreen()
    }
    composable(Screen.SavedHeroes.route){
      SavedHeroesScreen(navController = navController)
    }
    composable("edit_hero/{heroId}") { backStackEntry ->
      val heroId = backStackEntry.arguments?.getString("heroId")
      com.meyrforge.heroscodex.feature_saved_heroes.presentation.screen.EditHeroTraitsScreen(
        heroId = heroId ?: "",
        navController = navController
      )
    }
  }
}
