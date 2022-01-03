package com.yuchen.mediaplayer.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yuchen.mediaplayer.databinding.FragmentHomeBinding
import com.yuchen.mediaplayer.ext.getVmFactory
import com.yuchen.mediaplayer.util.Logger

class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        val adapter = HomeAdapter()
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = viewModel
            recyclerHome.adapter = adapter
        }

        viewModel.videos.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }

        return binding.root
    }
}