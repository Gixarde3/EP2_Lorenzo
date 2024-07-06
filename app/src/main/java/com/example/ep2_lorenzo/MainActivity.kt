package com.example.ep2_lorenzo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnCamara = findViewById<Button>(R.id.btnCamara)
        btnCamara.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }
        val btnPedometro = findViewById<Button>(R.id.btnPodometro)
        btnPedometro.setOnClickListener {
            val intent = Intent(this, CompassActivity::class.java)
            startActivity(intent)
        }
        val btnSensorProximidad = findViewById<Button>(R.id.btnSensorProximidad)
        btnSensorProximidad.setOnClickListener {
            val intent = Intent(this, ProximitySensorActivity::class.java)
            startActivity(intent)
        }
    }
}