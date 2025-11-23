package com.meyrforge.heroscodex.feature_name_generator.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
  tableName = "names",
  indices = [
    Index(
      value = ["race_id", "gender_id", "background_id"],
      name = "idx_lookup"
    )
  ]
)
data class GeneratedNameEntity(
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "id")
  val id: Long = 0,

  @ColumnInfo(name = "name")
  val name: String,

  @ColumnInfo(name = "race_id")
  val raceId: Int,

  @ColumnInfo(name = "gender_id")
  val genderId: Int,

  @ColumnInfo(name = "background_id")
  val backgroundId: Int
)
