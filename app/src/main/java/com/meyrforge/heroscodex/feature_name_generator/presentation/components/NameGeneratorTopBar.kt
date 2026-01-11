package com.meyrforge.heroscodex.feature_name_generator.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalDensity
import com.meyrforge.heroscodex.R
import com.meyrforge.heroscodex.core.ui.theme.MagicalGold

@Composable
fun NameGeneratorTopBar(
  currentTokens: Int,
  maxTokens: Int,
  nextRegenRemainingMs: Long,
  modifier: Modifier = Modifier,
  onRequestTokens: () -> Unit = {}
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
      maxTokens = maxTokens,
      nextRegenRemainingMs = nextRegenRemainingMs,
      onRequestTokens = onRequestTokens
    )
  }
}

@Composable
fun TokenCounter(
  currentTokens: Int,
  maxTokens: Int,
  nextRegenRemainingMs: Long,
  modifier: Modifier = Modifier,
  onRequestTokens: () -> Unit = {}
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

    val shownTokensState = remember { mutableIntStateOf(if (currentTokens >= 0) currentTokens else -1) }
    var shownTokens by remember { shownTokensState }
    val targetTokens by rememberUpdatedState(currentTokens)

    var showProgress by remember { mutableStateOf(false) }
    val progressState = remember { mutableFloatStateOf(0f) } 
    var progress by remember { progressState }

    val fontSize = 18.sp
    val density = LocalDensity.current
    val sizeDp = with(density) { fontSize.toDp() }

    LaunchedEffect(targetTokens) {
      if (targetTokens < 0) return@LaunchedEffect

      if (shownTokens < 0) {
        onRequestTokens()
        kotlinx.coroutines.delay(1000L)
        shownTokens = targetTokens
        return@LaunchedEffect
      }

      if (targetTokens > shownTokens) {
        showProgress = true
        progress = 0f
        val duration = 1220L
        val steps = 12
        val stepDelay = duration / steps
        repeat(steps) { i ->
          progress = (i + 1).toFloat() / steps.toFloat()
          kotlinx.coroutines.delay(stepDelay)
        }
        showProgress = false
        shownTokens = targetTokens
      } else {
        shownTokens = targetTokens
      }
    }

    Column(
      modifier = Modifier.height(48.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      if (shownTokens < 0) {
        Box(modifier = Modifier.wrapContentSize(), contentAlignment = Alignment.Center) {
          CircularProgressIndicator(
            strokeWidth = 2.dp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
              .width(sizeDp)
              .height(sizeDp)
          )
        }
      } else if (showProgress) {
        Box(modifier = Modifier.wrapContentSize(), contentAlignment = Alignment.Center) {
          CircularProgressIndicator(
          progress = { progress },
          modifier = Modifier
                        .width(sizeDp)
                        .height(sizeDp),
          color = MaterialTheme.colorScheme.primary,
          strokeWidth = 2.dp
          )
        }
      } else {
        Text(
          text = "$shownTokens/$maxTokens",
          fontSize = fontSize,
          fontWeight = FontWeight.Medium,
          color = MaterialTheme.colorScheme.onBackground
        )
      }
      
      
      if (shownTokens >= 0 && nextRegenRemainingMs >= 0L && currentTokens < maxTokens) {
        val remaining = nextRegenRemainingMs.coerceAtLeast(0L)
        val secondsTotal = (remaining / 1000L).toInt()
        val minutes = secondsTotal / 60
        val seconds = secondsTotal % 60
        Text(
          text = String.format("%d:%02d", minutes, seconds),
          fontSize = 12.sp,
          color = MaterialTheme.colorScheme.onBackground
        )
      }
    }
  }
}
