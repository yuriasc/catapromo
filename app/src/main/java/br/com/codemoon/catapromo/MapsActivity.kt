package br.com.codemoon.catapromo

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener {
    override fun onLocationChanged(location: Location?) {

    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }

    override fun onProviderEnabled(provider: String?) {

    }

    override fun onProviderDisabled(provider: String?) {

    }

    private lateinit var mMap: GoogleMap
    private lateinit var locationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap

        val queue = Volley.newRequestQueue(this)
        val url = "https://www.codemoon.com.br/catapromo/produto"

        val stringRequest = object : JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener<JSONObject> { response ->

                    val produtos = response.getString("produtos")
                    val json = JSONArray(produtos)

                    for (i in 0 until json.length()) {
                        val jresponse = json.getString(i)
                        val res = JSONObject(jresponse)

                        val coordenadas = LatLng(res.getString("latitude").toDouble(), res.getString("longitude").toDouble())
                        val valor = res.getString("valor")
                        mMap.addMarker(MarkerOptions().position(coordenadas).title(res.getString("nome")).snippet("Preço: R$ $valor"))
                        //Log.d("JSON", res.getString("nome"))
                    }

                },
                Response.ErrorListener {
                    Toast.makeText(this, "Erro ao gerar Lista", Toast.LENGTH_LONG).show()
                })
        {

        }
        queue.add(stringRequest)



        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this)
        val provider = locationManager.getBestProvider(Criteria(), true)

        val location = locationManager.getLastKnownLocation(provider)
        val latitude = location.latitude
        val longitude = location.longitude

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            val toast = Toast.makeText(applicationContext, "no permission", Toast.LENGTH_LONG)
            toast.show()
            return
        } else {
            mMap.isMyLocationEnabled = true
        }

        val local = LatLng(latitude, longitude)

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setMinZoomPreference(12.0f)

        //val sydney = LatLng(-34.0, 151.0)
       // mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(local))

        val cameraPosition = CameraPosition.Builder()
                .target(LatLng(latitude,longitude))      // Sets the center of the map to Mountain View
                .zoom(15f)                   // Sets the zoom
                //.bearing(90f)                // Sets the orientation of the camera to east
                //.tilt(30f)                   // Sets the tilt of the camera to 30 degrees
                .build()                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))


    }
}

private fun LocationManager.requestLocationUpdates(gpS_PROVIDER: String, i: Int, i1: Int, mapsActivity: MapsActivity) {
    Log.d("APP", "Request")
}
