package com.meyrforge.heroscodex.feature_name_generator.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.meyrforge.heroscodex.R

@Composable
fun GeneratedNameDisplay(
  generatedName: String,
  onGenerateClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    OutlinedTextField(
      value = generatedName,
      onValueChange = { },
      readOnly = true,
      modifier = Modifier.weight(1f),
      colors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.surface,
        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
        disabledContainerColor = MaterialTheme.colorScheme.surface,
        focusedBorderColor = MaterialTheme.colorScheme.outline,
        unfocusedBorderColor = MaterialTheme.colorScheme.outline
      ),
      shape = RoundedCornerShape(8.dp),
      textStyle = MaterialTheme.typography.bodyLarge
    )

    IconButton(
      onClick = onGenerateClick,
      modifier = Modifier
        .size(56.dp)
        .background(
          color = MaterialTheme.colorScheme.primary,
          shape = RoundedCornerShape(8.dp)
        )
    ) {
      Icon(
        painter = painterResource(id = R.drawable.ic_dice),
        contentDescription = "Generate name",
        tint = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier.size(32.dp)
      )
    }
  }
}
