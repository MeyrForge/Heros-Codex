package com.meyrforge.heroscodex.feature_character_generator.domain.usecase

import com.meyrforge.heroscodex.feature_character_generator.domain.model.CharacterClass
import com.meyrforge.heroscodex.feature_character_generator.domain.model.StartingEquipment
import com.meyrforge.heroscodex.feature_character_generator.domain.repository.CharacterRepository
import javax.inject.Inject

class GenerateInventoryUseCase @Inject constructor(
  private val characterRepository: CharacterRepository
) {

  fun generate(characterClass: CharacterClass): StartingEquipment {
    return characterRepository.getStartingEquipment(characterClass)
  }
}

