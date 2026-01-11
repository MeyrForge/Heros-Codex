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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.navigation.NavController
import com.meyrforge.heroscodex.core.ui.theme.DarkBackgroundEnd
import com.meyrforge.heroscodex.core.ui.theme.DarkBackgroundStart
import com.meyrforge.heroscodex.feature_saved_heroes.presentation.components.ExpandableHeroCard
import com.meyrforge.heroscodex.core.domain.model.SavedHero
import com.meyrforge.heroscodex.core.ui.theme.MagicalGold
import com.meyrforge.heroscodex.feature_saved_heroes.presentation.viewmodel.SavedHeroesViewModel

@Composable
fun SavedHeroesScreen(
    viewModel: SavedHeroesViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    var heroToDelete by remember { mutableStateOf<SavedHero?>(null) }
    var heroToEdit by remember { mutableStateOf<SavedHero?>(null) }
    var heroToGenerateName by remember { mutableStateOf<SavedHero?>(null) }

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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Mis Héroes",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            com.meyrforge.heroscodex.feature_name_generator.presentation.components.TokenCounter(
                currentTokens = uiState.currentTokens,
                maxTokens = uiState.maxTokens,
                nextRegenRemainingMs = uiState.nextRegenRemainingMs,
                onRequestTokens = viewModel::requestTokensNow
            )
        }

        LazyColumn {
            items(items = uiState.heroes, key = { it.id }) { hero ->
                ExpandableHeroCard(
                    hero = hero,
                    isExpanded = uiState.expandedHeroId == hero.id,
                    onCardClick = { viewModel.onHeroClicked(hero.id) },
                    onEditClick = { heroToEdit = hero },
                    onDeleteClick = { heroToDelete = hero }
                )
            }
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

        // Edit / Generate popup
        heroToEdit?.let { savedHero ->
            Dialog(
                onDismissRequest = { heroToEdit = null },
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
                            text = "¿Qué querés hacer con \n\n ${savedHero.name}?",
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text(
                            text = "Editar un héroe consumirá 1 token",
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            TextButton(
                                onClick = {
                                    // Navigate to edit traits screen
                                    heroToEdit = null
                                    navController.navigate("edit_hero/${savedHero.id}")
                                },
                                colors = ButtonDefaults.textButtonColors(contentColor = MagicalGold)
                            ) {
                                Text("Editar rasgos")
                            }

                            TextButton(
                                onClick = {
                                    // Open confirmation dialog for generation
                                    heroToGenerateName = savedHero
                                    heroToEdit = null
                                },
                                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Text("Generar nombre")
                            }

                        }
                    }
                }
            }
        }

        // Generate name confirmation dialog
        heroToGenerateName?.let { savedHero ->
            Dialog(
                onDismissRequest = { heroToGenerateName = null },
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
                            text = "¿Generar un nuevo nombre para \n\n ${savedHero.name}?",
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text(
                            text = "Generar un nuevo nombre consumirá 1 token",
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            TextButton(
                                onClick = { heroToGenerateName = null },
                                colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
                            ) {
                                Text("Cancelar")
                            }
                            TextButton(
                                onClick = {
                                    viewModel.onGenerateNameForHero(savedHero.id)
                                    heroToGenerateName = null
                                },
                                colors = ButtonDefaults.textButtonColors(contentColor = MagicalGold)
                            ) {
                                Text("Confirmar")
                            }
                        }
                    }
                }
            }
        }

        // Show generation result message
        uiState.lastGenerationMessage?.let { msg ->
            Dialog(
                onDismissRequest = { viewModel.clearLastGenerationMessage() },
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
                            text = msg,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            TextButton(
                                onClick = { viewModel.clearLastGenerationMessage() },
                                colors = ButtonDefaults.textButtonColors(contentColor = MagicalGold)
                            ) {
                                Text("OK")
                            }
                        }
                    }
                }
            }
        }

        if (uiState.isSaving) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.9f)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    androidx.compose.material3.LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Generando nombre...",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

