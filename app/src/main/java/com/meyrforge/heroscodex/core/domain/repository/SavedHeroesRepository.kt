package com.meyrforge.heroscodex.core.domain.repository

import com.meyrforge.heroscodex.core.domain.model.SavedHero
import com.meyrforge.heroscodex.feature_name_generator.domain.model.HeroName
import java.util.UUID

interface SavedHeroesRepository {
    suspend fun saveHero(heroName: HeroName): Result<Unit>
    suspend fun getSavedHeroes(): Result<List<SavedHero>>
    suspend fun deleteSavedHero(heroId: UUID): Result<Unit>
}
