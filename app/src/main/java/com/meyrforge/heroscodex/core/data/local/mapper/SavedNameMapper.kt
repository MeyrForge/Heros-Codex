package com.meyrforge.heroscodex.core.data.local.mapper

import com.meyrforge.heroscodex.core.database.entity.SavedNameEntity
import com.meyrforge.heroscodex.core.domain.model.Background
import com.meyrforge.heroscodex.core.domain.model.Gender
import com.meyrforge.heroscodex.core.domain.model.Race
import com.meyrforge.heroscodex.core.domain.model.SavedHero
import com.meyrforge.heroscodex.feature_name_generator.domain.model.HeroName
import java.util.UUID

fun HeroName.toEntity(): SavedNameEntity {
    return SavedNameEntity(
        uuid = UUID.randomUUID().toString(),
        name = this.name,
        raceId = this.race.toId(),
        genderId = this.gender.toId(),
        backgroundId = this.background.toId(),
        createdAt = System.currentTimeMillis()
    )
}

fun SavedNameEntity.toDomain(): SavedHero {
    return SavedHero(
        id = UUID.fromString(this.uuid),
        name = this.name,
        race = raceFromId(this.raceId),
        gender = genderFromId(this.genderId),
        background = backgroundFromId(this.backgroundId),
        createdAt = this.createdAt
    )
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

private fun raceFromId(id: Int): Race = when (id) {
    0 -> Race.DRAGONBORN
    1 -> Race.DWARF
    2 -> Race.ELF
    3 -> Race.GNOME
    4 -> Race.HALF_ELF
    5 -> Race.HALF_ORC
    6 -> Race.HALFLING
    7 -> Race.HUMAN
    8 -> Race.TIEFLING
    else -> Race.HUMAN
}

private fun genderFromId(id: Int): Gender = when (id) {
    0 -> Gender.MALE
    1 -> Gender.FEMALE
    2 -> Gender.NEUTRAL
    else -> Gender.NEUTRAL
}

private fun backgroundFromId(id: Int): Background = when (id) {
    0 -> Background.ACOLYTE
    1 -> Background.CHARLATAN
    2 -> Background.CRIMINAL
    3 -> Background.ENTERTAINER
    4 -> Background.FOLK_HERO
    5 -> Background.GUILD_ARTISAN
    6 -> Background.HERMIT
    7 -> Background.NOBLE
    8 -> Background.OUTLANDER
    9 -> Background.SAGE
    10 -> Background.SAILOR
    11 -> Background.SOLDIER
    12 -> Background.URCHIN
    else -> Background.ACOLYTE
}
