package com.yuchen.mediaplayer.data.source

import com.yuchen.mediaplayer.data.Video

interface MediaRepository {

    fun getMediaList(): List<Video>

}