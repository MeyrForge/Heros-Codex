package com.meyrforge.heroscodex.feature_name_generator.data.repository

import com.meyrforge.heroscodex.feature_name_generator.domain.repository.NameRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NameRepositoryImpl @Inject constructor() : NameRepository {

  override fun getMaleEndings(): List<String> = listOf(
    "on", "ar", "us", "or", "an", "er", "ix"
  )

  override fun getFemaleEndings(): List<String> = listOf(
    "ia", "ra", "el", "na", "la", "sa", "yn"
  )

  override fun getNameStarts(): List<String> = listOf(
    "Aer", "Bel", "Cel", "Dar", "Eld", "Fel", "Gar", "Hal", "Ith", "Jor",
    "Kel", "Lys", "Mor", "Nar", "Orl", "Pyr", "Qua", "Ral", "Syl", "Tar",
    "Ulr", "Vel", "Wyr", "Xar", "Yor", "Zel"
  )
}
