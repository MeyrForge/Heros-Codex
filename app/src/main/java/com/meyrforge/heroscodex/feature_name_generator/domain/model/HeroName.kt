package com.meyrforge.heroscodex.feature_name_generator.domain.model

import com.meyrforge.heroscodex.core.domain.model.Background
import com.meyrforge.heroscodex.core.domain.model.Gender
import com.meyrforge.heroscodex.core.domain.model.Race

data class HeroName(
  val name: String,
  val race: Race,
  val gender: Gender,
  val background: Background
)
