package com.meyrforge.heroscodex.feature_name_generator.data.repository

import com.meyrforge.heroscodex.core.database.dao.SavedNameDao
import com.meyrforge.heroscodex.core.database.entity.SavedNameEntity
import com.meyrforge.heroscodex.feature_name_generator.data.local.dao.NameDao
import com.meyrforge.heroscodex.feature_name_generator.domain.model.Background
import com.meyrforge.heroscodex.feature_name_generator.domain.model.Gender
import com.meyrforge.heroscodex.feature_name_generator.domain.model.HeroName
import com.meyrforge.heroscodex.feature_name_generator.domain.model.Race
import com.meyrforge.heroscodex.feature_name_generator.domain.repository.NameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NameRepositoryImpl @Inject constructor(
  private val nameDao: NameDao,
  private val savedNameDao: SavedNameDao
) : NameRepository {

  override suspend fun generateName(
    race: Race,
    gender: Gender,
    background: Background
  ): Result<HeroName> = withContext(Dispatchers.IO) {
    try {
      val name = nameDao.getRandomName(
        raceId = race.toId(),
        genderId = gender.toId(),
        backgroundId = background.toId()
      ) ?: "Unknown Hero"

      Result.success(
        HeroName(
          name = name,
          race = race,
          gender = gender,
          background = background
        )
      )
    } catch (e: Exception) {
      Result.failure(e)
    }
  }

  override suspend fun saveName(heroName: HeroName): Result<Unit> = withContext(Dispatchers.IO) {
    try {
      val entity = SavedNameEntity(
        name = heroName.name,
        raceId = heroName.race.toId(),
        genderId = heroName.gender.toId(),
        backgroundId = heroName.background.toId()
      )
      savedNameDao.insert(entity)
      Result.success(Unit)
    } catch (e: Exception) {
      Result.failure(e)
    }
  }

  private fun Race.toId(): Int = when (this) {
    Race.DRAGONBORN -> 0
    Race.DWARF -> 1
    Race.ELF -> 2
    Race.GNOME -> 3
    Race.HALF_ELF -> 4
    Race.HALF_ORC -> 5
    Race.HALFLING -> 6
    Race.HUMAN -> 7
    Race.TIEFLING -> 8
  }

  private fun Gender.toId(): Int = when (this) {
    Gender.MALE -> 0
    Gender.FEMALE -> 1
    Gender.NEUTRAL -> 2
  }

  private fun Background.toId(): Int = when (this) {
    Background.ACOLYTE -> 0
    Background.CHARLATAN -> 1
    Background.CRIMINAL -> 2
    Background.ENTERTAINER -> 3
    Background.FOLK_HERO -> 4
    Background.GUILD_ARTISAN -> 5
    Background.HERMIT -> 6
    Background.NOBLE -> 7
    Background.OUTLANDER -> 8
    Background.SAGE -> 9
    Background.SAILOR -> 10
    Background.SOLDIER -> 11
    Background.URCHIN -> 12
  }
}
