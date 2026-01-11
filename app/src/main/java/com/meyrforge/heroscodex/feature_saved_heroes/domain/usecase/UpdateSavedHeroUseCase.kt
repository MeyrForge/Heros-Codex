package com.meyrforge.heroscodex.feature_saved_heroes.domain.usecase

import com.meyrforge.heroscodex.core.domain.model.SavedHero
import com.meyrforge.heroscodex.core.domain.repository.SavedHeroesRepository
import javax.inject.Inject

class UpdateSavedHeroUseCase @Inject constructor(
    private val repository: SavedHeroesRepository
) {
    suspend operator fun invoke(updated: SavedHero): Result<Unit> {
        return repository.updateSavedHero(updated)
    }
}
