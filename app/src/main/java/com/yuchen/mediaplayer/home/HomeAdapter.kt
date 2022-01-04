package com.yuchen.mediaplayer.home


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yuchen.mediaplayer.data.Video
import com.yuchen.mediaplayer.data.VideoDetail
import com.yuchen.mediaplayer.databinding.ItemHomeVideoBinding

class HomeAdapter(private val onClickListener: OnClickListener) : ListAdapter<Video, RecyclerView.ViewHolder>(DiffCallback) {

    class OnClickListener(val clickListener: (video: Video) -> Unit) {
        fun onClick(video: Video) = clickListener(video)
    }

    class VideoViewHolder(private var binding: ItemHomeVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(video: Video, onClickListener: OnClickListener) {
            binding.root.setOnClickListener { onClickListener.onClick(video)}
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
        when (holder) {
            is VideoViewHolder -> {
                holder.bind(getItem(position), onClickListener)
            }
        }
    }
}