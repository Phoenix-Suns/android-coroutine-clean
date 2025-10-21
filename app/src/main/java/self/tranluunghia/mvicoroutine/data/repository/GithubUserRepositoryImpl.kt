package self.tranluunghia.mvicoroutine.data.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import self.tranluunghia.mvicoroutine.core.entity.DataState
import self.tranluunghia.mvicoroutine.core.helper.extention.repositoryExecutor
import self.tranluunghia.mvicoroutine.data.mapper.GithubRepoResponseMapper
import self.tranluunghia.mvicoroutine.data.mapper.GithubUserLocalMapper
import self.tranluunghia.mvicoroutine.data.mapper.GithubUserResponseMapper
import self.tranluunghia.mvicoroutine.data.mapper.GithubUserResponseToLocalMapper
import self.tranluunghia.mvicoroutine.data.source.local.GithubLocalDataSource
import self.tranluunghia.mvicoroutine.data.source.remote.GithubRemoteDataSource
import self.tranluunghia.mvicoroutine.domain.model.GithubRepo
import self.tranluunghia.mvicoroutine.domain.model.GithubUser
import self.tranluunghia.mvicoroutine.domain.repository.GithubUserRepository
import javax.inject.Inject

class GithubUserRepositoryImpl @Inject constructor(
    private val context: Context,
    private val retrofit: Retrofit,
    private val remoteDataSource: GithubRemoteDataSource,
    private val localDataSource: GithubLocalDataSource,
    private val githubUserResponseMapper: GithubUserResponseMapper,
    private val githubUserLocalMapper: GithubUserLocalMapper,
    private val userResponseToLocalMapper: GithubUserResponseToLocalMapper,
    private val githubRepoResponseMapper: GithubRepoResponseMapper
) : GithubUserRepository {

    /*override fun getUserDetail(username: String): Flow<DataState<GithubUser>> = flow {
        val githubUserResponse = githubWS.getGitHubUserDetail(username)
        githubUserResponseMapper.map(githubUserResponse)
    }*/

    /*override fun getUserDetail(username: String): Flow<DataState<GithubUser>> {
        return flow {
            try {
                val userRemote = remoteDataSource.getGitHubUserDetail(username)
                val userLocal = userResponseToLocalMapper.map(userRemote)
                localDataSource.saveRepo(userLocal)
            } catch (e: Exception) {
                localDataSource.getLocalRepos()
            }
        }
    }*/

    override fun getUserDetail(username: String): Flow<DataState<GithubUser>> =
        repositoryExecutor(
            context = context,
            retrofit = retrofit,
            apiCall = {
                remoteDataSource.getGitHubUserDetail(username)
            },
            transform = {
                githubUserResponseMapper.map(it)
            },
            emitCache = {
                val userLocal = localDataSource.getLocalRepo(username)
                githubUserLocalMapper.map(userLocal)
            }
        )

    override fun getRepoList(
        keyWork: String,
        page: Int,
        perPage: Int
    ): Flow<DataState<List<GithubRepo>>> =
        repositoryExecutor(
            context = context,
            retrofit = retrofit,
            apiCall = {
                remoteDataSource.getRepoList(keyWork, page, perPage)
            },
            transform = { response ->
                val listRepo = ArrayList<GithubRepo>()
                response.items.forEach {
                    listRepo.add(githubRepoResponseMapper.map(it))
                }
                listRepo
            }
        )
}