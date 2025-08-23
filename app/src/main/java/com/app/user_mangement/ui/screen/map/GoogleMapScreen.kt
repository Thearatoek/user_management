package com.example.app.ui.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.widget.Autocomplete
import com.google.maps.android.compose.*
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteActivity
@Composable
fun MapScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState()
    val places = listOf(
        MyPlace("ChIJyY0m0xP3GDcR5VEmgei0MIY", LatLng(11.5564, 104.9282)),
        MyPlace("ChIJVTPokywQ3TURF1pR7wC6-8I", LatLng(13.3633, 103.8564)),
        MyPlace("ChIJT3p1uP63VDcR2v7tqqPMa2U", LatLng(13.0957, 103.2022)),
        MyPlace("ChIJQ7y79w4YCDcR3kmwBQk1sRM", LatLng(10.6279, 103.5221)),
        MyPlace("ChIJb9N4e20XCDcRz9jVbH2mmhE", LatLng(10.6106, 104.1810))
    )
    val placesClient = Places.createClient(context)
    val autocompleteLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { data ->
                val place = Autocomplete.getPlaceFromIntent(data)
                Toast.makeText(context, "Selected: ${place.name}", Toast.LENGTH_SHORT).show()
            }
        } else if (result.resultCode == AutocompleteActivity.RESULT_ERROR) {
            result.data?.let { data ->
                val status = Autocomplete.getStatusFromIntent(data)
                Toast.makeText(context, "Error: ${status.statusMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = { latLng ->
                fetchNearestPlace(placesClient, latLng, context)
            }
        ) {
            places.forEach { place ->
                Marker(
                    state = MarkerState(position = place.latLng),
                    title = place.id, // show ID for now
                    snippet = "Tap for more",
                    onClick = {
                        showPlacePreview(context, place.id)
                        true
                    }
                )
            }
        }
    }
    // Auto zoom to show all markers
    LaunchedEffect(Unit) {
        val boundsBuilder = LatLngBounds.builder()
        places.forEach { boundsBuilder.include(it.latLng) }
        val bounds = boundsBuilder.build()
        cameraPositionState.animate(CameraUpdateFactory.newLatLngBounds(bounds, 120))
    }
}
fun showPlacePreview(context: Context, placeId: String) {
    val fields = listOf(
        Place.Field.ID,
        Place.Field.NAME,
        Place.Field.ADDRESS,
        Place.Field.PHONE_NUMBER,
        Place.Field.WEBSITE_URI,
        Place.Field.RATING,
        Place.Field.LAT_LNG
    )
    val request = FetchPlaceRequest.newInstance(placeId, fields)
    val placesClient = Places.createClient(context)

    placesClient.fetchPlace(request)
        .addOnSuccessListener { response ->
            Log.d("response", "$response")
            val place = response.place
            // Open Google Maps with place details using intent
            val gmmIntentUri = Uri.parse("geo:${place.latLng?.latitude},${place.latLng?.longitude}?q=${Uri.encode(place.name)}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            context.startActivity(mapIntent)
        }
        .addOnFailureListener { exception ->
            Toast.makeText(context, "Failed to load place: ${exception.message}", Toast.LENGTH_SHORT).show()
        }
}
fun fetchPlaceById(placesClient: PlacesClient, placeId: String, context: Context) {
    val fields = listOf(
        Place.Field.ID,
        Place.Field.NAME,
        Place.Field.ADDRESS,
        Place.Field.LAT_LNG,
        Place.Field.PHONE_NUMBER,
        Place.Field.RATING,
        Place.Field.WEBSITE_URI
    )

    val request = FetchPlaceRequest.newInstance(placeId, fields)
    placesClient.fetchPlace(request)
        .addOnSuccessListener { response ->
            val place = response.place
            val gmmIntentUri = Uri.parse("geo:${place.latLng?.latitude},${place.latLng?.longitude}?q=${Uri.encode(place.name)}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            context.startActivity(mapIntent)
        }
        .addOnFailureListener { e ->
            Toast.makeText(context, "Failed to fetch place: ${e.message}", Toast.LENGTH_SHORT).show()
        }
}

fun fetchNearestPlace(placesClient: PlacesClient, latLng: LatLng, context: Context) {
    // Using FindCurrentPlaceRequest as an approximation
    val placeFields = listOf(
        Place.Field.ID,
        Place.Field.NAME,
        Place.Field.ADDRESS,
        Place.Field.LAT_LNG
    )

    val request = FindCurrentPlaceRequest.newInstance(placeFields)

    if (ActivityCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        val placeResponse = placesClient.findCurrentPlace(request)
        placeResponse.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val places = task.result?.placeLikelihoods
                // Find nearest place to clicked LatLng
                val nearest = places?.minByOrNull { it.place.latLng?.distanceTo(latLng) ?: Double.MAX_VALUE }
                nearest?.let {
                    fetchPlaceById(placesClient, it.place.id!!, context)
                }
            } else {
                Toast.makeText(context, "Failed to get nearby places", Toast.LENGTH_SHORT).show()
            }
        }
    } else {
        Toast.makeText(context, "Location permission not granted", Toast.LENGTH_SHORT).show()
    }
}

fun LatLng.distanceTo(other: LatLng): Double {
    val results = FloatArray(1)
    android.location.Location.distanceBetween(latitude, longitude, other.latitude, other.longitude, results)
    return results[0].toDouble()
}

data class MyPlace(val id: String, val latLng: LatLng)
