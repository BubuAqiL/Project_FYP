package com.example.kidlearn

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var btn3: Button
    private lateinit var btn4: Button

    private var mediaPlayer: MediaPlayer? = null // MediaPlayer for button click sounds
    private var welcomeMediaPlayer: MediaPlayer? = null // MediaPlayer for welcome sound

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Play the welcome sound when MainActivity is created
        playWelcomeSound()

        // Initialize buttons
        btn1 = findViewById(R.id.btn_ABC)
        btn2 = findViewById(R.id.btn_123)
        btn3 = findViewById(R.id.btn_test)
        btn4 = findViewById(R.id.btn_guess)

        // Button listeners for navigating to different activities with unique sound for each button
        btn1.setOnClickListener {
            playNavigationSound(R.raw.sound_abc) // Play ABC sound before navigating
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        btn2.setOnClickListener {
            playNavigationSound(R.raw.sound_num) // Play 123 sound before navigating
            val intent = Intent(this, MainActivity4::class.java)
            startActivity(intent)
        }

        btn3.setOnClickListener {
            playNavigationSound(R.raw.letters_sound) // Play test sound before navigating
            val intent = Intent(this, MainActivity6::class.java)
            startActivity(intent)
        }

        btn4.setOnClickListener {
            playNavigationSound(R.raw.sound_item) // Play guess sound before navigating
            val intent = Intent(this, MainActivity7::class.java)
            startActivity(intent)
        }

        // Set up edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Function to play the unique sound for each button
    private fun playNavigationSound(soundResource: Int) {
        mediaPlayer = MediaPlayer.create(this, soundResource) // Use passed resource ID
        mediaPlayer?.start() // Start playing the sound
    }

    // Function to play the welcome sound when MainActivity is created
    private fun playWelcomeSound() {
        welcomeMediaPlayer = MediaPlayer.create(this, R.raw.homepage_sound) // Use your welcome sound file
        welcomeMediaPlayer?.start() // Start playing the welcome sound
    }

    // Stop the welcome sound when the activity is paused or stops being visible
    override fun onPause() {
        super.onPause()
        welcomeMediaPlayer?.pause()
    }

    // Release the MediaPlayer when the activity is destroyed
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release() // Release the resource when the activity is destroyed
        welcomeMediaPlayer?.release() // Release welcome MediaPlayer when activity is destroyed
    }
}
