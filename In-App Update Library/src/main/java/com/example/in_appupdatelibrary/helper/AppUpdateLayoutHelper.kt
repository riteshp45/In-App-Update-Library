package com.example.inappupdatedownloadingui.helper

import android.app.Activity
import android.view.View
import android.widget.RelativeLayout
import com.example.in_appupdatelibrary.R
import com.example.inappupdatedownloadingui.circularprogress.CircularProgressBar

class AppUpdateLayoutHelper(activity: Activity) {
    private val updateLayout: View = activity.findViewById(R.id.layout_update_bar)
    private val statusContainer: RelativeLayout = updateLayout.findViewById(R.id.updateIcon)
    private val restartButton: RelativeLayout = updateLayout.findViewById(R.id.restart_button)
    private var circularProgressBar = updateLayout.findViewById<CircularProgressBar>(R.id.circularProgressBar)

    fun showDownloading(bytesDownloaded: Long, totalBytesToDownload: Long) {
        updateLayout.visibility = View.VISIBLE
        if (totalBytesToDownload > 0) {
            val progress = ((bytesDownloaded * 100) / totalBytesToDownload).toInt()
            circularProgressBar.setProgress(progress)
            circularProgressBar.setMax(100)
        }
        circularProgressBar.visibility = View.VISIBLE
        restartButton.visibility = View.GONE
        statusContainer.setOnClickListener(null)
    }

    fun showRestartApp(onRestartClick: () -> Unit) {
        updateLayout.visibility = View.VISIBLE
        circularProgressBar.visibility = View.GONE
        restartButton.visibility = View.VISIBLE
        restartButton.setOnClickListener { onRestartClick() }
    }

    fun hide() {
        updateLayout.visibility = View.GONE
    }
}