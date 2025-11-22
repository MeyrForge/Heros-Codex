package com.meyrforge.heroscodex.feature_name_generator.domain.usecase

import com.meyrforge.heroscodex.feature_name_generator.domain.model.HeroName
import com.meyrforge.heroscodex.feature_name_generator.domain.model.Gender
import com.meyrforge.heroscodex.feature_name_generator.domain.model.Race
import com.meyrforge.heroscodex.feature_name_generator.domain.model.Background
import com.meyrforge.heroscodex.feature_name_generator.domain.repository.NameRepository
import javax.inject.Inject

class GenerateNameUseCase @Inject constructor(
  private val nameRepository: NameRepository
) {

  fun generate(gender: Gender, race: Race, background: Background): HeroName {
    val start = nameRepository.getNameStarts(race).random()
    val ending = when (gender) {
      Gender.MALE -> nameRepository.getMaleEndings(race).random()
      Gender.FEMALE -> nameRepository.getFemaleEndings(race).random()
      Gender.NEUTRAL -> (nameRepository.getMaleEndings(race) + nameRepository.getFemaleEndings(race)).random()
    }
    val suffix = nameRepository.getSuffixes(background).random()

    return HeroName("$start$ending $suffix", gender, background)
  }
}
