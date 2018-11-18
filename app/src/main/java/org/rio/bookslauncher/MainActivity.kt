package org.rio.bookslauncher

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.orm.SugarContext
import org.rio.bookslauncher.launchers.LauncherAdapter


class MainActivity : AppCompatActivity() {

    var adapter: LauncherAdapter? = null

    /*
     * number how many grid are used
     * on launcher recycler view
     */
    val NUMBER_OF_GRID = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
         * initialize local database
         */
        SugarContext.init(this)

        /*
         * initialize view
         */
        fire()
    }

    fun fire() {
        /*
         * save reference to adapter
         */
        this.adapter = LauncherAdapter(applicationContext)

        /*
         * set view grid layout and adapter
         */
        val recView = findViewById<RecyclerView>(R.id.recycler)
        recView.layoutManager = GridLayoutManager(applicationContext, NUMBER_OF_GRID)
        recView.adapter = this.adapter
    }

    fun refreshLauncherData () {
        /*
         * update adapter icons running background
         * prevent delay on UI thread
         */
        AppLoader(applicationContext, {
            // is a List<AppInfo>?
            appList ->

            // idk why i need to do a null check
            if (this.adapter != null) {
                this.adapter!!.refreshData(appList)
            }
        }).execute()
    }

    override fun onResume() {
        super.onResume()
        /*
         * background process will be running
         * every home resume
         */
        refreshLauncherData()
    }

}
