package com.meyrforge.heroscodex.feature_name_generator.domain.repository

interface NameRepository {
  fun getMaleEndings(): List<String>
  fun getFemaleEndings(): List<String>
  fun getNameStarts(): List<String>
}
