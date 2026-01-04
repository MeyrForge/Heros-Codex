package com.meyrforge.heroscodex.feature_main_menu.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.meyrforge.heroscodex.core.ui.theme.DarkBackgroundEnd
import com.meyrforge.heroscodex.core.ui.theme.DarkBackgroundStart
import com.meyrforge.heroscodex.core.ui.theme.MagicalGold
import com.meyrforge.heroscodex.feature_main_menu.domain.model.MenuOption
import com.meyrforge.heroscodex.feature_main_menu.presentation.viewmodel.MainMenuViewModel

@Composable
fun MainMenuScreen(
  modifier: Modifier = Modifier,
  navController: NavController,
  viewModel: MainMenuViewModel = hiltViewModel()
) {
  LaunchedEffect(Unit) {
    viewModel.navigationEvent.collect { screen ->
      navController.navigate(screen.route)
    }
  }

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(
        Brush.radialGradient(
          colors = listOf(DarkBackgroundStart, DarkBackgroundEnd),
          radius = 1200f
        )
      )
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Spacer(Modifier.weight(1f))
      Text(
        text = "Hero's Codex",
        style = MaterialTheme.typography.headlineLarge,
        color = MagicalGold,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
      )
      Spacer(modifier = Modifier.height(96.dp))
      Column(
        modifier = Modifier.fillMaxWidth(0.75f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        Button(
          onClick = { viewModel.onMenuOptionSelected(MenuOption.NAME_GENERATOR) },
          modifier = Modifier.fillMaxWidth(),
          colors = ButtonDefaults.buttonColors(
            containerColor = MagicalGold,
            contentColor = DarkBackgroundEnd
          ),
          shape = RoundedCornerShape(12.dp)
        ) {
          Text(
            text = "Generar Nombre",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
          )
        }
        Button(
          onClick = { viewModel.onMenuOptionSelected(MenuOption.HERO_GENERATOR) },
          modifier = Modifier.fillMaxWidth(),
          colors = ButtonDefaults.buttonColors(
            containerColor = MagicalGold,
            contentColor = DarkBackgroundEnd
          ),
          shape = RoundedCornerShape(12.dp)
        ) {
          Text(
            text = "Generar Héroe",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
          )
        }
        Button(
          onClick = { viewModel.onMenuOptionSelected(MenuOption.MY_HEROES) },
          modifier = Modifier.fillMaxWidth(),
          colors = ButtonDefaults.buttonColors(
            containerColor = MagicalGold,
            contentColor = DarkBackgroundEnd
          ),
          shape = RoundedCornerShape(12.dp)
        ) {
          Text(
            text = "Mis Héroes",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
          )
        }
      }
      Spacer(Modifier.weight(1.5f))
    }
  }
}
