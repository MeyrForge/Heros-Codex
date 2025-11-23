package com.meyrforge.heroscodex.feature_name_generator.domain.repository

import com.meyrforge.heroscodex.feature_name_generator.domain.model.Background
import com.meyrforge.heroscodex.feature_name_generator.domain.model.Gender
import com.meyrforge.heroscodex.feature_name_generator.domain.model.HeroName
import com.meyrforge.heroscodex.feature_name_generator.domain.model.Race

interface NameRepository {
  suspend fun generateName(
    race: Race,
    gender: Gender,
    background: Background
  ): Result<HeroName>
}
