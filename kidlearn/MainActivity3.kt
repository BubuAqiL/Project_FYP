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

class MainActivity3 : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        // Back Button Setup
        val backButton = findViewById<Button>(R.id.btn_kembali)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
            finish() // Avoid stacking MainActivity3
        }

        // Retrieve the name passed from MainActivity2
        val name = intent.getStringExtra("name")?.lowercase() ?: ""
        Toast.makeText(this, "Selected: $name", Toast.LENGTH_SHORT).show()

        // Set the image based on the name
        val imageView: ImageView = findViewById(R.id.imageView3)
        val imageId = resources.getIdentifier(name, "drawable", packageName)
        if (imageId != 0) {
            imageView.setImageResource(imageId)
        } else {
            Log.e("MainActivity3", "Drawable with name '$name' not found")
        }

        // Play sound based on the name
        val soundId = resources.getIdentifier(name, "raw", packageName)
        if (soundId != 0) {
            mediaPlayer = MediaPlayer.create(this, soundId)
            mediaPlayer?.apply {
                start()
                setOnCompletionListener { release() }
            }
        } else {
            Log.e("MainActivity3", "Sound file with name '$name' not found")
        }

        // Handle window insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Release MediaPlayer if still active
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
