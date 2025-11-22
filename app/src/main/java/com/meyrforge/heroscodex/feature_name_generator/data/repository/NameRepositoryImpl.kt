package com.meyrforge.heroscodex.feature_name_generator.data.repository

import com.meyrforge.heroscodex.feature_name_generator.domain.model.Background
import com.meyrforge.heroscodex.feature_name_generator.domain.model.Race
import com.meyrforge.heroscodex.feature_name_generator.domain.repository.NameRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NameRepositoryImpl @Inject constructor() : NameRepository {

  override fun getMaleEndings(race: Race): List<String> = when (race) {
    Race.HUMAN -> listOf("on", "ar", "us", "or", "an", "er", "ix")
    Race.ELF -> listOf("ion", "ael", "or", "las", "dir", "thil", "mir")
    Race.DWARF -> listOf("im", "oin", "ur", "in", "ar", "or", "ak")
  }

  override fun getFemaleEndings(race: Race): List<String> = when (race) {
    Race.HUMAN -> listOf("ia", "ra", "el", "na", "la", "sa", "yn")
    Race.ELF -> listOf("iel", "wen", "riel", "ith", "lia", "eth", "ara")
    Race.DWARF -> listOf("a", "in", "is", "ild", "ara", "eth", "ona")
  }

  override fun getNameStarts(race: Race): List<String> = when (race) {
    Race.HUMAN -> listOf(
      "Aer", "Bel", "Cel", "Dar", "Eld", "Fel", "Gar", "Hal", "Ith", "Jor",
      "Kel", "Lys", "Mor", "Nar", "Orl", "Pyr", "Qua", "Ral", "Syl", "Tar"
    )
    Race.ELF -> listOf(
      "Ael", "Ber", "Cel", "Dar", "Elo", "Fae", "Gal", "Hal", "Ili", "Kal",
      "Leg", "Mel", "Nar", "Ori", "Pel", "Qua", "Sil", "Thar", "Uth", "Val"
    )
    Race.DWARF -> listOf(
      "Bar", "Dor", "Dur", "Ebr", "Gar", "Gim", "Kal", "Mor", "Nor", "Ors",
      "Thr", "Tor", "Bal", "Bof", "Dag", "Dwa", "Fal", "Kil", "Thor", "Ulf"
    )
  }

  override fun getSuffixes(background: Background): List<String> = when (background) {
    Background.ACOLYTE -> listOf("the Devoted", "the Faithful", "the Blessed", "the Pious")
    Background.SOLDIER -> listOf("the Brave", "the Bold", "the Shield", "the Sword")
    Background.NOBLE -> listOf("the Noble", "the Highborn", "the Lordly", "the Regal")
  }
}
