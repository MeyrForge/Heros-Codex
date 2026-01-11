package com.meyrforge.heroscodex.feature_saved_heroes.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.meyrforge.heroscodex.core.ui.theme.DarkBackgroundEnd
import com.meyrforge.heroscodex.core.ui.theme.DarkBackgroundStart
import com.meyrforge.heroscodex.core.ui.theme.MagicalGold
import com.meyrforge.heroscodex.feature_name_generator.presentation.components.CompactOptionSelector
import com.meyrforge.heroscodex.feature_name_generator.presentation.components.HorizontalOptionSelector
import com.meyrforge.heroscodex.feature_name_generator.presentation.model.SelectorOption
import com.meyrforge.heroscodex.feature_saved_heroes.presentation.viewmodel.SavedHeroesViewModel
import com.meyrforge.heroscodex.core.domain.model.Race
import com.meyrforge.heroscodex.core.domain.model.Gender
import com.meyrforge.heroscodex.core.domain.model.Background
import com.meyrforge.heroscodex.navigation.Screen
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import com.meyrforge.heroscodex.core.ui.theme.ParchmentText

@Composable
fun EditHeroTraitsScreen(
  heroId: String,
  viewModel: SavedHeroesViewModel = hiltViewModel(),
  navController: NavController? = null
) {
  val uiState by viewModel.uiState.collectAsState()
  val hero = uiState.heroes.find { it.id.toString() == heroId } ?: return

  var selectedRace by remember { mutableStateOf(hero.race) }
  var selectedGender by remember { mutableStateOf(hero.gender) }
  var selectedBackground by remember { mutableStateOf(hero.background) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(
        Brush.radialGradient(
          colors = listOf(DarkBackgroundStart, DarkBackgroundEnd),
          radius = 1200f
        )
      )
      .verticalScroll(rememberScrollState())
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(20.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.End
    ) {
      com.meyrforge.heroscodex.feature_name_generator.presentation.components.TokenCounter(
        currentTokens = uiState.currentTokens,
        maxTokens = uiState.maxTokens,
        nextRegenRemainingMs = uiState.nextRegenRemainingMs,
        onRequestTokens = viewModel::requestTokensNow
      )
    }
    Text(
      text = hero.name,
      style = MaterialTheme.typography.headlineSmall,
      color = MagicalGold,
      fontWeight = FontWeight.Bold,
      fontSize = 28.sp
    )

    HorizontalOptionSelector(
      title = "Raza",
      options = listOf(
        SelectorOption("dragonborn", "Dracónido"),
        SelectorOption("elf", "Elfo"),
        SelectorOption("dwarf", "Enano"),
        SelectorOption("gnome", "Gnomo"),
        SelectorOption("human", "Humano"),
        SelectorOption("halfling", "Mediano"),
        SelectorOption("half_elf", "Semielfo"),
        SelectorOption("half_orc", "Semiorco"),
        SelectorOption("tiefling", "Tiefling")
      ),
      selectedOption = selectedRace,
      onOptionSelected = { race -> selectedRace = race },
      optionMapper = { option ->
        when (option.id) {
          "dragonborn" -> Race.DRAGONBORN
          "dwarf" -> Race.DWARF
          "elf" -> Race.ELF
          "gnome" -> Race.GNOME
          "half_elf" -> Race.HALF_ELF
          "half_orc" -> Race.HALF_ORC
          "halfling" -> Race.HALFLING
          "human" -> Race.HUMAN
          "tiefling" -> Race.TIEFLING
          else -> Race.HUMAN
        }
      }
    )

    CompactOptionSelector(
      title = "Sexo",
      options = listOf(
        SelectorOption("male", "Hombre"),
        SelectorOption("female", "Mujer"),
        SelectorOption("neutral", "Neutro")
      ),
      selectedOption = selectedGender,
      onOptionSelected = { gender -> selectedGender = gender },
      optionMapper = { option ->
        when (option.id) {
          "male" -> Gender.MALE
          "female" -> Gender.FEMALE
          "neutral" -> Gender.NEUTRAL
          else -> Gender.MALE
        }
      }
    )

    HorizontalOptionSelector(
      title = "Trasfondo",
      options = listOf(
        SelectorOption("acolyte", "Acólito"),
        SelectorOption("guild_artisan", "Artesano Gremial"),
        SelectorOption("entertainer", "Artista"),
        SelectorOption("charlatan", "Charlatán"),
        SelectorOption("criminal", "Criminal"),
        SelectorOption("hermit", "Ermitaño"),
        SelectorOption("outlander", "Forastero"),
        SelectorOption("folk_hero", "Héroe del Pueblo"),
        SelectorOption("urchin", "Huérfano"),
        SelectorOption("sailor", "Marinero"),
        SelectorOption("noble", "Noble"),
        SelectorOption("sage", "Sabio"),
        SelectorOption("soldier", "Soldado")
      ),
      selectedOption = selectedBackground,
      onOptionSelected = { background -> selectedBackground = background },
      optionMapper = { option ->
        when (option.id) {
          "acolyte" -> Background.ACOLYTE
          "charlatan" -> Background.CHARLATAN
          "criminal" -> Background.CRIMINAL
          "entertainer" -> Background.ENTERTAINER
          "folk_hero" -> Background.FOLK_HERO
          "guild_artisan" -> Background.GUILD_ARTISAN
          "hermit" -> Background.HERMIT
          "noble" -> Background.NOBLE
          "outlander" -> Background.OUTLANDER
          "sage" -> Background.SAGE
          "sailor" -> Background.SAILOR
          "soldier" -> Background.SOLDIER
          "urchin" -> Background.URCHIN
          else -> Background.ACOLYTE
        }
      }
    )

    val changed = selectedRace != hero.race || selectedGender != hero.gender || selectedBackground != hero.background

    // Show hint when traits have not changed
    if (!changed) {
      Text(
        text = "Selecciona rasgos diferentes para editar a tu héroe",
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
      )
    }

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      // Cancel button
      Button(
        onClick = { navController?.popBackStack() },
        modifier = Modifier.weight(1f),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(12.dp)
      ) {
        Text(text = "Cancelar", color = ParchmentText)
      }

      Button(
        onClick = {
          viewModel.onUpdateHeroTraits(hero.id, selectedRace, selectedGender, selectedBackground)
        },
        enabled = changed,
        modifier = Modifier.weight(1f),
        colors = ButtonDefaults.buttonColors(
          containerColor = MagicalGold,
          contentColor = MaterialTheme.colorScheme.onBackground,
          disabledContainerColor = MagicalGold.copy(alpha = 0.3f),
          disabledContentColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(12.dp)
      ) {
        Text(
          text = "Confirmar",
          color = Color.Black,
          fontWeight = FontWeight.Bold,
        )
      }
    }
    // after update completes, navigate back to main menu
    androidx.compose.runtime.LaunchedEffect(uiState.lastUpdatedHeroId) {
      if (uiState.lastUpdatedHeroId != null && uiState.lastUpdatedHeroId.toString() == heroId) {
        navController?.navigate(Screen.MainMenu.route)
        viewModel.clearLastUpdatedHero()
      }
    }
  }
}
