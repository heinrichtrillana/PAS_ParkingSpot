package com.example.myparkingspot.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.myparkingspot.R
import com.example.myparkingspot.ui.Location
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class HistoryFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel
    private var adapter: LocationAdapter? = null


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val query: Query = FirebaseFirestore.getInstance()
            .collection("locations")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(50)

        val options = FirestoreRecyclerOptions.Builder<Location>()
            .setQuery(query,Location::class.java)
            .build()

        adapter = LocationAdapter(options)
        var recyclerView = view?.findViewById<RecyclerView>(R.id.location_list)
        recyclerView?.adapter = adapter
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        historyViewModel =
                ViewModelProviders.of(this).get(HistoryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_history, container, false)

        return root
    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        if (adapter != null) {
            adapter!!.stopListening()
        }
    }
}
