package com.yuchen.mediaplayer.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoDetail(
    val videoId: String,
    val videoName: String,
    val videoUrl: String
): Parcelable