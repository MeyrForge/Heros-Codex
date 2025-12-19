package com.meyrforge.heroscodex.feature_name_generator.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.meyrforge.heroscodex.R
import com.meyrforge.heroscodex.core.ui.theme.DarkGlass
import com.meyrforge.heroscodex.core.ui.theme.MagicalGold

@Composable
fun GeneratedNameDisplay(
  generatedName: String,
  onGenerateClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier
      .fillMaxWidth()
      .height(150.dp),
    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    color = DarkGlass,
    border = BorderStroke(1.dp, MagicalGold.copy(alpha = 0.3f))
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Column {
        Text(
          text = "Nombre generado:",
          color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
          fontSize = 14.sp
        )
        Text(
          text = generatedName.ifEmpty { "..." },
          color = MaterialTheme.colorScheme.onBackground,
          fontSize = 24.sp,
          fontWeight = FontWeight.Bold
        )
      }

      Box(
        modifier = Modifier
          .size(70.dp)
          .clip(CircleShape)
          .background(Brush.linearGradient(listOf(MagicalGold, Color(0xFFC21807))))
          .clickable(onClick = onGenerateClick),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          painter = painterResource(id = R.drawable.ic_dice),
          contentDescription = "Roll D20",
          tint = Color.White,
          modifier = Modifier.size(32.dp)
        )
      }
    }
  }
}
