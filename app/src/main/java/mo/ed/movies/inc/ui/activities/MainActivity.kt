package mo.ed.movies.inc.ui.activities

import android.graphics.Color
import android.os.Bundle
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.activity_main.*
import mo.ed.movies.inc.BuildConfig
import mo.ed.movies.inc.R
import mo.ed.movies.inc.common.network.Http
import mo.ed.movies.inc.common.network.RetrofitService
import mo.ed.movies.inc.responses.moviesListResponse.Item
import mo.ed.movies.inc.responses.moviesListResponse.MoviesListResponse
import mo.ed.movies.inc.ui.adapter.MoviesRecyclerAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val EN_LANGUAGE: String="en-US"
    private lateinit var linearLayoutManager:LinearLayoutManager
    private lateinit var adapter:MoviesRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)
        var apiKey = BuildConfig.MoviesApiKey;
        getMoviesList(apiKey = apiKey)
        changeSwipeColor()
        swipe_refresh_layout.setOnRefreshListener{
            getMoviesList(apiKey=apiKey)
        }
    }

    fun getMoviesList(apiKey: String){
        val retrofitService = Http.create(RetrofitService::class.java)
        val call=retrofitService.getMoviesList(apiKey,EN_LANGUAGE)
        swipe_refresh_layout.isRefreshing=true
        call.enqueue(object : Callback<MoviesListResponse> {
            override fun onResponse(call: Call<MoviesListResponse>, response: Response<MoviesListResponse>) {
                if (response.code() == 200) {
                    val moviesResponse= response.body()!!
                    val sortedList =moviesResponse.items.sortedBy {moviesResponse.items?.toString() }
                    populateMoviesList(sortedList)
                }
            }
            override fun onFailure(call: Call<MoviesListResponse>, t: Throwable) {
                message!!.text = t.message
                message.visibility=VISIBLE
                swipe_refresh_layout.isRefreshing=false
            }
        })


    }

    private fun populateMoviesList(items: List<Item>) {
        linearLayoutManager= LinearLayoutManager(this)
        recycler.layoutManager=linearLayoutManager
        adapter= MoviesRecyclerAdapter(applicationContext,items)
        recycler.adapter=adapter
        swipe_refresh_layout.isRefreshing=false
    }


    private fun changeSwipeColor() {
        val color = Color.parseColor("#FFFFFF")
        swipe_refresh_layout.setColorSchemeColors(color)
        swipe_refresh_layout.setProgressBackgroundColorSchemeResource(R.color.colorPrimary)
    }
}