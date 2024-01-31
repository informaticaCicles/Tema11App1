package com.example.tema11app1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tema11app1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMapGeneral.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("operacion", 0)
            startActivity(intent)
        }

        binding.btnMapUbicacion.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("operacion", 1)
            startActivity(intent)
        }

        binding.btnMapMarkers.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("operacion", 2)
            startActivity(intent)
        }

        binding.btnMapPolilineas.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("operacion", 3)
            startActivity(intent)
        }
    }
}