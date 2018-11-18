package org.rio.bookslauncher.launchers

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import org.rio.bookslauncher.R

/**
 * Created by rio.chandra.r on 13/11/18.
 */

class LauncherItem(itemView: View): RecyclerView.ViewHolder(itemView) {
    val view: View get() = itemView
    val textView: TextView get() = itemView.findViewById(R.id.litem_text_view)
    val imageView: ImageView get() = itemView.findViewById(R.id.litem_image_view)
}