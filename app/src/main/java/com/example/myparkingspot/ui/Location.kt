package com.example.myparkingspot.ui

import com.google.firebase.Timestamp

class Location {
    var address: String? = null
    var latitude : Double? = null
    var longitude : Double? = null
    var timestamp : Timestamp? = null

    constructor(){}

    constructor( address : String, latitude: Double, longitude : Double, timestamp : Timestamp){
        this.address = address
        this.latitude = latitude
        this.longitude = longitude
        this.timestamp = timestamp
    }
}