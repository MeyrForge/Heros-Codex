package com.meyrforge.heroscodex.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tokens")
data class TokensEntity(
  @PrimaryKey val id: Int = 1,
  val current: Int,
  val max: Int,
  val lastConsumedAt: Long
)
