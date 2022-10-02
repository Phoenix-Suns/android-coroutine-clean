package self.tranluunghia.mvicoroutine.data.api.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import self.tranluunghia.mvicoroutine.data.model.GithubList
import self.tranluunghia.mvicoroutine.data.model.response.GithubRepoResponse
import self.tranluunghia.mvicoroutine.data.model.response.GithubUserResponse

interface GithubWS {
    @GET("users/{username}")
    suspend fun getGitHubUserDetail(@Path("username") userName: String) : GithubUserResponse

    @GET("search/repositories")
    suspend fun getRepoList(
        @Query("q") keyWork: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ) : GithubList<GithubRepoResponse>

}