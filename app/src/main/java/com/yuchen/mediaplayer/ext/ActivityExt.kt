package com.yuchen.mediaplayer.ext

import android.app.Activity
import com.yuchen.mediaplayer.factory.ViewModelFactory

fun Activity.getVmFactory(): ViewModelFactory {
    return ViewModelFactory()
}