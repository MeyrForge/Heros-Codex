package com.meyrforge.heroscodex.core.data.repository

import com.meyrforge.heroscodex.core.data.local.mapper.toDomain
import com.meyrforge.heroscodex.core.data.local.mapper.toEntity
import com.meyrforge.heroscodex.core.database.dao.SavedNameDao
import com.meyrforge.heroscodex.core.domain.model.SavedHero
import com.meyrforge.heroscodex.core.domain.repository.SavedHeroesRepository
import com.meyrforge.heroscodex.feature_name_generator.domain.model.HeroName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class SavedHeroesRepositoryImpl @Inject constructor(
    private val savedNameDao: SavedNameDao
) : SavedHeroesRepository {

    override suspend fun saveHero(heroName: HeroName): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            savedNameDao.insert(heroName.toEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getSavedHeroes(): Result<List<SavedHero>> = withContext(Dispatchers.IO) {
        try {
            val savedHeroes = savedNameDao.getSavedNames().map { it.toDomain() }
            Result.success(savedHeroes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteSavedHero(heroId: UUID): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            savedNameDao.deleteByUuid(heroId.toString())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateSavedHero(updated: SavedHero): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            // remove existing by uuid then insert updated entity with same uuid
            savedNameDao.deleteByUuid(updated.id.toString())
            savedNameDao.insert(updated.toEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
