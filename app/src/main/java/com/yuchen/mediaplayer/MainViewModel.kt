package com.yuchen.mediaplayer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yuchen.mediaplayer.util.CurrentFragmentType

class MainViewModel : ViewModel() {

    // Record current fragment to support data binding
    val currentFragmentType = MutableLiveData<CurrentFragmentType>()
}