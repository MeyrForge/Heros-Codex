package com.meyrforge.heroscodex.feature_name_generator.domain.usecase

import com.meyrforge.heroscodex.core.domain.model.Background
import com.meyrforge.heroscodex.core.domain.model.Gender
import com.meyrforge.heroscodex.feature_name_generator.domain.model.HeroName
import com.meyrforge.heroscodex.core.domain.model.Race
import com.meyrforge.heroscodex.feature_name_generator.domain.repository.NameRepository
import javax.inject.Inject

class GenerateNameUseCase @Inject constructor(
  private val nameRepository: NameRepository
) {

  suspend operator fun invoke(
    gender: Gender,
    race: Race,
    background: Background
  ): Result<HeroName> {
    return nameRepository.generateName(race, gender, background)
  }
}
