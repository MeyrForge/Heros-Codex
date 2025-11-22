package com.meyrforge.heroscodex.feature_name_generator.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.meyrforge.heroscodex.feature_name_generator.domain.model.Background
import com.meyrforge.heroscodex.feature_name_generator.domain.model.Gender
import com.meyrforge.heroscodex.feature_name_generator.domain.model.Race
import com.meyrforge.heroscodex.feature_name_generator.presentation.components.GeneratedNameDisplay
import com.meyrforge.heroscodex.feature_name_generator.presentation.components.NameGeneratorTopBar
import com.meyrforge.heroscodex.feature_name_generator.presentation.components.OptionSelector
import com.meyrforge.heroscodex.feature_name_generator.presentation.components.SelectorOption

@Composable
fun NameGeneratorScreen(
  modifier: Modifier = Modifier
) {
  var selectedRace by remember { mutableStateOf(Race.HUMAN) }
  var selectedGender by remember { mutableStateOf(Gender.MALE) }
  var selectedBackground by remember { mutableStateOf(Background.ACOLYTE) }
  var generatedName by remember { mutableStateOf("") }
  var currentTokens by remember { mutableStateOf(10) }
  val maxTokens = 10

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
  ) {
    NameGeneratorTopBar(
      currentTokens = currentTokens,
      maxTokens = maxTokens
    )

    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
      // Race selector
      OptionSelector(
        title = "Raza",
        options = listOf(
          SelectorOption("human", "Humano"),
          SelectorOption("elf", "Elfo"),
          SelectorOption("dwarf", "Enano")
        ),
        selectedOption = selectedRace,
        onOptionSelected = { selectedRace = it },
        optionMapper = { option ->
          when (option.id) {
            "human" -> Race.HUMAN
            "elf" -> Race.ELF
            "dwarf" -> Race.DWARF
            else -> Race.HUMAN
          }
        }
      )

      // Sex selector
      OptionSelector(
        title = "Sexo",
        options = listOf(
          SelectorOption("male", "Hombre"),
          SelectorOption("female", "Mujer"),
          SelectorOption("neutral", "Neutro")
        ),
        selectedOption = selectedGender,
        onOptionSelected = { selectedGender = it },
        optionMapper = { option ->
          when (option.id) {
            "male" -> Gender.MALE
            "female" -> Gender.FEMALE
            "neutral" -> Gender.NEUTRAL
            else -> Gender.MALE
          }
        }
      )

      // Background selector
      OptionSelector(
        title = "Trasfondo",
        options = listOf(
          SelectorOption("acolyte", "Acólito"),
          SelectorOption("soldier", "Soldado"),
          SelectorOption("noble", "Noble")
        ),
        selectedOption = selectedBackground,
        onOptionSelected = { selectedBackground = it },
        optionMapper = { option ->
          when (option.id) {
            "acolyte" -> Background.ACOLYTE
            "soldier" -> Background.SOLDIER
            "noble" -> Background.NOBLE
            else -> Background.ACOLYTE
          }
        }
      )

      // Generated name display with dice button
      GeneratedNameDisplay(
        generatedName = generatedName,
        onGenerateClick = {
          if (currentTokens > 0) {
            // TODO: Integrate with ViewModel
            generatedName = "Aeron the Brave"
            currentTokens--
          }
        }
      )

      // Save Name button
      Button(
        onClick = {
          // TODO: Implement save functionality
        },
        modifier = Modifier.fillMaxWidth(),
        enabled = generatedName.isNotEmpty()
      ) {
        Text(
          text = "Guardar Nombre",
          style = MaterialTheme.typography.labelLarge
        )
      }
    }
  }
}
