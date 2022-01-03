package com.yuchen.mediaplayer.home


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yuchen.mediaplayer.data.Video
import com.yuchen.mediaplayer.databinding.ItemHomeVideoBinding
import com.yuchen.mediaplayer.util.Logger

class HomeAdapter : ListAdapter<Video, RecyclerView.ViewHolder>(DiffCallback) {

    class VideoViewHolder(private var binding: ItemHomeVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(video: Video) {
            binding.video = video
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Video>() {
        override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem.groupId == newItem.groupId
        }

        override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VideoViewHolder(
            ItemHomeVideoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Logger.i("holder: $holder")
        when (holder) {
            is VideoViewHolder -> {
                Logger.i("123455: ${getItem(position).groupId}")
                holder.bind(getItem(position))
            }
        }
    }
}