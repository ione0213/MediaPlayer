package com.yuchen.mediaplayer.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.HttpDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.yuchen.mediaplayer.MediaApplication
import com.yuchen.mediaplayer.R
import com.yuchen.mediaplayer.data.Video
import com.yuchen.mediaplayer.databinding.FragmentPlayerBinding
import com.yuchen.mediaplayer.ext.getVmFactory
import com.yuchen.mediaplayer.util.DoubleClickListener
import com.yuchen.mediaplayer.util.Logger
import java.util.*


class PlayerFragment : Fragment() {

    private val viewModel by viewModels<PlayerViewModel> { getVmFactory() }

    private var player: ExoPlayer? = null
    private lateinit var httpDataSourceFactory: HttpDataSource.Factory
    private lateinit var defaultDataSourceFactory: DefaultDataSource.Factory
    private lateinit var cacheDataSourceFactory: DataSource.Factory
    private val simpleCache: SimpleCache = MediaApplication.simpleCache

    private lateinit var binding: FragmentPlayerBinding
    private lateinit var video: Video

    private var isFillScreen = false

    private val playbackStateListener: Player.Listener by lazy { playbackStateListener() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPlayerBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        video = PlayerFragmentArgs.fromBundle(requireArguments()).video

        //Switch video view size with double click event
        binding.videoView.setOnClickListener(object: DoubleClickListener() {
            override fun onDoubleClick(v: View?) {
                binding.videoView.resizeMode = when (isFillScreen) {
                    false -> {
                        isFillScreen = true
                        AspectRatioFrameLayout.RESIZE_MODE_FILL
                    }
                    true -> {
                        isFillScreen = false
                        AspectRatioFrameLayout.RESIZE_MODE_FIT
                    }
                }
            }
        })

        val videoView = binding.videoView
        val button = videoView.findViewById<ImageButton>(R.id.close)
        button.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    private fun initializePlayer() {

        // TrackSelector for BASH format
//        val trackSelector = DefaultTrackSelector(requireContext()).apply {
//            setParameters(buildUponParameters().setMaxVideoSizeSd())
//        }

        httpDataSourceFactory = DefaultHttpDataSource.Factory()
            .setAllowCrossProtocolRedirects(true)

        defaultDataSourceFactory = DefaultDataSource.Factory(
            requireContext(), httpDataSourceFactory
        )

        //A DataSource that reads and writes a Cache.
        cacheDataSourceFactory = CacheDataSource.Factory()
            .setCache(simpleCache)
            .setUpstreamDataSourceFactory(httpDataSourceFactory)
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)

        player = ExoPlayer.Builder(requireContext())
            .setSeekForwardIncrementMs(10000)
            .setSeekBackIncrementMs(10000)
            .setMediaSourceFactory(DefaultMediaSourceFactory(cacheDataSourceFactory))
            .build()
            .also { exoPlayer ->
                binding.videoView.player = exoPlayer

                video.videoList.forEach {
                    exoPlayer.addMediaSource(
                        ProgressiveMediaSource.Factory(cacheDataSourceFactory)
                            .createMediaSource(
                                MediaItem.Builder()
                                    .setUri(it.videoUrl)
                                    .build()
                            )
                    )
                }

                exoPlayer.apply {
                    playWhenReady = viewModel.playWhenReady.value ?: true
                    seekTo(
                        viewModel.currentWindow.value ?: 0,
                        viewModel.playbackPosition.value ?: 0L
                    )
                    addListener(playbackStateListener)
                    prepare()
                }
            }
    }

    private fun hideSystemUi() {
        binding.videoView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    private fun releasePlayer() {
        player?.run {
            viewModel.setPlaybackPosition(this.currentPosition)
            viewModel.setCurrentWindow(this.currentMediaItemIndex)
            viewModel.setPlayWhenReady(this.playWhenReady)
            removeListener(playbackStateListener)
            release()
        }
        player = null
    }

    private fun playbackStateListener() = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {

            viewModel.setLoadingStatus(playbackState)
        }
    }
}