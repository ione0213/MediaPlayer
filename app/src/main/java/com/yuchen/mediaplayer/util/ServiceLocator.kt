package com.yuchen.mediaplayer.util

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.yuchen.mediaplayer.data.source.DefaultMediaRepository
import com.yuchen.mediaplayer.data.source.MediaRepository
import com.yuchen.mediaplayer.data.source.remote.MediaRemoteDataSource

object ServiceLocator {

    @Volatile
    var mediaRepository: MediaRepository? = null
        @VisibleForTesting set

    fun provideTasksRepository(context: Context): MediaRepository {
        synchronized(this) {
            return mediaRepository
                ?: createMediaRepository(context)
        }
    }

    private fun createMediaRepository(context: Context): MediaRepository {
        return DefaultMediaRepository(MediaRemoteDataSource)
    }
}