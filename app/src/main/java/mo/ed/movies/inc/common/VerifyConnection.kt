package mo.ed.movies.inc.common

import android.content.Context
import android.net.ConnectivityManager

class VerifyConnection(context: Context) {
    public fun isConnected(context: Context):Boolean{
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork=cm.activeNetworkInfo
        if (activeNetwork!=null){
            val isCon=activeNetwork.isConnected
            return isCon
        }else return false

    }
}