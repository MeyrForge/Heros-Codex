package com.meyrforge.heroscodex.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_names")
data class SavedNameEntity(
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
