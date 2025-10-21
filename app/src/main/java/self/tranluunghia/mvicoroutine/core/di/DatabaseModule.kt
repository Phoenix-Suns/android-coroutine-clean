package self.tranluunghia.mvicoroutine.core.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import self.tranluunghia.mvicoroutine.data.source.local.AppDatabase
import self.tranluunghia.mvicoroutine.data.source.local.GithubDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase =
        Room.databaseBuilder(app, AppDatabase::class.java, "github_db").build()

    @Provides
    fun provideGithubDao(db: AppDatabase): GithubDao = db.githubDao()
}
