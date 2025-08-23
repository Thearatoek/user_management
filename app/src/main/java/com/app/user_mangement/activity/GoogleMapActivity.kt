
package com.app.user_mangement.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.klakmoum.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class Map5Activity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var gmap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.btn) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        gmap = map
        // 5 sample places in Cambodia
        val places = listOf(
            "Phnom Penh"      to LatLng(11.5564, 104.9282),
            "Siem Reap"       to LatLng(13.3633, 103.8564),
            "Battambang"      to LatLng(13.0957, 103.2022),
            "Sihanoukville"   to LatLng(10.6279, 103.5221),
            "Kampot"          to LatLng(10.6106, 104.1810)
        )
        val bounds = LatLngBounds.Builder()
        places.forEach { (name, latLng) ->
            gmap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(name)
                    .snippet("Tap for more")
            )
            bounds.include(latLng)
        }
        // Animate camera to include all 5 markers with padding
        val padding = 120 // px
        gmap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), padding))
        // Optional: marker click listener
        gmap.setOnMarkerClickListener { marker ->
            marker.showInfoWindow()
            true // return true to consume the event
        }
    }
}
