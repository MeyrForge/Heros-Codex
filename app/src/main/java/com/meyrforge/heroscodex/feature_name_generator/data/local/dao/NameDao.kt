package com.meyrforge.heroscodex.feature_name_generator.data.local.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface NameDao {
    
    @Query("""
        SELECT name FROM names 
        WHERE race_id = :raceId 
          AND gender_id = :genderId 
          AND background_id = :backgroundId
        ORDER BY RANDOM() 
        LIMIT 1
    """)
    suspend fun getRandomName(
        raceId: Int,
        genderId: Int,
        backgroundId: Int
    ): String?
}
