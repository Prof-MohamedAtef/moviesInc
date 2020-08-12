package mo.ed.movies.inc.common.network

import mo.ed.movies.inc.responses.crewListResponse.CrewListResponse
import mo.ed.movies.inc.responses.moviesListResponse.MoviesListResponse
import mo.ed.movies.inc.responses.rate.RateBody
import mo.ed.movies.inc.responses.rate.RateResponse
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
    @GET("/3/list/2?")
    fun getMoviesList(@Query("api_key") api_key: String, @Query("language") language: String):Call<MoviesListResponse>

    @GET("/3/movie/{movieId}/credits?")
    fun getCrewList( @Path("movieId") movieId: Long, @Query("api_key") apiKey: String): Call<CrewListResponse>

    @POST("/3/movie/{movieId}/rating?")
    fun rateMovie(@Path("movieId") movieId: Long, @Query("api_key") apiKey: String, @Body rateBody: RateBody): Call<RateResponse>
}