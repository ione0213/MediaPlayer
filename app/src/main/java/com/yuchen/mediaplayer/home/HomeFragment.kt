package com.yuchen.mediaplayer.home

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yuchen.mediaplayer.NavigationDirections
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
        val adapter = HomeAdapter(
            HomeAdapter.OnClickListener {
                viewModel.navToPlayer(it)
            })
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

        viewModel.navToPlayer.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(NavigationDirections.navToPlayerFragment(it))
                viewModel.onNavToPlayer()
            }
        }

        return binding.root
    }
}