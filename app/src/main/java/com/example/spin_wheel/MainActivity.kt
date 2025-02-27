package com.example.spin_wheel

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val spinWheel = findViewById<ImageView>(R.id.spin_wheel)
        val spinButton = findViewById<Button>(R.id.spin_button)

// Define the items and partition count
        val items = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
        val partitionCount = items.size

// Create SpinWheelDrawable instance and set it as the drawable for the ImageView
        val spinWheelDrawable = SpinWheelDrawable(items, partitionCount, resources)
        spinWheel.setImageDrawable(spinWheelDrawable)

// Create arrow ImageView to display the arrow
        val arrowImageView = findViewById<ImageView>(R.id.arrow_image_view)
        arrowImageView.setImageResource(R.drawable.ic_arrow_down) // Ensure this drawable is set

// Set click listener for the spin button
        spinButton.setOnClickListener {
            // Generate a random rotation angle
            val random = Random(0)
            val initialRotation = spinWheel.rotation
            val randomSpins = random.nextInt(10) + 5  // To ensure multiple spins
            val randomOffset = random.nextInt(360 / partitionCount)  // Offset to stop at a different partition
            val finalRotation = initialRotation + 360f * randomSpins + randomOffset

            // Use ObjectAnimator to animate the rotation
            val animator = ObjectAnimator.ofFloat(spinWheel, "rotation", initialRotation, finalRotation)
            animator.duration = 3000 // Duration of the spin (in milliseconds)

            // Add a listener to calculate the selected item when the animation ends
            animator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}

                override fun onAnimationEnd(animation: Animator) {
                    // Calculate the selected item based on the final rotation
                    val anglePerPartition = 360f / partitionCount
                    val normalizedRotation = (finalRotation % 360 + 360) % 360 // Normalize angle between 0-360
                    val selectedItem = ((partitionCount - (normalizedRotation / anglePerPartition).toInt()) % partitionCount)

                    // Set the selected item in the drawable
                    spinWheelDrawable.setSelectedItem(selectedItem)

                    // Optionally display the selected item
                    Toast.makeText(this@MainActivity, "Selected Item: ${items[selectedItem]}", Toast.LENGTH_SHORT).show()
                }

                override fun onAnimationCancel(animation: Animator) {}

                override fun onAnimationRepeat(animation: Animator) {}
            })

            animator.start()
        }


//        val spinWheel = findViewById<ImageView>(R.id.spin_wheel)
//        val spinButton = findViewById<Button>(R.id.spin_button)
//
//        val items = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
//        val partitionCount = 5
//        val resources = resources // Get the Resources object from the Context
//        spinWheel.setImageDrawable(SpinWheelDrawable(items, partitionCount, resources))
//
//        spinButton.setOnClickListener {
//            val animator = ObjectAnimator.ofFloat(spinWheel, "rotation", 0f, 360f)
//            animator.duration = 2000
//            animator.start()
//        }

//        // Handle button click
//        val spinner = findViewById<CustomSpinnerView>(R.id.customSpinner)
//        val spinButton = findViewById<Button>(R.id.spinButton)
//
//        spinButton.setOnClickListener {
//            spinWheel(spinner)
//        }
    }

//    private fun spinWheel(spinner: CustomSpinnerView) {
//        // Generate a random number of rotations (between 0 and 3600 degrees)
//        val randomAngle = Random.nextInt(3600, 7200)
//
//        // ObjectAnimator to rotate the custom spinner view
//        val animator = ObjectAnimator.ofFloat(spinner, "rotation", 0f, randomAngle.toFloat())
//
//        // Set duration and interpolator for smooth deceleration
//        animator.duration = 3000 // 3 seconds
//        animator.interpolator = DecelerateInterpolator()
//
//        // Detect which partition is selected at the end of the spin
//        animator.addListener(object : Animator.AnimatorListener {
//            override fun onAnimationStart(p0: Animator) {
//            }
//
//            override fun onAnimationEnd(p0: Animator) {
//                val finalRotation = (spinner.rotation % 360).toInt()
//                val slices = spinner.slices.size
//                val anglePerSlice = 360 / slices
//
//                // Determine the prize based on the final rotation angle
//                val prizeIndex = finalRotation / anglePerSlice
//                val selectedPrize = spinner.slices[prizeIndex]
//                // Show the result (you can use Toast or Dialog)
//                Toast.makeText(this@MainActivity, "You won: $selectedPrize", Toast.LENGTH_SHORT)
//                    .show()
//            }
//
//            override fun onAnimationCancel(p0: Animator) {
//            }
//
//            override fun onAnimationRepeat(p0: Animator) {
//            }
//        })
//
//        // Start the animation
//        animator.start()
//    }
}