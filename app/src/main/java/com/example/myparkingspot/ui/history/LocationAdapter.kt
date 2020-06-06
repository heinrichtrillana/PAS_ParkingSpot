package com.example.myparkingspot.ui.history

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myparkingspot.R
import com.example.myparkingspot.ui.Location
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import java.util.*

class LocationAdapter( options : FirestoreRecyclerOptions<Location>):
    FirestoreRecyclerAdapter<Location, LocationAdapter.LocationViewHolder>(options) {

    class LocationViewHolder constructor( val view: View) : RecyclerView.ViewHolder(view){

        fun bind(item: Location) {
            val address = view.findViewById<TextView>(R.id.address_display)
            address.text = item.address

            val coordinates = view.findViewById<TextView>(R.id.coordinates)
            coordinates.text = "(" + item.latitude.toString() + ", " + item.longitude.toString() + ")"

            val date = view.findViewById<TextView>(R.id.date)
            date.text = item.timestamp?.toDate().toString()
        }
    }


    override fun onBindViewHolder(view: LocationViewHolder, p1: Int, location: Location) {
        view.bind(location)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_location_item, parent, false)

        return LocationViewHolder(view)
    }


}
