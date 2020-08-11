package mo.ed.movies.inc.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.activity_movie_detail.*
import mo.ed.movies.inc.R
import mo.ed.movies.inc.responses.Item
import mo.ed.movies.inc.util.Constants

class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        ButterKnife.bind(this)
        val intent: Intent= intent
        var movie=intent.extras?.getParcelable<Item>(Constants.MOVIE_KEY)
        tvMovieTitle.text= movie?.title
    }
}