package mo.ed.movies.inc.common.network

import mo.ed.movies.inc.responses.MoviesListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("/list/1")
    fun getMoviesList(@Query("api_key") api_key: String):Call<MoviesListResponse>
}