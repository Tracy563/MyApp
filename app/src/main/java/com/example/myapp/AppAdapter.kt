package com.example.myapp

import android.content.Intent
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class AppItem(val name: String, val packageName: String, val activityName: String)

class AppAdapter(private val apps: List<AppItem>, private val pm: PackageManager) :
    RecyclerView.Adapter<AppAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.appIcon)
        val name: TextView = view.findViewById(R.id.appName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_app, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val app = apps[position]
        holder.name.text = app.name
        try {
            val appInfo = pm.getApplicationInfo(app.packageName, 0)
            holder.icon.setImageDrawable(pm.getApplicationIcon(appInfo))
        } catch (e: Exception) {
            holder.icon.setImageResource(R.mipmap.ic_launcher)
        }

        holder.itemView.setOnClickListener {
            val intent = pm.getLaunchIntentForPackage(app.packageName)
            if (intent != null) {
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = apps.size
}
