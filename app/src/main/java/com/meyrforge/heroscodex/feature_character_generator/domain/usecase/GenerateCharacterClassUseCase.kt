package com.meyrforge.heroscodex.feature_character_generator.domain.usecase

import com.meyrforge.heroscodex.feature_character_generator.domain.model.CharacterClass
import com.meyrforge.heroscodex.feature_character_generator.domain.repository.CharacterRepository
import javax.inject.Inject

class GenerateCharacterClassUseCase @Inject constructor(
  private val characterRepository: CharacterRepository
) {

  fun generate(): CharacterClass {
    val availableClasses = characterRepository.getAvailableClasses()
    return availableClasses.random()
  }
}

