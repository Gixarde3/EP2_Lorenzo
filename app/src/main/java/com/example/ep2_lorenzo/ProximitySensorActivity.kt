package com.example.ep2_lorenzo

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class ProximitySensorActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var proximitySensor: Sensor? = null
    private lateinit var callStatusTextView: TextView
    private lateinit var callBackground: ConstraintLayout
    private lateinit var callerImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proximity_sensor)

        callStatusTextView = findViewById(R.id.callStatusTextView)
        callBackground = findViewById(R.id.callBackground)
        callerImageView = findViewById(R.id.callerImageView)

        // Inicializar el SensorManager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // Verificar si el dispositivo tiene sensor de proximidad
        if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
            proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        } else {
            callStatusTextView.text = "El dispositivo no tiene sensor de proximidad"
        }

        val buttonEndCall = findViewById<ImageView>(R.id.endCallButton)
        buttonEndCall.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        proximitySensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_PROXIMITY) {
            val distance = event.values[0]
            val maxRange = proximitySensor?.maximumRange ?: 0f

            if (distance < maxRange) {
                // Objeto cerca (pantalla cerca de la cara)
                callBackground.setBackgroundColor(android.graphics.Color.BLACK)
                callerImageView.visibility = View.INVISIBLE
                callStatusTextView.text = "Llamada en curso"
            } else {
                // Objeto lejos (pantalla lejos de la cara)
                callBackground.setBackgroundColor(android.graphics.Color.WHITE)
                callerImageView.visibility = View.VISIBLE
                callStatusTextView.text = "Llamando..."
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No es necesario implementar esto para el sensor de proximidad
    }
}