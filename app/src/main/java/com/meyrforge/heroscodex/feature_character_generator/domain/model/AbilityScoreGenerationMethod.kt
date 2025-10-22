package com.meyrforge.heroscodex.feature_character_generator.domain.model

enum class AbilityScoreGenerationMethod {
  // Roll 4d6, drop the lowest die, sum the remaining three. Repeat 6 times
  FOUR_D6_DROP_LOWEST,

  // Auto-assign standard array prioritizing class primary abilities
  STANDARD_ARRAY,

  // User manually distributes standard array values [15, 14, 13, 12, 10, 8]
  MANUAL_ARRAY,

  // User manually distributes 27 points starting from base 8 (range: 8-15)
  MANUAL_POINT_BUY
}

