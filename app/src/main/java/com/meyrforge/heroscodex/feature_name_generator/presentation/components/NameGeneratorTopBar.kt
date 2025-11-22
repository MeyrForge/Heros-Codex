package com.meyrforge.heroscodex.feature_name_generator.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NameGeneratorTopBar(
  currentTokens: Int,
  maxTokens: Int,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(16.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = "Generador de Nombre",
      style = MaterialTheme.typography.titleLarge,
      color = MaterialTheme.colorScheme.onBackground
    )

    TokenCounter(
      currentTokens = currentTokens,
      maxTokens = maxTokens
    )
  }
}

@Composable
private fun TokenCounter(
  currentTokens: Int,
  maxTokens: Int,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    // Coin icon
    Box(
      modifier = Modifier
        .size(32.dp)
        .clip(CircleShape)
        .background(MaterialTheme.colorScheme.secondary),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = "◆",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSecondary
      )
    }

    // Counter
    Text(
      text = "$currentTokens/$maxTokens",
      style = MaterialTheme.typography.titleMedium,
      color = MaterialTheme.colorScheme.onBackground
    )
  }
}
