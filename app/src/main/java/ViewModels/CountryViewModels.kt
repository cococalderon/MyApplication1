package ViewModels

import Interface.IonClick
import Library.Ref.Companion.URL_COUNTRYJSON
import Models.Country
import ViewModels.Adapter.CountryAdapter
import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.quantusti.myapplication1.R
import com.quantusti.myapplication1.databinding.ActivityCountryBinding
import org.json.JSONArray

class CountryViewModels : ViewModel, IonClick,
    SwipeRefreshLayout.OnRefreshListener {

    private var _activity : Activity? = null
    private var _bindingtable: ActivityCountryBinding? = null
    private lateinit var countries: ArrayList<Country>
    private var _recycler: RecyclerView? = null
    private var _lManager: RecyclerView.LayoutManager? = null
    private var _countryAdapter: CountryAdapter? = null
    private var _progressBar: ProgressBar? = null
    private var _swipeRefresh: SwipeRefreshLayout? = null

    /***************************Constructor Table*********************************/
    constructor(activity: Activity, bindingTable: ActivityCountryBinding?){
        _activity = activity
        _bindingtable = bindingTable
        _recycler = _bindingtable!!.recyclerViews
        _recycler!!.setHasFixedSize(true)
        _progressBar = _bindingtable!!.progressBars
        _swipeRefresh = _bindingtable!!.swipeRefresh
        _lManager = LinearLayoutManager(activity)
        _recycler!!.layoutManager = _lManager

        _progressBar!!.visibility = ProgressBar.INVISIBLE
        _swipeRefresh!!.setOnRefreshListener(this)
        downloadTaskUsingGson()
    }

    /***************************Actions*********************************/
    override fun onClick(view: View) {
        when (view.id){
            R.id.searchBtn -> searchOnClick()
        }
    }

    fun searchOnClick(){
        //Parse Data Manually
        //downloadTask()

        //Parse Data with Gson Library
        downloadTaskUsingGson()
    }

    //this function is parsing data manually
    fun downloadTask(){
        _progressBar!!.visibility = ProgressBar.VISIBLE
        // Create new request queue
        val requestQueue = Volley.newRequestQueue(_activity)
        // New JsonArrayRequest request
        var jsArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            URL_COUNTRYJSON,
            null,
            Response.Listener { response ->
                val data = response.toString()
                var jArray = JSONArray(data)
                for (i in 0..jArray.length()-1){
                    var jobject = jArray.getJSONObject(i)
                    val country = Country(
                                        jobject.getString("name"),
                                        jobject.getString("region"),
                                        jobject.getString("code"),
                                        jobject.getString("capital")
                    )
                    countries.add(country)
                    initRecyclerView(countries)
                }
            },
            Response.ErrorListener { error ->
                Log.d(
                    TAG,
                    "Error Request JSON: " + error.message
                )
            }
        )
        // Add request to queue
        requestQueue?.add(jsArrayRequest)
        _progressBar!!.visibility = ProgressBar.INVISIBLE
    }

    //this function is parsing data using GSON Library
    fun downloadTaskUsingGson(){
        _progressBar!!.visibility = ProgressBar.VISIBLE
        // Create new request queue
        val requestQueue = Volley.newRequestQueue(_activity)
        // New JsonArrayRequest request
        var jsArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            URL_COUNTRYJSON,
            null,
            Response.Listener { response ->
                val data = response.toString()
                val typeToken = object : TypeToken<ArrayList<Country>>() {}.type
                countries = Gson().fromJson<ArrayList<Country>>(data, typeToken)
                initRecyclerView(countries)
            },
            Response.ErrorListener { error ->
                Log.d(
                    TAG,
                    "Error Request JSON: " + error.message
                )
            }
        )
        // Add request to queue
        requestQueue?.add(jsArrayRequest)
        _progressBar!!.visibility = ProgressBar.INVISIBLE
    }

    /***************************Table*********************************/
    private fun initRecyclerView(list: MutableList<Country>){
        _countryAdapter = CountryAdapter(list)
        _recycler!!.adapter = _countryAdapter
        _swipeRefresh!!.isRefreshing = false
    }

    override fun onRefresh() {
        searchOnClick()
    }
}