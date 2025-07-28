package com.example.in_applibrary

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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

        appUpdateLayoutHelper.customInAppUpdateLayout(
            updateIconDrawable = com.example.in_appupdatelibrary.R.drawable.ic_app_update,
            downloadingStatusTextValue = "Update Available – Don’t Miss Out!",
            downloadingStatusTextColor = ContextCompat.getColor(this, com.example.in_appupdatelibrary.R.color.black),
            circularProgressBarColor = ContextCompat.getColor(this, com.example.in_appupdatelibrary.R.color.colorPrimaryOrange),
            circularProgressBarBackgroundColor = ContextCompat.getColor(this, com.example.in_appupdatelibrary.R.color.progress_background),
            circularProgressPercentageCountText = 26f,
            circularProgressStrokeWidth = 12f,
            circularProgressBackgroundStrokeWidth = 14f,
            mainContainerLayoutBackgroundTintColor = ContextCompat.getColor(this, com.example.in_appupdatelibrary.R.color.background_selected),
            installButtonBackgroundTintColor = ContextCompat.getColor(this, com.example.in_appupdatelibrary.R.color.colorPrimaryOrange),
            installButtonTextValue = "Install",
            installButtonTextColor = ContextCompat.getColor(this, com.example.in_appupdatelibrary.R.color.white),
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