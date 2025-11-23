package com.meyrforge.heroscodex.feature_name_generator.domain.model

data class HeroName(
  val name: String,
  val race: Race,
  val gender: Gender,
  val background: Background
)
