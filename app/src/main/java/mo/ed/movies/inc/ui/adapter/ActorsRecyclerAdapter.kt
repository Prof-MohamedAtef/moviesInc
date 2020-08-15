package mo.ed.movies.inc.ui.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.actor_item.view.*
import kotlinx.android.synthetic.main.list_item.view.*
import mo.ed.movies.inc.R
import mo.ed.movies.inc.responses.crewListResponse.Cast
import mo.ed.movies.inc.responses.moviesListResponse.Item
import mo.ed.movies.inc.ui.activities.MovieDetailActivity
import mo.ed.movies.inc.util.Constants

class ActorsRecyclerAdapter(val context : Context, private val actors:List<Cast>):RecyclerView.Adapter<ActorsRecyclerAdapter.MoviesHolder>() {
    class MoviesHolder (view: View):RecyclerView.ViewHolder(view), View.OnClickListener {
        override fun onClick(v: View?) {
            Log.d("RecyclerView", "CLICK!")
        }

        private val  actorProfile=view.actorProfile
        private val actorName=view.actorName
        private val actorCharacter=view.actorCharacter
        public val actor_constraint=view.actor_constraint_layout

        fun bind(actor: Cast, context: Context){
            val completePath= Constants.POSTER_BASE_PATH +actor.profilePath
            Glide.with(context)
                .load(completePath)
                .into(actorProfile)
            actorName.text=actor.name
            actorCharacter.text=actor.character
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        val view:View=LayoutInflater.from(parent.context).inflate(R.layout.actor_item, parent, false)
        val movies= MoviesHolder(view)
        return movies
    }

    override fun getItemCount()=actors.size

    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
        holder.bind(actors[position],context)
        holder.actor_constraint.setOnClickListener {
            val actorName= actors[position].name
            Toast.makeText(context,  actorName, Toast.LENGTH_LONG).show()
        }
    }
}