package com.example.tema11app1

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.tema11app1.databinding.ActivityMapsBinding

import android.Manifest
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var operacion: Int? = 0

    private val iesSimarro = LatLng(38.986, -0.532944)
    private val fpCheste = LatLng(39.476488, -0.648207)

    private val hamburgueseria = LatLng(39.478992, -0.426408)

    private val POLILINEA = PolylineOptions()
        .add(iesSimarro)
        .add(LatLng(39.482039, -0.422245))
        .add(LatLng(39.481244, -0.422760))
        .add(LatLng(39.478858, -0.424340))
        .add(LatLng(39.478352, -0.424717))
        .add(LatLng(39.478418, -0.425030))
        .add(LatLng(39.478762, -0.425824))
        .add(LatLng(39.479084, -0.426598))
        .add(LatLng(39.479337, -0.427238))
        .add(LatLng(39.479367, -0.427436))
        .add(LatLng(39.479287, -0.427600))
        .add(LatLng(39.479145, -0.427570))
        .add(LatLng(39.479061, -0.427397))
        .add(LatLng(39.479095, -0.427119))
        .add(LatLng(39.479080, -0.426925))
        .add(LatLng(39.479000, -0.426538))
        .add(LatLng(39.478808, -0.426067))
        .add(LatLng(39.478609, -0.425992))
        .add(LatLng(39.478544, -0.425843))
        .add(LatLng(39.478406, -0.425858))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        operacion = intent.getIntExtra("operacion", 0)
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

        when (operacion) {
            0 -> {
                posicionSimarro()
            }
            1 -> {
                ubicacionActual2()
            }
            2 -> {
                setMarker(iesSimarro, "IES Simarro", "Se encuentra en Xativa")
                setMarker(fpCheste, "CIPFP Cheste", "Se encuentra en Cheste")
            }
            3 -> {
                setMarker(iesSimarro, "IES Simarro", "Se encuentra en Xativa")
                setMarker(hamburgueseria, "McDonald's", "Se encuentra en Mislata")
                setPolilyne(POLILINEA)
            }
        }
    }

    private fun posicionSimarro() {
        // Añadimos la posición del IES Simarro y movemos la cámara

        val iesSimarro = LatLng(38.986, -0.532944)
        mMap.addMarker(MarkerOptions().position(iesSimarro).title("IES Lluís simarro"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(iesSimarro))
    }


    private fun ubicacionActual() {
        // Seteamos el tipo de mapa
        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "MyLocation Enabled", Toast.LENGTH_SHORT).show()
            return
        }

        // Activamos la capa o layer MyLocation
        mMap.isMyLocationEnabled = true
    }

    private fun ubicacionActual2() {
        val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener(this) { location ->
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    // Logic to handle location object
                    val posActual = LatLng(location.latitude, location.longitude)
                    mMap.addMarker(MarkerOptions().position(posActual).title("Estás aquí"))
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(posActual))
                }
            }
    }

    // Método que nos permite crear marcadores
    private fun setMarker(position: LatLng, titulo: String, info: String) {

        // Agregamos marcadores para indicar sitios de interés
        val myMarker = mMap.addMarker(MarkerOptions()
            .position(position)
            .title(titulo)  // Agrega un título al marcador
            .snippet(info)  // Agrega información detalle relacionada
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))) // Color del marcador
    }

    // Método que añade la polilínea al mapa
    private fun setPolilyne(options: PolylineOptions) {
        val polyline: Polyline = mMap.addPolyline(options)
    }

}