package com.yuchen.mediaplayer.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yuchen.mediaplayer.data.Video
import com.yuchen.mediaplayer.data.VideoDetail
import com.yuchen.mediaplayer.data.source.MediaRepository

class HomeViewModel(private val mediaRepository: MediaRepository): ViewModel() {

    private val _videos = MutableLiveData<List<Video>>()

    val videos: LiveData<List<Video>>
        get() = _videos

    private val _navToPlayer = MutableLiveData<Video?>()

    val navToPlayer: LiveData<Video?>
        get() = _navToPlayer

    init {
        getVideos()
    }

    private fun getVideos(){
        _videos.value = mediaRepository.getMediaList()
    }

    fun navToPlayer(video: Video) {
        _navToPlayer.value = video
    }

    fun onNavToPlayer() {
        _navToPlayer.value = null
    }
}