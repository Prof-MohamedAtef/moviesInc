package mo.ed.movies.inc.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_movie_detail.*
import mo.ed.movies.inc.R
import mo.ed.movies.inc.responses.moviesListResponse.Item
import mo.ed.movies.inc.ui.fragments.ActorsFragment
import mo.ed.movies.inc.util.Constants
import java.util.*


class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        ButterKnife.bind(this)

        val mToolbar=detail_toolbar
        if (Locale.getDefault().getLanguage().contentEquals("en")){
            mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_left));
        }else if (Locale.getDefault().getLanguage().contentEquals("ar")){
            mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_right));
        }
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        mToolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                onBackPressed()
            }
        })

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
}