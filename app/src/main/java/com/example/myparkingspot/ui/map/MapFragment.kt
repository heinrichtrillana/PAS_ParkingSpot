package com.example.myparkingspot.ui.map

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.myparkingspot.R
import com.example.myparkingspot.databinding.FragmentMapBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_map.*


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mapViewModel: MapViewModel
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding : FragmentMapBinding

    private var showLastLocation : Boolean = false;
    private lateinit var lastLocation : Marker
    private lateinit var sharedPref : SharedPreferences

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)
        requestPermissions() //Pedir permisos al iniciar la actividad;

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        sharedPref = activity?.getSharedPreferences(
            getString(R.string.location_preference_file), Context.MODE_PRIVATE) ?: return
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mapViewModel =
                ViewModelProviders.of(this).get(MapViewModel::class.java)
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_map, container, false)

        binding.addLocation.setOnClickListener{onAddLocationClicked()}
        binding.lastLocation.setOnClickListener{onSeeLocationClicked()}


        return binding.root
    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let{
             googleMap = it
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray){
        when (requestCode) {
            0 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Si nos da permiso, habilitamos la capa y centramos en su ubicacion
                    googleMap.isMyLocationEnabled = true
                    Log.i("Location", "Init")

                    //Mover la camara a mi ubicacion
                    fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
                        location?.let {
                            var posi = LatLng(it.latitude, it.longitude)
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posi,18F));
                        }
                    }
                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }

    private fun onSeeLocationClicked(){
        //lastLocation = googleMap.addMarker(MarkerOptions().position(LatLng(lat,long)))
        //lastLocation.isVisible = false

        if(showLastLocation){ //Si estaba seleccionada la deselecciono
            binding.lastLocation.setImageResource(R.drawable.ic_location_black_24dp)
            //Esconder pin
            lastLocation.isVisible = false;
            showLastLocation = false;
        } else {
            //Sacar los valores del shared preferences
            if( sharedPref.contains(getString(R.string.latitude_key))
                && sharedPref.contains(getString(R.string.longitude_key))){ //Si existe la posicion anterior, la muestro

                binding.lastLocation.setImageResource(R.drawable.ic_location_off_black_24dp)
                var lat = sharedPref.getString(getString(R.string.latitude_key), "");
                var long = sharedPref.getString(getString(R.string.longitude_key), "");

                var latLng = LatLng(lat!!.toDouble(),long!!.toDouble())

                //añadir pin
                lastLocation = googleMap.addMarker(MarkerOptions().position(latLng))
                lastLocation.isVisible = true;
                showLastLocation = true;
                //Mover hacia el pin
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,18F));
            } else { //Si no , alerto
                Snackbar.make(mapView, "No hay posicion anterior", Snackbar.LENGTH_SHORT).show();
            }

        }

    }

    private fun onAddLocationClicked(){
        if(!checkPermissions()){ //Si no tiene permisos de Localizacion los pide.
            requestPermissions()
            return;
        }
        if(!isLocationEnabled()){ //Si no tiene la ubicacion activada, pide que se active
            Snackbar.make(mapView, "TURN LOCATION ON", Snackbar.LENGTH_SHORT).show();
            return;
        }
        //Cuando todo esta bien, se obitene la localizacion y se guarda.
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Got last known location. In some rare situations this can be null.
                Log.i("Location", location.toString())

                with(sharedPref.edit()) {
                    putString(getString(R.string.latitude_key),location!!.latitude.toString())
                    putString(getString(R.string.longitude_key),location!!.longitude.toString())
                    commit()
                }

                lastLocation.isVisible = false //Si habia un marcador, al añadir uno nuevo escondemos el anterior.
                Snackbar.make(mapView, "Posicion añadida correctamente", Snackbar.LENGTH_SHORT).show();
            }
    }


    private fun requestPermissions() {
        val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
        requestPermissions(permissions,0)
    }

    private fun checkPermissions(): Boolean {
        context?.let{
            if (ActivityCompat.checkSelfPermission(it, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(it,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            )   return true
        }
        return false
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
}
