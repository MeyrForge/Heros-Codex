package com.meyrforge.heroscodex.feature_saved_heroes.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.TextButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.meyrforge.heroscodex.core.ui.theme.DarkBackgroundEnd
import com.meyrforge.heroscodex.core.ui.theme.DarkBackgroundStart
import com.meyrforge.heroscodex.feature_saved_heroes.presentation.components.ExpandableHeroCard
import com.meyrforge.heroscodex.core.domain.model.SavedHero
import com.meyrforge.heroscodex.feature_saved_heroes.presentation.viewmodel.SavedHeroesViewModel

@Composable
fun SavedHeroesScreen(
    viewModel: SavedHeroesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var heroToDelete by remember { mutableStateOf<SavedHero?>(null) }

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
                    onCardClick = { viewModel.onHeroClicked(hero.id) },
                    onDeleteClick = { heroToDelete = hero }
                )
            }
        }

        // Confirmation dialog (custom Dialog without internal paddings)
        heroToDelete?.let { savedHero ->
            Dialog(
                onDismissRequest = { heroToDelete = null },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Estás a punto de eliminar a \n\n ${savedHero.name}",
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            TextButton(
                                onClick = { heroToDelete = null },
                                colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
                            ) {
                                Text("Cancelar")
                            }
                            TextButton(
                                onClick = {
                                    viewModel.onDeleteHeroClicked(savedHero.id)
                                    heroToDelete = null
                                },
                                colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
                            ) {
                                Text("Eliminar", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}
