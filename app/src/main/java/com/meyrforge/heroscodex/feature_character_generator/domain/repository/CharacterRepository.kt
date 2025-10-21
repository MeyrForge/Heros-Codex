package com.meyrforge.heroscodex.feature_character_generator.domain.repository

import com.meyrforge.heroscodex.feature_character_generator.domain.model.CharacterClass

interface CharacterRepository {
  fun getAvailableClasses(): List<CharacterClass>
}

