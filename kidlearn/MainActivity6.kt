package com.example.kidlearn

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent

class MainActivity6 : AppCompatActivity() {

    private lateinit var lineDrawer: CustomView
    private var correctMediaPlayer: MediaPlayer? = null
    private var incorrectMediaPlayer: MediaPlayer? = null
    private var resetMediaPlayer: MediaPlayer? = null
    private var selectedLetter: TextView? = null
    private var selectedFruit: ImageView? = null
    private lateinit var letters: List<TextView>
    private lateinit var fruits: List<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main6)

        lineDrawer = findViewById(R.id.line_drawer)

        val backButton = findViewById<Button>(R.id.btn_back)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Initialize reset button
        val resetButton = findViewById<Button>(R.id.btn_reset2)
        resetButton.setOnClickListener {
            playResetSound() // Play reset sound
            resetExercise()
        }

        letters = listOf(
            findViewById<TextView>(R.id.letter_W),
            findViewById<TextView>(R.id.letter_P),
            findViewById<TextView>(R.id.letter_G),
            findViewById<TextView>(R.id.letter_O),
            findViewById<TextView>(R.id.letter_C)
        )

        fruits = listOf(
            findViewById<ImageView>(R.id.fruit_grape),
            findViewById<ImageView>(R.id.fruit_orange),
            findViewById<ImageView>(R.id.fruit_cherry),
            findViewById<ImageView>(R.id.fruit_watermelon),
            findViewById<ImageView>(R.id.fruit_pineapple)
        )

        letters.forEach { letter ->
            letter.setOnClickListener {
                handleLetterClick(letter)
            }
        }

        fruits.forEach { fruit ->
            fruit.setOnClickListener {
                handleFruitClick(fruit)
            }
        }
    }

    private fun handleLetterClick(letter: TextView) {
        if (selectedLetter == letter) {
            // Deselect if already selected
            selectedLetter?.setBackgroundResource(R.drawable.letter_bg)
            selectedLetter = null
        } else {
            // Select new letter
            selectedLetter?.setBackgroundResource(R.drawable.letter_bg)
            selectedLetter = letter
            letter.setBackgroundResource(R.drawable.selected_bg)

            // Check match if fruit is already selected
            selectedFruit?.let { fruit ->
                checkMatch(letter, fruit)
            }
        }
    }

    private fun handleFruitClick(fruit: ImageView) {
        if (selectedFruit == fruit) {
            // Deselect if already selected
            selectedFruit?.setBackgroundResource(android.R.color.transparent)
            selectedFruit = null
        } else {
            // Select new fruit
            selectedFruit?.setBackgroundResource(android.R.color.transparent)
            selectedFruit = fruit
            fruit.setBackgroundResource(R.drawable.selected_bg)

            // Check match if letter is already selected
            selectedLetter?.let { letter ->
                checkMatch(letter, fruit)
            }
        }
    }

    private fun checkMatch(letter: TextView, fruit: ImageView) {
        val letterValue = letter.tag.toString()
        val fruitTag = fruit.tag.toString()

        if (isMatchingPair(letterValue, fruitTag)) {
            handleCorrectMatch(letter, fruit)
        } else {
            handleIncorrectMatch()
        }
    }

    private fun isMatchingPair(letter: String, fruit: String): Boolean {
        return (letter == "W" && fruit == "watermelon") ||
                (letter == "P" && fruit == "pineapple") ||
                (letter == "G" && fruit == "grape") ||
                (letter == "O" && fruit == "orange") ||
                (letter == "C" && fruit == "cherry")
    }

    private fun handleCorrectMatch(letter: TextView, fruit: ImageView) {
        playCorrectSound()
        Toast.makeText(this, "Correct Match!", Toast.LENGTH_SHORT).show()

        val letterLocation = IntArray(2)
        val fruitLocation = IntArray(2)
        val lineDrawerLocation = IntArray(2)

        letter.getLocationInWindow(letterLocation)
        fruit.getLocationInWindow(fruitLocation)
        lineDrawer.getLocationInWindow(lineDrawerLocation)

        val startX = letterLocation[0] - lineDrawerLocation[0] + letter.width / 2f
        val startY = letterLocation[1] - lineDrawerLocation[1] + letter.height / 2f
        val endX = fruitLocation[0] - lineDrawerLocation[0] + fruit.width / 2f
        val endY = fruitLocation[1] - lineDrawerLocation[1] + fruit.height / 2f

        lineDrawer.addLine(startX, startY, endX, endY)

        letter.isEnabled = false
        fruit.isEnabled = false
        resetSelections()
    }

    private fun handleIncorrectMatch() {
        playIncorrectSound()
        Toast.makeText(this, "Try Again!", Toast.LENGTH_SHORT).show()
        resetSelections()
    }

    private fun resetSelections() {
        selectedLetter?.setBackgroundResource(R.drawable.letter_bg)
        selectedFruit?.setBackgroundResource(android.R.color.transparent)
        selectedLetter = null
        selectedFruit = null
    }

    private fun resetExercise() {
        // Clear all drawn lines
        lineDrawer.clearLines()

        // Re-enable all letters and fruits
        letters.forEach { letter ->
            letter.isEnabled = true
            letter.setBackgroundResource(R.drawable.letter_bg)
        }

        fruits.forEach { fruit ->
            fruit.isEnabled = true
            fruit.setBackgroundResource(android.R.color.transparent)
        }

        // Reset selections
        selectedLetter = null
        selectedFruit = null

        // Show reset confirmation
        Toast.makeText(this, "Exercise Reset!", Toast.LENGTH_SHORT).show()
    }

    private fun playResetSound() {
        resetMediaPlayer = MediaPlayer.create(this, R.raw.sound_reset) // Use your reset sound file
        resetMediaPlayer?.start()
    }

    private fun playCorrectSound() {
        correctMediaPlayer = MediaPlayer.create(this, R.raw.correct)
        correctMediaPlayer?.start()
    }

    private fun playIncorrectSound() {
        incorrectMediaPlayer = MediaPlayer.create(this, R.raw.incorrect)
        incorrectMediaPlayer?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        correctMediaPlayer?.release()
        incorrectMediaPlayer?.release()
        resetMediaPlayer?.release() // Release the reset MediaPlayer
    }
}
