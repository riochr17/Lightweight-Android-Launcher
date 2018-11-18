package org.rio.bookslauncher.launchers

/**
 * Created by rio.chandra.r on 13/11/18.
 */

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import android.support.v4.content.ContextCompat.startActivity
import android.net.Uri.fromParts
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import org.rio.bookslauncher.*


class LauncherAdapter(val context: Context): RecyclerView.Adapter<LauncherItem>() {
    var appInfos: List<AppInfo>? = null

    override fun getItemCount(): Int {
        if(this.appInfos == null) {
            return 0
        }

        return this.appInfos!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): LauncherItem {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.launcher_item, parent, false)
        return LauncherItem(view)
    }

    override fun onBindViewHolder(holder: LauncherItem?, position: Int) {
        val launcherData = appInfos!!.get(position)
        holder!!.imageView.setImageDrawable(launcherData.icon)
        holder!!.textView.text = launcherData.label
        holder!!.view.setOnClickListener {
            view ->

            // increase click counter
            AppCounter(launcherData.packageName.toString()).execute()

            Toast.makeText(view.context, "goto ${launcherData.label}", Toast.LENGTH_SHORT)
            val context = view.context
            val launchIntent = context.getPackageManager().getLaunchIntentForPackage(launcherData.packageName.toString())
            context.startActivity(launchIntent)
        }
        holder!!.view.setOnLongClickListener { view ->
            val intent = Intent()
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", launcherData.packageName.toString(), null)
            intent.setData(uri)
            context.startActivity(intent)

            true
        }
    }

    fun refreshData(appInfos: List<AppInfo>?) {
        this.appInfos = appInfos
        this.notifyDataSetChanged()
    }
}