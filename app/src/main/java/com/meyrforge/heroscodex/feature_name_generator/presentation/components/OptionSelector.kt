package com.meyrforge.heroscodex.feature_name_generator.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class SelectorOption(
  val id: String,
  val label: String
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T> OptionSelector(
  title: String,
  options: List<SelectorOption>,
  selectedOption: T,
  onOptionSelected: (T) -> Unit,
  optionMapper: (SelectorOption) -> T,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
    Text(
      text = title,
      style = MaterialTheme.typography.labelLarge,
      color = MaterialTheme.colorScheme.secondary,
    )

    HorizontalDivider(
      color = MaterialTheme.colorScheme.outline,
      modifier = Modifier.padding(bottom = 8.dp)
    )

    FlowRow(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp),
      maxItemsInEachRow = 3
    ) {
      options.forEach { option ->
        val mappedOption = optionMapper(option)
        SelectionChip(
          label = option.label,
          isSelected = selectedOption == mappedOption,
          onClick = { onOptionSelected(mappedOption) },
          modifier = Modifier.weight(1f)
        )
      }
      
      // Add invisible spacers to fill incomplete rows
      val remainingSlots = (3 - (options.size % 3)) % 3
      repeat(remainingSlots) {
        androidx.compose.foundation.layout.Spacer(modifier = Modifier.weight(1f))
      }
    }
  }
}

@Composable
private fun SelectionChip(
  label: String,
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Button(
    onClick = onClick,
    modifier = modifier,
    colors = ButtonDefaults.buttonColors(
      containerColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
      } else {
        MaterialTheme.colorScheme.surface
      },
      contentColor = if (isSelected) {
        MaterialTheme.colorScheme.onPrimary
      } else {
        MaterialTheme.colorScheme.onSurface
      }
    )
  ) {
    Text(
      text = label,
      style = MaterialTheme.typography.bodyMedium,
      textAlign = androidx.compose.ui.text.style.TextAlign.Center
    )
  }
}
