package com.starvision.api

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.starvision.view.center.models.ProfileModels
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {
    val myPushResponse : MutableLiveData<ProfileModels> = MutableLiveData()

    fun pushData(url : String ,params : HashMap<String?,String?>) {
        viewModelScope.launch {
            val response = repository.pushData(url,params)
            myPushResponse.value = response
        }
    }

}