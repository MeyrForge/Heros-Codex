package com.meyrforge.heroscodex.feature_character_generator.domain.usecase

import com.meyrforge.heroscodex.feature_character_generator.domain.model.CharacterClass
import com.meyrforge.heroscodex.feature_character_generator.domain.model.Feature
import com.meyrforge.heroscodex.feature_character_generator.domain.repository.CharacterRepository
import javax.inject.Inject

class GenerateFeaturesUseCase @Inject constructor(
  private val characterRepository: CharacterRepository
) {

  fun generate(characterClass: CharacterClass, level: Int = 1): List<Feature> {
    return characterRepository.getFeaturesForClass(characterClass, level)
  }
}

