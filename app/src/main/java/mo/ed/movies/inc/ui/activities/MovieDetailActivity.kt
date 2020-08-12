package mo.ed.movies.inc.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_movie_detail.*
import mo.ed.movies.inc.R
import mo.ed.movies.inc.common.VerifyConnection
import mo.ed.movies.inc.responses.moviesListResponse.Item
import mo.ed.movies.inc.ui.fragments.ActorsFragment
import mo.ed.movies.inc.util.Constants
import java.util.*


class MovieDetailActivity : AppCompatActivity() {


    private var parentLayout= View(applicationContext)
    private lateinit var connectivity: VerifyConnection
    private var ratingValue = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        ButterKnife.bind(this)
        connectivity=VerifyConnection(applicationContext)

        initializedAppBar()
        parentLayout = findViewById<View>(android.R.id.content)
        ratingBar_()

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
        }
    }

    private fun ratingBar_() {
        ratingBar.setOnRatingBarChangeListener(RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser ->
            ratingValue=rating
            if (ratingValue>0){
                if (connectivity.isConnected(applicationContext)){
                    initiateRating(ratingValue)
                }else{
                    ShowSnackBar(getString(R.string.internet_disabled))
                }
            }
        })
    }

    private fun initiateRating(ratingValue: Float) {
        //todo
    }

    private fun ShowSnackBar(message: String){
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