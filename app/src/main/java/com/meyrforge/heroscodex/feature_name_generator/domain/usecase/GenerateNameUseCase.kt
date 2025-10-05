package com.meyrforge.heroscodex.feature_name_generator.domain.usecase

import com.meyrforge.heroscodex.feature_name_generator.domain.model.HeroName
import com.meyrforge.heroscodex.feature_name_generator.domain.model.Gender
import com.meyrforge.heroscodex.feature_name_generator.domain.repository.NameRepository
import javax.inject.Inject

class GenerateNameUseCase @Inject constructor(
  private val nameRepository: NameRepository
) {

  fun generate(gender: Gender): HeroName {
    val start = nameRepository.getNameStarts().random()
    val ending = when (gender) {
      Gender.MALE -> nameRepository.getMaleEndings().random()
      Gender.FEMALE -> nameRepository.getFemaleEndings().random()
      Gender.NEUTRAL -> (nameRepository.getMaleEndings() + nameRepository.getFemaleEndings()).random()
    }

    return HeroName("$start$ending")
  }
}
