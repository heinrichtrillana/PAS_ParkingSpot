package com.example.myparkingspot.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }


    fun setName( name : String){
        Log.i("probando2", name);
        _name.value = name
        Log.i("probando3", _name.value);
    }
    val text: LiveData<String> = _text

}
