package com.example.myparkingspot.ui

class Location {
    var address: String? = null
    var latitude : Double? = null
    var longitude : Double? = null
    var timestamp : Long? = null

    constructor(){}

    constructor( address : String, latitude: Double, longitude : Double, timestamp : Long){
        this.address = address
        this.latitude = latitude
        this.longitude = longitude
        this.timestamp = timestamp
    }
}