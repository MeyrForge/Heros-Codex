package com.meyrforge.heroscodex.feature_name_generator.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.meyrforge.heroscodex.feature_name_generator.domain.model.Background
import com.meyrforge.heroscodex.feature_name_generator.domain.model.Gender
import com.meyrforge.heroscodex.feature_name_generator.domain.model.Race
import com.meyrforge.heroscodex.feature_name_generator.presentation.components.GeneratedNameDisplay
import com.meyrforge.heroscodex.feature_name_generator.presentation.components.NameGeneratorTopBar
import com.meyrforge.heroscodex.feature_name_generator.presentation.components.OptionSelector
import com.meyrforge.heroscodex.feature_name_generator.presentation.components.SelectorOption
import com.meyrforge.heroscodex.feature_name_generator.presentation.viewmodel.NameGeneratorViewModel

@Composable
fun NameGeneratorScreen(
  modifier: Modifier = Modifier,
  viewModel: NameGeneratorViewModel = hiltViewModel()
) {
  val uiState by viewModel.uiState.collectAsState()
  val snackbarHostState = remember { SnackbarHostState() }

  LaunchedEffect(uiState.error) {
    uiState.error?.let { error ->
      snackbarHostState.showSnackbar(error)
      viewModel.clearError()
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
  ) {
    NameGeneratorTopBar(
      currentTokens = uiState.currentTokens,
      maxTokens = uiState.maxTokens
    )

    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
      OptionSelector(
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
        selectedOption = uiState.selectedRace,
        onOptionSelected = viewModel::onRaceSelected,
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

      OptionSelector(
        title = "Sexo",
        options = listOf(
          SelectorOption("male", "Hombre"),
          SelectorOption("female", "Mujer"),
          SelectorOption("neutral", "Neutro")
        ),
        selectedOption = uiState.selectedGender,
        onOptionSelected = viewModel::onGenderSelected,
        optionMapper = { option ->
          when (option.id) {
            "male" -> Gender.MALE
            "female" -> Gender.FEMALE
            "neutral" -> Gender.NEUTRAL
            else -> Gender.MALE
          }
        }
      )

      OptionSelector(
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
        selectedOption = uiState.selectedBackground,
        onOptionSelected = viewModel::onBackgroundSelected,
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

      GeneratedNameDisplay(
        generatedName = uiState.generatedName?.name ?: "",
        onGenerateClick = viewModel::generateName
      )

      Button(
        onClick = {
          // TODO: Implement save functionality
        },
        modifier = Modifier.fillMaxWidth(),
        enabled = uiState.generatedName != null && !uiState.isLoading
      ) {
        Text(
          text = "Guardar Nombre",
          style = MaterialTheme.typography.labelLarge
        )
      }
    }

    SnackbarHost(hostState = snackbarHostState)
  }
}
