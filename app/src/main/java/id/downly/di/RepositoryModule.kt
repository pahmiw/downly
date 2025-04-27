package id.downly.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.downly.data.repository.RepositoryImpl
import id.downly.domain.repository.Repository
import javax.inject.Singleton

/**
 * @Author Ahmad Pahmi Created on April 2025
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRepository(impl: RepositoryImpl): Repository
}