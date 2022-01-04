package com.yuchen.mediaplayer.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayerViewModel : ViewModel() {

    private val _playWhenReady = MutableLiveData<Boolean>()

    val playWhenReady: LiveData<Boolean>
        get() = _playWhenReady

    private val _currentWindow = MutableLiveData<Int>()

    val currentWindow: LiveData<Int>
        get() = _currentWindow

    private val _playbackPosition = MutableLiveData<Long>()

    val playbackPosition: LiveData<Long>
        get() = _playbackPosition

    init {
        _playWhenReady.value = true
        _currentWindow.value = 0
        _playbackPosition.value = 0L
    }

    fun setPlayWhenReady(status: Boolean) {
        _playWhenReady.value = status
    }

    fun setCurrentWindow(index: Int) {
        _currentWindow.value = index
    }

    fun setPlaybackPosition(position: Long) {
        _playbackPosition.value = position
    }
}