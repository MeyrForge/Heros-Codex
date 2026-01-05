package com.meyrforge.heroscodex.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.meyrforge.heroscodex.core.database.entity.SavedNameEntity

@Dao
interface SavedNameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(savedNameEntity: SavedNameEntity)

    @Query("SELECT * FROM saved_names ORDER BY created_at ASC")
    suspend fun getSavedNames(): List<SavedNameEntity>
}
