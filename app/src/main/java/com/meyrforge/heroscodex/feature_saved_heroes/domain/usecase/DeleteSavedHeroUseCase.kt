package com.meyrforge.heroscodex.feature_saved_heroes.domain.usecase

import com.meyrforge.heroscodex.core.domain.repository.SavedHeroesRepository
import java.util.UUID
import javax.inject.Inject

class DeleteSavedHeroUseCase @Inject constructor(
    private val repository: SavedHeroesRepository
) {
    suspend operator fun invoke(heroId: UUID): Result<Unit> {
        return repository.deleteSavedHero(heroId)
    }
}
