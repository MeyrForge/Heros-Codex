package com.meyrforge.heroscodex.feature_name_generator.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.meyrforge.heroscodex.R
import com.meyrforge.heroscodex.core.ui.theme.MagicalGold

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
      text = "Forja de Nombres",
      color = MagicalGold,
      fontSize = 28.sp,
      fontWeight = FontWeight.Bold
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
    Image(
      painter = painterResource(id = R.drawable.ic_token),
      contentDescription = "token",
      modifier = Modifier.size(32.dp)
    )

    Text(
      text = "$currentTokens/$maxTokens",
      fontSize = 18.sp,
      fontWeight = FontWeight.Medium,
      color = MaterialTheme.colorScheme.onBackground
    )
  }
}
