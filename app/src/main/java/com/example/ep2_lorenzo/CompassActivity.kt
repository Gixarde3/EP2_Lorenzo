package com.example.ep2_lorenzo

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.roundToInt

class CompassActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var compassImage: ImageView
    private lateinit var degreeText: TextView
    private var currentDegree = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compass)

        compassImage = findViewById(R.id.compass_image)
        degreeText = findViewById(R.id.degree_text)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_GAME
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val degree = event?.values?.get(0)?.roundToInt() ?: 0
        degreeText.text = "$degree°"

        val rotateAnimation = RotateAnimation(
            currentDegree,
            -degree.toFloat(),
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotateAnimation.duration = 210
        rotateAnimation.fillAfter = true

        compassImage.startAnimation(rotateAnimation)
        currentDegree = -degree.toFloat()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No es necesario implementar esto para este ejemplo
    }
}