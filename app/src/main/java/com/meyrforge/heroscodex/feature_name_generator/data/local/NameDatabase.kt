package com.meyrforge.heroscodex.feature_name_generator.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.meyrforge.heroscodex.feature_name_generator.data.local.dao.NameDao
import com.meyrforge.heroscodex.feature_name_generator.data.local.entity.GeneratedNameEntity
import java.io.File
import java.io.FileOutputStream

@Database(
  entities = [GeneratedNameEntity::class],
  version = 1,
  exportSchema = false
)
abstract class NameDatabase : RoomDatabase() {

  abstract fun nameDao(): NameDao

  companion object {
    private const val DATABASE_NAME = "dnd_names.db"

    @Volatile
    private var INSTANCE: NameDatabase? = null

    fun getInstance(context: Context): NameDatabase {
      return INSTANCE ?: synchronized(this) {
        INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
      }
    }

    private fun buildDatabase(context: Context): NameDatabase {
      val dbFile = context.getDatabasePath(DATABASE_NAME)

      if (!dbFile.exists()) {
        copyDatabaseFromAssets(context, dbFile)
      }

      return Room.databaseBuilder(
        context.applicationContext,
        NameDatabase::class.java,
        DATABASE_NAME
      )
        .fallbackToDestructiveMigration(false)
        .build()
    }

    private fun copyDatabaseFromAssets(context: Context, dbFile: File) {
      dbFile.parentFile?.mkdirs()

      context.assets.open(DATABASE_NAME).use { input ->
        FileOutputStream(dbFile).use { output ->
          input.copyTo(output, bufferSize = 8192)
        }
      }
    }
  }
}
