package com.example.kidlearn

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity5 : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)

        // Back Button Setup
        val backButton = findViewById<Button>(R.id.btn_kembali1)
        backButton.setOnClickListener {
            finish() // Go back to the previous activity
        }

        // Get the number passed from the intent
        val number = intent.getIntExtra("number", -1) // Default to -1 if no number is passed
        if (number == -1) {
            Log.e("MainActivity5", "No number received from intent")
            Toast.makeText(this, "Invalid number received", Toast.LENGTH_SHORT).show()
            return
        }

        // Display the number for debugging or user feedback
        Toast.makeText(this, "Number: $number", Toast.LENGTH_LONG).show()

        // Set the corresponding image based on the number
        val imageView: ImageView = findViewById(R.id.imageView4)
        val imageName = "num_$number" // Assuming drawable resources are named as "num_1", "num_2", etc.
        val imageId = resources.getIdentifier(imageName, "drawable", packageName)
        if (imageId != 0) {
            imageView.setImageResource(imageId)
        } else {
            Log.e("MainActivity5", "Drawable resource with name '$imageName' not found")
        }

        // Set the corresponding sound based on the number
        val soundName = "num_$number" // Assuming raw resources are named as "num_1", "num_2", etc.
        val soundId = resources.getIdentifier(soundName, "raw", packageName)
        if (soundId != 0) {
            mediaPlayer = MediaPlayer.create(this, soundId)
            mediaPlayer?.apply {
                start()
                setOnCompletionListener { release() }
            }
        } else {
            Log.e("MainActivity5", "Sound file with name '$soundName' not found")
        }

        // Handle system bars and edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Release the MediaPlayer if it is still active
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
