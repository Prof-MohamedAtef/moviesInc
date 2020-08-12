package mo.ed.movies.inc.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_movie_detail.*
import mo.ed.movies.inc.BuildConfig
import mo.ed.movies.inc.R
import mo.ed.movies.inc.common.VerifyConnection
import mo.ed.movies.inc.common.network.Http
import mo.ed.movies.inc.common.network.RetrofitService
import mo.ed.movies.inc.responses.moviesListResponse.Item
import mo.ed.movies.inc.responses.moviesListResponse.MoviesListResponse
import mo.ed.movies.inc.responses.rate.RateBody
import mo.ed.movies.inc.responses.rate.RateResponse
import mo.ed.movies.inc.ui.fragments.ActorsFragment
import mo.ed.movies.inc.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MovieDetailActivity : AppCompatActivity() {



    private lateinit var connectivity: VerifyConnection
    private var ratingValue = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        ButterKnife.bind(this)
        connectivity=VerifyConnection(applicationContext)

        initializedAppBar()

        val intent: Intent= intent
        var movie=intent.extras?.getParcelable<Item>(Constants.MOVIE_KEY)
        movieTitle.text= movie?.title
        tvScreenTitle.text= movie?.title
        movieReleaseYear.text=movie?.releaseDate
        val list=movie?.genreIds
//        for (i in 0 until list!!.size){
//            movieGenre.append(list[i].toString())
//        }
        movieReleaseYear.text=movie?.releaseDate
        movieOverview.text=movie?.overview

        val completePath= Constants.POSTER_BASE_PATH +movie?.posterPath
        Glide.with(applicationContext)
//                .setDefaultRequestOptions(options)

            .load(completePath)
            .into(moviePoster)

        if (savedInstanceState!=null){

        }else{
            var movieId=movie!!.id
            val actorsFragment=ActorsFragment()
            val bundle=Bundle()
            bundle.putLong(Constants.MOVIE_ID, movieId)
            actorsFragment.arguments=bundle
            supportFragmentManager.beginTransaction().replace(R.id.container_frame,actorsFragment )
                .commitAllowingStateLoss()

            ratingBar_(movieId)
        }
    }

    private fun ratingBar_(movieId: Long) {
        ratingBar.setOnRatingBarChangeListener(RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser ->
            ratingValue=rating
            if (ratingValue>0){
                if (connectivity.isConnected(applicationContext)){
                    initiateRating(ratingValue, movieId)
                }else{
                    ShowSnackBar(getString(R.string.internet_disabled))
                }
            }
        })
    }

    private fun initiateRating(ratingValue: Float, movieId: Long) {
        //todo
        var apiKey = BuildConfig.MoviesApiKey;
        val retrofitService = Http.create(RetrofitService::class.java)
        val rateBody= RateBody(ratingValue.toDouble())
        val call=retrofitService.rateMovie(movieId, apiKey, rateBody)
        swipe_refresh_layout.isRefreshing=true
        call.enqueue(object : Callback<RateResponse>{
            override fun onResponse(call: Call<RateResponse>, response: Response<RateResponse>) {
                if (response.code() == 200) {
                    val rateResponse= response.body()!!
                    if (!rateResponse.success){
                        ShowSnackBar(rateResponse.statusMessage.toString())
                        swipe_refresh_layout.isRefreshing=false
                    }else{
                        ShowSnackBar(rateResponse.statusMessage.toString())
                        swipe_refresh_layout.isRefreshing=false
                    }
                }else if (response.code()==401){
                    ShowSnackBar(getString(R.string.error_401))
                    swipe_refresh_layout.isRefreshing=false
                }
            }
            override fun onFailure(call: Call<RateResponse>, t: Throwable) {
                ShowSnackBar(t.message.toString())
                swipe_refresh_layout.isRefreshing=false
            }
        })
    }

    private fun ShowSnackBar(message: String){
        var parentLayout= View(applicationContext)
        parentLayout = findViewById<View>(android.R.id.content)
        Snackbar.make(parentLayout, message.toString(), Snackbar.LENGTH_LONG).show()
    }

    private fun initializedAppBar() {
        val mToolbar = detail_toolbar
        if (Locale.getDefault().getLanguage().contentEquals("en")) {
            mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_left));
        } else if (Locale.getDefault().getLanguage().contentEquals("ar")) {
            mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_right));
        }
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        mToolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                onBackPressed()
            }
        })
    }
}