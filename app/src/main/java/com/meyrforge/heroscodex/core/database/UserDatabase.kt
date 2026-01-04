package com.meyrforge.heroscodex.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.meyrforge.heroscodex.core.database.dao.SavedNameDao
import com.meyrforge.heroscodex.core.database.entity.SavedNameEntity

@Database(
  entities = [SavedNameEntity::class],
  version = 1,
  exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {

  abstract fun savedNameDao(): SavedNameDao

  companion object {
    private const val DATABASE_NAME = "user_data.db"

    @Volatile
    private var INSTANCE: UserDatabase? = null

    fun getInstance(context: Context): UserDatabase {
      return INSTANCE ?: synchronized(this) {
        INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
      }
    }

    private fun buildDatabase(context: Context): UserDatabase {
      return Room.databaseBuilder(
        context.applicationContext,
        UserDatabase::class.java,
        DATABASE_NAME
      ).build()
    }
  }
}
