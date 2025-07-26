package com.example.in_applibrary

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.inappupdatedownloadingui.helper.AppUpdateLayoutHelper


class MainActivity : AppCompatActivity() {

    private lateinit var appUpdateLayoutHelper: AppUpdateLayoutHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize the AppUpdateLayoutHelper first
        appUpdateLayoutHelper = AppUpdateLayoutHelper(this)

        appUpdateLayoutHelper.configureProgressBar(
            com.example.in_appupdatelibrary.R.drawable.ic_app_update,
            "Update Available – Don’t Miss Out!",
            ContextCompat.getColor(this, com.example.in_appupdatelibrary.R.color.colorPrimary),
            ContextCompat.getColor(this, com.example.in_appupdatelibrary.R.color.colorAccent),
            26f,
            12f,
            14f
        )
        // Start the download simulation
        simulateDownloadProgress(appUpdateLayoutHelper)

    }

    private fun simulateDownloadProgress(updateLayoutHelper: AppUpdateLayoutHelper) {
        val handler = Handler(Looper.getMainLooper())
        val totalBytes = 100L
        var downloadedBytes = 0L // Using a var instead of an array

        handler.postDelayed(object : Runnable {
            override fun run() {
                downloadedBytes += 5
                updateLayoutHelper.showDownloading(downloadedBytes, totalBytes)

                if (downloadedBytes < totalBytes) {
                    handler.postDelayed(this, 500)
                } else {
                    updateLayoutHelper.showRestartApp(
                        onRestartClick = {
                            updateLayoutHelper.hide()
                        },
                        restartTextValue = "Restart to complete the update"
                    )
                    /*updateLayoutHelper.showRestartApp {
                        updateLayoutHelper.hide()
                        null
                    }*/
                }
            }
        }, 500)
    }
}