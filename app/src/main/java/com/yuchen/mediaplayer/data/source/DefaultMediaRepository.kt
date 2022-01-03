package com.yuchen.mediaplayer.data.source

import com.yuchen.mediaplayer.data.Video
import com.yuchen.mediaplayer.data.source.remote.MediaRemoteDataSource

class DefaultMediaRepository(private val mediaRemoteDataSource: MediaRemoteDataSource) :
    MediaRepository {

    override fun getMediaList(): List<Video> {
        return mediaRemoteDataSource.getMediaList()
    }
}