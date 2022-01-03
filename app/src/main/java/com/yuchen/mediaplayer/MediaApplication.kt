package com.yuchen.mediaplayer

import android.app.Application
import com.yuchen.mediaplayer.data.source.MediaRepository
import com.yuchen.mediaplayer.util.ServiceLocator
import kotlin.properties.Delegates

class MediaApplication: Application() {

    val mediaRepository: MediaRepository
        get() = ServiceLocator.provideTasksRepository(this)

    companion object {
        var instance: MediaApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}