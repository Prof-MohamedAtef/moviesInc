package mo.ed.movies.inc.common.network;

import mo.ed.movies.inc.responses.MoviesListResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitService {
    @GET("/list/1")
    Call<MoviesListResponse> getMovies();

}
