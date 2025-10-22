package com.meyrforge.heroscodex.feature_character_generator.domain.repository

import com.meyrforge.heroscodex.feature_character_generator.domain.model.CharacterClass
import com.meyrforge.heroscodex.feature_character_generator.domain.model.Feature
import com.meyrforge.heroscodex.feature_character_generator.domain.model.RacialBonuses

interface CharacterRepository {
  fun getAvailableClasses(): List<CharacterClass>
  fun getRacialBonuses(raceId: String): RacialBonuses
  fun getFeaturesForClass(characterClass: CharacterClass, level: Int): List<Feature>
}

