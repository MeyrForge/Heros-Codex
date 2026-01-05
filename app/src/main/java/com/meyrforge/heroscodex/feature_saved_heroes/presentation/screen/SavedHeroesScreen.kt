package com.meyrforge.heroscodex.feature_saved_heroes.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.meyrforge.heroscodex.core.ui.theme.DarkBackgroundEnd
import com.meyrforge.heroscodex.core.ui.theme.DarkBackgroundStart
import com.meyrforge.heroscodex.feature_saved_heroes.presentation.components.ExpandableHeroCard
import com.meyrforge.heroscodex.feature_saved_heroes.presentation.viewmodel.SavedHeroesViewModel

@Composable
fun SavedHeroesScreen(
    viewModel: SavedHeroesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(DarkBackgroundStart, DarkBackgroundEnd),
                    radius = 1200f
                )
            )
            .padding(16.dp)
    ) {
        Text(
            text = "Mis Héroes",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(uiState.heroes) { hero ->
                ExpandableHeroCard(
                    hero = hero,
                    isExpanded = uiState.expandedHeroId == hero.id,
                    onCardClick = { viewModel.onHeroClicked(hero.id) }
                )
            }
        }
    }
}
