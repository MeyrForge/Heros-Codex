package com.meyrforge.heroscodex.feature_name_generator.domain.repository

import com.meyrforge.heroscodex.feature_name_generator.domain.model.Background
import com.meyrforge.heroscodex.feature_name_generator.domain.model.Race

interface NameRepository {
  fun getMaleEndings(race: Race): List<String>
  fun getFemaleEndings(race: Race): List<String>
  fun getNameStarts(race: Race): List<String>
  fun getSuffixes(background: Background): List<String>
}
