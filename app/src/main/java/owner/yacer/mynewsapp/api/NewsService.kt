package owner.yacer.mynewsapp.api

import owner.yacer.mynewsapp.BuildConfig
import owner.yacer.mynewsapp.models.ResponseObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("v2/everything")
    suspend fun getNews(
        @Query("domains") domain: String = "bbc.com",
        @Query("page") page: Int = 1,
        @Query("pageSize") size: Int = 10,
        @Query("apiKey") key: String = BuildConfig.API_KEY
    ): Response<ResponseObject>
}