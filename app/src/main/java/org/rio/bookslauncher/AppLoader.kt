package org.rio.bookslauncher

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.AsyncTask

/**
 * Created by rio.chandra.r on 13/11/18.
 */

class AppLoader(val context: Context, val callback: (List<AppInfo>?) -> Unit): AsyncTask<Void, Void, List<AppInfo>>() {
    override fun doInBackground(vararg params: Void?): List<AppInfo>? {
        return getAppList()
    }

    override fun onPostExecute(result: List<AppInfo>?) {
        callback(result)
    }

    fun getAppList(): List<AppInfo>? {
        var appsList = ArrayList<AppInfo>();

        // init local store
        // get package manager pointer
        val packageMan = context.packageManager;

        // ?? i am not sure
        val i = Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        val allApps = packageMan.queryIntentActivities(i, 0);
        for(ri in allApps) {

            // no need self icon app visible on launcher
            if(ri.activityInfo.packageName == context.packageName) {
                continue
            }

            // create instance, set data, and append to output
            var app = AppInfo()
            app.label = ri.loadLabel(packageMan)
            app.packageName = ri.activityInfo.packageName
            app.icon = ri.activityInfo.loadIcon(packageMan)

            // assign counter by local database saved
            app.count = SUGARS().getCount(app.packageName.toString())

            // append
            appsList.add(app);
        }

        // sort data by largest click count
        appsList.sortByDescending { it.count }
        return appsList
    }
}

class AppCounter(val packageName: String): AsyncTask<Void, Void, Boolean>() {
    override fun doInBackground(vararg params: Void?): Boolean {
        SUGARS().increment(packageName)
        return true
    }
}

class AppInfo {
    internal var label: CharSequence? = null
    internal var packageName: CharSequence? = null
    internal var icon: Drawable? = null
    internal var count: Int? = null
}