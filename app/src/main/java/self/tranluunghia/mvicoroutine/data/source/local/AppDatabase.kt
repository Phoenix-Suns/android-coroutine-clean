package self.tranluunghia.mvicoroutine.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import self.tranluunghia.mvicoroutine.data.model.local.GithubUserLocal

@Database(entities = [GithubUserLocal::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun githubDao(): GithubDao
}
