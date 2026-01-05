package com.meyrforge.heroscodex.core.domain.usecase

import com.meyrforge.heroscodex.core.domain.repository.SavedHeroesRepository
import com.meyrforge.heroscodex.feature_name_generator.domain.model.HeroName
import javax.inject.Inject

class SaveNameUseCase @Inject constructor(
  private val savedHeroesRepository: SavedHeroesRepository
) {

  suspend operator fun invoke(heroName: HeroName): Result<Unit> {
    return savedHeroesRepository.saveHero(heroName)
  }
}
