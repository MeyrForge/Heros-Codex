package com.meyrforge.heroscodex.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.meyrforge.heroscodex.core.database.entity.SavedNameEntity

@Dao
interface SavedNameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(savedNameEntity: SavedNameEntity)
}
