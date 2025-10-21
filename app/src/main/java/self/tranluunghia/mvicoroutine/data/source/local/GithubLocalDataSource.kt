package self.tranluunghia.mvicoroutine.data.source.local

import self.tranluunghia.mvicoroutine.data.model.local.GithubUserLocal
import javax.inject.Inject

class GithubLocalDataSource @Inject constructor(
    private val dao: GithubDao
) {
    suspend fun getLocalRepos(): List<GithubUserLocal> = dao.getAll()
    suspend fun getLocalRepo(username: String): GithubUserLocal = dao.getUser(username)
    suspend fun saveRepos(list: List<GithubUserLocal>) = dao.insertAll(list)
    suspend fun saveRepo(user: GithubUserLocal) = dao.insert(user)
}
