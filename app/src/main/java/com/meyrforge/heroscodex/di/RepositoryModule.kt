package com.meyrforge.heroscodex.di

import com.meyrforge.heroscodex.feature_name_generator.data.repository.NameRepositoryImpl
import com.meyrforge.heroscodex.feature_name_generator.domain.repository.NameRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
  
  @Binds
  @Singleton
  abstract fun bindNameRepository(
    nameRepositoryImpl: NameRepositoryImpl
  ): NameRepository
}
