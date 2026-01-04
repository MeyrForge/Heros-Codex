package com.meyrforge.heroscodex.feature_name_generator.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.meyrforge.heroscodex.feature_name_generator.data.local.entity.SavedNameEntity

@Dao
interface SavedNameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(savedNameEntity: SavedNameEntity)
}
