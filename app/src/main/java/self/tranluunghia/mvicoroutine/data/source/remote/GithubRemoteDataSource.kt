package self.tranluunghia.mvicoroutine.data.source.remote

import self.tranluunghia.mvicoroutine.data.model.GithubList
import self.tranluunghia.mvicoroutine.data.model.response.GithubRepoResponse
import self.tranluunghia.mvicoroutine.data.model.response.GithubUserResponse
import javax.inject.Inject

class GithubRemoteDataSource @Inject constructor(
    private val api: GithubApiService
) {
    suspend fun getGitHubUserDetail(user: String): GithubUserResponse = api.getGitHubUserDetail(user)

    suspend fun getRepoList(
        keyWork: String,
        page: Int,
        perPage: Int
    ) : GithubList<GithubRepoResponse> = api.getRepoList(keyWork, page, perPage)
}
