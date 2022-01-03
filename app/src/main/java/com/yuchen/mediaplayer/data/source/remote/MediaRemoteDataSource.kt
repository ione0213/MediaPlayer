package com.yuchen.mediaplayer.data.source.remote

import com.yuchen.mediaplayer.data.Video
import com.yuchen.mediaplayer.data.VideoDetail
import com.yuchen.mediaplayer.data.source.MediaDataSource

object MediaRemoteDataSource : MediaDataSource {

    override fun getMediaList(): List<Video> =
        listOf(
            Video(
                "001",
                listOf(
                    VideoDetail(
                        "00101",
                        "Doggy S101",
                        "https://storage.googleapis.com/exoplayer-test-media-1/mp4/android-screens-10s.mp4"
                    ),
                    VideoDetail(
                        "00102",
                        "Doggy S102",
                        "https://storage.googleapis.com/exoplayer-test-media-1/mp4/android-screens-25s.mp4"
                    )
                )
            ),
            Video(
                "002",
                listOf(
                    VideoDetail(
                        "00201",
                        "Frame Counter",
                        "https://storage.googleapis.com/exoplayer-test-media-1/mp4/frame-counter-one-hour.mp4"
                    )
                )
            ),
            Video(
                "003",
                listOf(
                    VideoDetail(
                        "00301",
                        "Dizzy Cat",
                        "https://html5demos.com/assets/dizzy.mp4"
                    )
                )
            ),
            Video(
                "004",
                listOf(
                    VideoDetail(
                        "00401",
                        "Mix S101",
                        "https://storage.googleapis.com/exoplayer-test-media-1/gen-3/screens/dash-vod-single-segment/video-avc-baseline-480.mp4",
                    ),
                    VideoDetail(
                        "00402",
                        "Mix S102",
                        "https://storage.googleapis.com/exoplayer-test-media-1/mp4/dizzy-with-tx3g.mp4",
                    ),
                    VideoDetail(
                        "00403",
                        "Mix S103",
                        "https://storage.googleapis.com/exoplayer-test-media-1/gen-3/screens/dash-vod-single-segment/video-137.mp4"
                    )
                )
            ),
            Video(
                "005",
                listOf(
                    VideoDetail(
                        "00501",
                        "Big Buck Bunny",
                        "https://storage.googleapis.com/downloads.webmproject.org/av1/exoplayer/bbb-av1-480p.mp4"
                    )
                )
            )
        )
}