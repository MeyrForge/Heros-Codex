package com.meyrforge.heroscodex.feature_name_generator.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.meyrforge.heroscodex.core.ui.theme.DarkGlass
import com.meyrforge.heroscodex.core.ui.theme.MagicalGold

@Composable
fun FantasySelectionCard(
  text: String,
  isSelected: Boolean,
  onSelected: () -> Unit,
  modifier: Modifier = Modifier
) {
  val borderWidth by animateDpAsState(
    targetValue = if (isSelected) 2.dp else 0.dp,
    label = "border"
  )
  val borderColor by animateColorAsState(
    targetValue = if (isSelected) MagicalGold else Color.Transparent,
    label = "color"
  )
  val cardHeight by animateDpAsState(
    targetValue = if (isSelected) 110.dp else 100.dp,
    animationSpec = tween(300),
    label = "scale"
  )

  Surface(
    modifier = modifier
      .size(width = 100.dp, height = cardHeight)
      .clickable(onClick = onSelected),
    shape = RoundedCornerShape(16.dp),
    color = DarkGlass,
    border = BorderStroke(borderWidth, borderColor)
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
      modifier = Modifier.padding(8.dp)
    ) {
      Icon(
        imageVector = Icons.Default.Person,
        contentDescription = null,
        tint = if (isSelected) MagicalGold else MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.size(32.dp)
      )
      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = text,
        color = if (isSelected) MagicalGold else MaterialTheme.colorScheme.onSurface,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        textAlign = androidx.compose.ui.text.style.TextAlign.Center
      )
    }
  }
}
