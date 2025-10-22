package com.meyrforge.heroscodex.feature_character_generator.domain.model

data class Item(
  val id: String,
  val name: String,
  val description: String,
  val type: String,
  val quantity: Int = 1,
  val properties: List<String> = emptyList()
)

