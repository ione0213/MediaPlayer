package com.yuchen.mediaplayer.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yuchen.mediaplayer.data.Video
import com.yuchen.mediaplayer.data.source.MediaRepository

class HomeViewModel(private val mediaRepository: MediaRepository): ViewModel() {

    private val _videos = MutableLiveData<List<Video>>()

    val videos: LiveData<List<Video>>
        get() = _videos

    init {
        getVideos()
    }

    private fun getVideos(){
        _videos.value = mediaRepository.getMediaList()
    }
}