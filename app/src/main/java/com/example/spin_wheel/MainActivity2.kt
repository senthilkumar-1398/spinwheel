package com.example.spin_wheel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bluehomestudio.luckywheel.LuckyWheel
import com.bluehomestudio.luckywheel.WheelItem
import kotlin.random.Random

class MainActivity2 : AppCompatActivity() {
    private val wheelItems: MutableList<WheelItem> = ArrayList()
    lateinit var wheel: LuckyWheel
    lateinit var tvSpin: TextView
    var randomNumber = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        tvSpin = findViewById(R.id.tv_spin)
        wheel = findViewById(R.id.lwv)
        wheelItems.add(
            WheelItem(
                generateRandomYellowishLightColor(),
                getBitmapOrFallback(R.drawable.bg_rransparent),
                "20"
            )
        )

        wheelItems.add(
            WheelItem(
                generateRandomYellowishLightColor(),
                getBitmapOrFallback(R.drawable.bg_rransparent),
                "50"
            )
        )

        wheelItems.add(
            WheelItem(
                generateRandomYellowishLightColor(),
                getBitmapOrFallback(R.drawable.bg_rransparent),
                "100"
            )
        )

        wheelItems.add(
            WheelItem(
                generateRandomYellowishLightColor(),
                getBitmapOrFallback(R.drawable.bg_rransparent), "76"
            )
        )

        wheelItems.add(
            WheelItem(
                generateRandomYellowishLightColor(),
                getBitmapOrFallback(R.drawable.bg_rransparent),
                "2000"
            )
        )

        wheelItems.add(
            WheelItem(
                generateRandomYellowishLightColor(),
                getBitmapOrFallback(R.drawable.bg_rransparent),
                "67"
            )
        )

        wheelItems.add(
            WheelItem(
                generateRandomYellowishLightColor(),
                getBitmapOrFallback(R.drawable.bg_rransparent), "350"
            )
        )

        wheelItems.add(
            WheelItem(
                generateRandomYellowishLightColor(),
                getBitmapOrFallback(R.drawable.bg_rransparent),
                "175"
            )
        )

        wheel.addWheelItems(wheelItems)
        wheel.setLuckyWheelReachTheTarget {
            println("Lucky wheel reach the target")
            tvSpin.isEnabled = true
            Toast.makeText(this, "Win".plus(wheelItems[randomNumber - 1].text), Toast.LENGTH_SHORT)
                .show()
        }

        tvSpin.setOnClickListener {
            spinWheel()
        }

    }

    private fun getBitmapOrFallback(resourceId: Int): Bitmap {
        var bitmap = BitmapFactory.decodeResource(getResources(), resourceId)
        if (bitmap == null) {
            // Fallback to a transparent bitmap
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        }
        return bitmap
    }

    fun spinWheel() {
        val tempRandomNumber = randomNumber
        randomNumber = (1..wheelItems.size).random()

        if (tempRandomNumber != randomNumber) {
            wheel.rotateWheelTo(randomNumber)
            tvSpin.isEnabled = false
        } else {
            randomNumber = (1..wheelItems.size).random()
            spinWheel()
        }
        Log.e("TAG", "onCreate: $randomNumber")
    }

    private fun generateRandomYellowishLightColor(): Int {
        val hue = Random.nextFloat() * 360
        // Random saturation (0 to 1 for dull to vivid colors)
        val saturation = Random.nextFloat()
        // Random brightness (0 to 1 for dark to bright colors)
        val brightness = Random.nextFloat()
        // Convert HSV to color
        val hsv = floatArrayOf(hue, saturation, brightness)
        return Color.HSVToColor(hsv)
    }

}