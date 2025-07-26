package com.example.inappupdatedownloadingui.helper

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.in_appupdatelibrary.R
import com.example.inappupdatedownloadingui.circularprogress.CircularProgressBar

class AppUpdateLayoutHelper(activity: Activity) {

    private val updateLayout: View = activity.findViewById(R.id.layout_update_bar)
    private val statusContainer: RelativeLayout = updateLayout.findViewById(R.id.updateIcon)
    private val updateText: TextView = updateLayout.findViewById(R.id.updateText)
    private val updateIconImage: ImageView = updateLayout.findViewById(R.id.updateIconImage)
    private val restartButton: RelativeLayout = updateLayout.findViewById(R.id.restart_button)
    private val circularProgressBar: CircularProgressBar =
        updateLayout.findViewById(R.id.circularProgressBar)

    /**
     * Display the progress UI with percentage text while downloading the update.
     */
    fun showDownloading(bytesDownloaded: Long, totalBytesToDownload: Long) {
        updateLayout.visibility = View.VISIBLE
        if (totalBytesToDownload > 0) {
            val progress = ((bytesDownloaded * 100) / totalBytesToDownload).toInt()
            circularProgressBar.setMax(100)
            circularProgressBar.setProgress(progress)
        }

        circularProgressBar.visibility = View.VISIBLE
        restartButton.visibility = View.GONE
        statusContainer.setOnClickListener(null)
    }

    /**
     * Show the restart UI once update is downloaded and installed.
     */
    fun showRestartApp(onRestartClick: () -> Unit, restartTextValue: String) {
        updateLayout.visibility = View.VISIBLE
        circularProgressBar.visibility = View.GONE
        restartButton.visibility = View.VISIBLE
        updateText.text = restartTextValue
        restartButton.setOnClickListener { onRestartClick() }
    }

    /**
     * Hide the entire update layout.
     */
    fun hide() {
        updateLayout.visibility = View.GONE
    }

    /**
     * Dynamically configure appearance of the progress bar.
     * Call this once before using `showDownloading()`.
     */
    fun configureProgressBar(
        updateIconResId: Int?,
        updateTextValue: String,
        progressColor: Int,
        backgroundColor: Int,
        textSize: Float,
        progressStrokeWidth: Float,
        backgroundStrokeWidth: Float
    ) {
        circularProgressBar.setProgressColor(progressColor)
        circularProgressBar.setBackgroundColorDynamic(backgroundColor)
        circularProgressBar.setTextSize(textSize)
        circularProgressBar.setStrokeWidth(backgroundStrokeWidth, progressStrokeWidth)

        updateText.text = updateTextValue

        updateIconResId?.let {
            updateIconImage.setImageResource(it)
            updateIconImage.visibility = View.VISIBLE
        }
    }


}
