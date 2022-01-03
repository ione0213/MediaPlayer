package com.yuchen.mediaplayer.ext

import androidx.fragment.app.Fragment
import com.yuchen.mediaplayer.MediaApplication
import com.yuchen.mediaplayer.factory.ViewModelFactory

fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as MediaApplication).mediaRepository
    return ViewModelFactory(repository)
}