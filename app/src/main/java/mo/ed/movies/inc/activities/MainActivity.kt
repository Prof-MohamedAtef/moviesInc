package mo.ed.movies.inc.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item.*
import mo.ed.movies.inc.R
import mo.ed.movies.inc.common.network.RetrofitService

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)


    }

    fun getMoviesList(){
        val service:RetrofitService
        service.movies.enqueue()
    }
}