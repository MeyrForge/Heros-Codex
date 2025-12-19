package com.meyrforge.heroscodex.feature_name_generator.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.meyrforge.heroscodex.feature_name_generator.presentation.model.SelectorOption

@Composable
fun <T> HorizontalOptionSelector(
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

    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      contentPadding = PaddingValues(vertical = 8.dp)
    ) {
      items(options) { option ->
        val mappedOption = optionMapper(option)
        FantasySelectionCard(
          text = option.label,
          isSelected = selectedOption == mappedOption,
          onSelected = { onOptionSelected(mappedOption) }
        )
      }
    }
  }
}
