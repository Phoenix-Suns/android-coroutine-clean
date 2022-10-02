package self.tranluunghia.mvicoroutine.domain.repository

import kotlinx.coroutines.flow.Flow
import self.tranluunghia.mvicoroutine.core.entity.DataState
import self.tranluunghia.mvicoroutine.domain.model.GithubRepo
import self.tranluunghia.mvicoroutine.domain.model.GithubUser

interface GithubUserRepository {
    fun getUserDetail(username: String): Flow<DataState<GithubUser>>
    fun getRepoList(keyWork: String, page: Int, perPage: Int): Flow<DataState<List<GithubRepo>>>
}