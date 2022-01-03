package com.yuchen.mediaplayer

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.yuchen.mediaplayer.data.Video
import com.yuchen.mediaplayer.util.Logger

@BindingAdapter("video")
fun TextView.bindVideoName(video: Video) {
    Logger.i("video: $video")
    text = video.videoList.first().videoName
}