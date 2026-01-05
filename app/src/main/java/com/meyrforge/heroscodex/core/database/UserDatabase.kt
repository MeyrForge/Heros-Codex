package com.meyrforge.heroscodex.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.meyrforge.heroscodex.core.database.dao.SavedNameDao
import com.meyrforge.heroscodex.core.database.entity.SavedNameEntity
import java.util.UUID

@Database(
  entities = [SavedNameEntity::class],
  version = 2, // Incremented version
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
      ).addMigrations(MIGRATION_1_2)
       .build()
    }

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            val defaultUuid = UUID.randomUUID().toString()
            val defaultCreatedAt = System.currentTimeMillis()

            db.execSQL("ALTER TABLE saved_names ADD COLUMN uuid TEXT NOT NULL DEFAULT '$defaultUuid'")
            db.execSQL("ALTER TABLE saved_names ADD COLUMN created_at INTEGER NOT NULL DEFAULT $defaultCreatedAt")
        }
    }
  }
}
