package com.meyrforge.heroscodex.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.meyrforge.heroscodex.core.database.entity.TokensEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedTokensDao {

  @Query("SELECT * FROM tokens WHERE id = 1 LIMIT 1")
  fun getTokensEntity(): Flow<TokensEntity?>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(entity: TokensEntity)

  @Query("UPDATE tokens SET current = :current WHERE id = 1")
  suspend fun updateCurrent(current: Int)

  @Query("UPDATE tokens SET max = :max WHERE id = 1")
  suspend fun updateMax(max: Int)
}
