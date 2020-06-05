package com.example.myparkingspot.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    fun setName( name : String){
        _name.value = name
    }

    private val _surname = MutableLiveData<String>()
    val surname: LiveData<String>
        get() = _surname

    fun setSurname( surname : String){
        _surname.value = surname
    }

    private val _id = MutableLiveData<String>()
    val id: LiveData<String>
        get() = _id

    fun setId( id : String){
        _id.value = id
    }

    private val _phone = MutableLiveData<String>()
    val phone: LiveData<String>
        get() = _phone

    fun setPhone( phone : String){
        _phone.value = phone
    }

    private val _manufacturer = MutableLiveData<String>()
    val manufacturer: LiveData<String>
        get() = _manufacturer

    fun setManufacturer( manufacturer : String){
        _manufacturer.value = manufacturer
    }

    private val _model = MutableLiveData<String>()
    val model: LiveData<String>
        get() = _model

    fun setModel( model : String){
        _model.value = model
    }

    private val _plate = MutableLiveData<String>()
    val plate: LiveData<String>
        get() = _plate

    fun setPlate( plate : String){
        _plate.value = plate
    }
}
