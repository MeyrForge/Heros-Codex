package com.meyrforge.heroscodex.feature_saved_heroes.domain.usecase

import com.meyrforge.heroscodex.core.domain.model.SavedHero
import com.meyrforge.heroscodex.core.domain.repository.SavedHeroesRepository
import javax.inject.Inject

class GetSavedHeroesUseCase @Inject constructor(
    private val repository: SavedHeroesRepository
) {
    suspend operator fun invoke(): Result<List<SavedHero>> {
        return repository.getSavedHeroes()
    }
}
