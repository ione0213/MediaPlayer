package com.yuchen.mediaplayer.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yuchen.mediaplayer.MainViewModel
import com.yuchen.mediaplayer.data.source.MediaRepository
import com.yuchen.mediaplayer.home.HomeViewModel

class ViewModelFactory constructor(
    private val mediaRepository: MediaRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel()
                isAssignableFrom(HomeViewModel::class.java) ->
                    HomeViewModel(mediaRepository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}