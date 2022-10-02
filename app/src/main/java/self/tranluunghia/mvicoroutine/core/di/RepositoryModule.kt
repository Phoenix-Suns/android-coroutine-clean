package self.tranluunghia.mvicoroutine.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import self.tranluunghia.mvicoroutine.data.repository.GithubUserRepositoryImpl
import self.tranluunghia.mvicoroutine.domain.repository.GithubUserRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bind(impl: GithubUserRepositoryImpl): GithubUserRepository
}