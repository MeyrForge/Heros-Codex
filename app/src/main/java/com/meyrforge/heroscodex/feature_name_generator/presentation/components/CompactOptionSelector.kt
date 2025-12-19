package com.meyrforge.heroscodex.feature_name_generator.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.meyrforge.heroscodex.feature_name_generator.presentation.model.SelectorOption

@Composable
fun <T> CompactOptionSelector(
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
      color = MaterialTheme.colorScheme.onBackground,
      fontSize = 18.sp,
      fontWeight = FontWeight.Medium,
      modifier = Modifier.padding(vertical = 8.dp)
    )

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      options.forEach { option ->
        val mappedOption = optionMapper(option)
        FantasySelectionCard(
          text = option.label,
          isSelected = selectedOption == mappedOption,
          onSelected = { onOptionSelected(mappedOption) },
          modifier = Modifier.weight(1f)
        )
      }
    }
  }
}
