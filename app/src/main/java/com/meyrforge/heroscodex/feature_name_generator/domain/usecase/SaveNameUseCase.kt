package com.meyrforge.heroscodex.feature_name_generator.domain.usecase

import com.meyrforge.heroscodex.feature_name_generator.domain.model.HeroName
import com.meyrforge.heroscodex.feature_name_generator.domain.repository.NameRepository
import javax.inject.Inject

class SaveNameUseCase @Inject constructor(
  private val nameRepository: NameRepository
) {

  suspend operator fun invoke(heroName: HeroName): Result<Unit> {
    return nameRepository.saveName(heroName)
  }
}
