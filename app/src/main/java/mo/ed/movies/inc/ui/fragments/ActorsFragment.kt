package mo.ed.movies.inc.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.activity_main.*
import mo.ed.movies.inc.BuildConfig
import mo.ed.movies.inc.R
import mo.ed.movies.inc.common.network.Http
import mo.ed.movies.inc.common.network.RetrofitService
import mo.ed.movies.inc.responses.crewListResponse.Cast
import mo.ed.movies.inc.responses.crewListResponse.CrewListResponse
import mo.ed.movies.inc.responses.moviesListResponse.MoviesListResponse
import mo.ed.movies.inc.ui.adapter.ActorsRecyclerAdapter
import mo.ed.movies.inc.ui.adapter.MoviesRecyclerAdapter
import mo.ed.movies.inc.util.Constants
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class ActorsFragment : Fragment() {

    private lateinit var adapter: ActorsRecyclerAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var movieId: Long=0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View
        view=inflater.inflate(R.layout.actress_fragment,container,false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val bundle= arguments
        if (bundle!=null){
            movieId= bundle.getLong(Constants.MOVIE_ID)
        }

        var apiKey = BuildConfig.MoviesApiKey;
        getCrewList(apiKey = apiKey, movieId = movieId)

        changeSwipeColor()
        swipe_refresh_layout.setOnRefreshListener{
            getCrewList(apiKey = apiKey, movieId = movieId)
        }
    }

    private fun changeSwipeColor() {
        val color = Color.parseColor("#FFFFFF")
        swipe_refresh_layout.setColorSchemeColors(color)
        swipe_refresh_layout.setProgressBackgroundColorSchemeResource(R.color.colorPrimary)
    }

    private fun getCrewList(apiKey: String, movieId: Long) {
        val retrofitService = Http.create(RetrofitService::class.java)
        val call=retrofitService.getCrewList( movieId, apiKey)
        swipe_refresh_layout.isRefreshing=true
        call.enqueue(object : retrofit2.Callback<CrewListResponse>{
            override fun onResponse(call: Call<CrewListResponse>, response: Response<CrewListResponse>) {
                if (response.code() == 200) {
                    val teamResponse= response.body()!!
                    val sortedCast =teamResponse.cast.sortedBy {teamResponse.cast?.toString() }
                    populateCastTeamList(sortedCast)
                }
            }
            override fun onFailure(call: Call<CrewListResponse>, t: Throwable) {
                message!!.text = t.message
                message.visibility= View.VISIBLE
                swipe_refresh_layout.isRefreshing=false
            }
        })
    }

    private fun populateCastTeamList(sortedCast: List<Cast>) {
        linearLayoutManager= LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,true)
        recycler.layoutManager=linearLayoutManager
        adapter= ActorsRecyclerAdapter(requireContext(),sortedCast)
        recycler.adapter=adapter
        swipe_refresh_layout.isRefreshing=false
    }
}