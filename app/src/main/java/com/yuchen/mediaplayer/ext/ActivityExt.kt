package com.yuchen.mediaplayer.ext

import android.app.Activity
import com.yuchen.mediaplayer.MediaApplication
import com.yuchen.mediaplayer.factory.ViewModelFactory

fun Activity.getVmFactory(): ViewModelFactory {
    val repository = (applicationContext as MediaApplication).mediaRepository
    return ViewModelFactory(repository)
}