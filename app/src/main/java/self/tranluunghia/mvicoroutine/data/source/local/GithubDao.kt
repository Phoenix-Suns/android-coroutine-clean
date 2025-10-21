package self.tranluunghia.mvicoroutine.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import self.tranluunghia.mvicoroutine.data.model.local.GithubUserLocal

@Dao
interface GithubDao {
    @Query("SELECT * FROM github_user")
    suspend fun getAll(): List<GithubUserLocal>

    @Query("SELECT * FROM github_user WHERE name = :username")
    suspend fun getUser(username: String): GithubUserLocal

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<GithubUserLocal>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: GithubUserLocal)
}
