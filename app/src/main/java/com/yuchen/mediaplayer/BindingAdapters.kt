package com.yuchen.mediaplayer

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.exoplayer2.ui.PlayerView
import com.yuchen.mediaplayer.data.Video
import com.yuchen.mediaplayer.player.LoadingStatus
import com.yuchen.mediaplayer.util.Logger

@BindingAdapter("video")
fun TextView.bindVideoName(video: Video) {
    Logger.i("video: $video")
    text = video.videoList.first().videoName
}

@BindingAdapter("setupStatus")
fun bindStatus(view: ProgressBar, status: LoadingStatus?) {
    view.visibility = when (status) {
        LoadingStatus.LOADING -> View.VISIBLE
        LoadingStatus.DONE -> View.GONE
        else -> View.GONE
    }
}

@BindingAdapter("setPlayer")
fun bindPlayer(view: PlayerView, status: LoadingStatus?) {
    view.isEnabled = when (status) {
        LoadingStatus.LOADING -> false
        LoadingStatus.DONE -> true
        else -> true
    }
}