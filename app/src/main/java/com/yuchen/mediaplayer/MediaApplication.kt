package com.yuchen.mediaplayer

import android.app.Application
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.database.StandaloneDatabaseProvider
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.yuchen.mediaplayer.data.source.MediaRepository
import com.yuchen.mediaplayer.util.ServiceLocator
import kotlin.properties.Delegates

class MediaApplication: Application() {

    val mediaRepository: MediaRepository
        get() = ServiceLocator.provideTasksRepository(this)

    companion object {
        var instance: MediaApplication by Delegates.notNull()

        lateinit var simpleCache: SimpleCache
        const val exoPlayerCacheSize: Long = 90 * 1024 * 1024
        lateinit var leastRecentlyUsedCacheEvictor: LeastRecentlyUsedCacheEvictor
        lateinit var exoDatabaseProvider: StandaloneDatabaseProvider
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        leastRecentlyUsedCacheEvictor = LeastRecentlyUsedCacheEvictor(exoPlayerCacheSize)
        exoDatabaseProvider = StandaloneDatabaseProvider(this)
        simpleCache = SimpleCache(cacheDir, leastRecentlyUsedCacheEvictor, exoDatabaseProvider)
    }
}