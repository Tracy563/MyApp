package com.example.myapp

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 4)

        val pm: PackageManager = packageManager
        val intent = pm.getLaunchIntentForPackage(packageName)
        val apps = pm.queryIntentActivities(
            android.content.Intent(android.content.Intent.ACTION_MAIN).apply {
                addCategory(android.content.Intent.CATEGORY_LAUNCHER)
            }, 0
        ).map {
            val name = it.loadLabel(pm).toString()
            val icon = it.activityInfo.loadIcon(pm)
            // we need drawable res, so we will use icon directly in adapter
            AppItem(name, it.activityInfo.packageName, it.activityInfo.name)
        }.sortedBy { it.name.lowercase() }

        recyclerView.adapter = AppAdapter(apps, pm)
    }
}
