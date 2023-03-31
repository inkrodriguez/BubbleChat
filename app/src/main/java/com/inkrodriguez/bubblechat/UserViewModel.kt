package com.inkrodriguez.bubblechat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

    private val _myLiveData = MutableLiveData<String>()
    val myLiveData: LiveData<String> = _myLiveData

    // You should call this to update your liveData
    fun updateInfo(newInfo: String) {
        _myLiveData.value = newInfo
    }

}