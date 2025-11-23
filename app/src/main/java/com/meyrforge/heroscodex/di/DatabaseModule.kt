package com.meyrforge.heroscodex.di

import android.content.Context
import com.meyrforge.heroscodex.feature_name_generator.data.local.NameDatabase
import com.meyrforge.heroscodex.feature_name_generator.data.local.dao.NameDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

  @Provides
  @Singleton
  fun provideNameDatabase(
    @ApplicationContext context: Context
  ): NameDatabase {
    return NameDatabase.getInstance(context)
  }

  @Provides
  @Singleton
  fun provideNameDao(database: NameDatabase): NameDao {
    return database.nameDao()
  }
}

