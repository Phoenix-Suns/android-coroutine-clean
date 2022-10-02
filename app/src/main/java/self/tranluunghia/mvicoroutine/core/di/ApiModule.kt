package self.tranluunghia.mvicoroutine.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import self.tranluunghia.mvicoroutine.data.api.service.GithubWS
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Singleton
    @Provides
    internal fun provideGithubService(retrofit: Retrofit): GithubWS {
        return retrofit.create(GithubWS::class.java)
    }
}