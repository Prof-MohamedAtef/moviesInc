package mo.ed.movies.inc.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item.view.*
import mo.ed.movies.inc.R
import mo.ed.movies.inc.responses.Item
import mo.ed.movies.inc.util.Constants

class MoviesRecyclerAdapter(val context : Context, private val movies:List<Item>):RecyclerView.Adapter<MoviesRecyclerAdapter.MoviesHolder>() {
    class MoviesHolder (view: View):RecyclerView.ViewHolder(view), View.OnClickListener {
        override fun onClick(v: View?) {
            Log.d("RecyclerView", "CLICK!")
        }

        private val  moviePoster=view.moviePoster
        private val movieTitle=view.movieTitle
        private val movieRate=view.vote_average_value
        private val movieDate=view.vote_release_value

        fun bind(movie: Item, context: Context){
//            val options = RequestOptions()
//                .placeholder(R.drawable.image_loader)
//                .circleCrop()
//                .error(R.drawable.image_loader)
            val completePath= Constants.POSTER_BASE_PATH +movie.posterPath
            Glide.with(context)
//                .setDefaultRequestOptions(options)

                .load(completePath)
                .into(moviePoster)
            Log.e("MoviePoster", movie.posterPath)
            movieTitle.text=movie.title
            movieDate.text=movie.releaseDate
            movieRate.text=  movie.voteAverage.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        val view:View=LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        val movies= MoviesHolder(view)
        return movies
    }

    override fun getItemCount()=movies.size

    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
        holder.bind(movies[position],context)
    }
}