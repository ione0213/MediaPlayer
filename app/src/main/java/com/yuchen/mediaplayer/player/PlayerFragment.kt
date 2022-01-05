package com.yuchen.mediaplayer.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.yuchen.mediaplayer.R
import com.yuchen.mediaplayer.data.Video
import com.yuchen.mediaplayer.databinding.FragmentPlayerBinding
import com.yuchen.mediaplayer.ext.getVmFactory
import com.yuchen.mediaplayer.util.Logger


class PlayerFragment : Fragment() {

    private val viewModel by viewModels<PlayerViewModel> { getVmFactory() }

    private var player: ExoPlayer? = null
    private lateinit var binding: FragmentPlayerBinding
    private lateinit var video: Video

    private val playbackStateListener: Player.Listener = playbackStateListener()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPlayerBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        video = PlayerFragmentArgs.fromBundle(requireArguments()).video

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
        Logger.i("onStop()")
        releasePlayer()
    }

    private fun initializePlayer() {

        // TrackSelector for BASH format
//        val trackSelector = DefaultTrackSelector(requireContext()).apply {
//            setParameters(buildUponParameters().setMaxVideoSizeSd())
//        }

        player = ExoPlayer.Builder(requireContext())
            .setSeekForwardIncrementMs(10000)
            .setSeekBackIncrementMs(10000)
            .build()
            .also { exoPlayer ->
                binding.videoView.player = exoPlayer

                video.videoList.forEach {
                    exoPlayer.addMediaItem(
                        MediaItem.Builder()
                            .setUri(it.videoUrl)
                            .build()
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
            viewModel.setCurrentWindow(this.currentWindowIndex)
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