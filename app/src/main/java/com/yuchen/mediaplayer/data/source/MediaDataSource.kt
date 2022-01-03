package com.yuchen.mediaplayer.data.source

import com.yuchen.mediaplayer.data.Video

interface MediaDataSource {

    fun getMediaList(): List<Video>
}