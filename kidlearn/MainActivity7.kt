package com.example.kidlearn

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent

class MainActivity7 : AppCompatActivity() {

    private lateinit var lineDrawer: CustomView
    private var correctMediaPlayer: MediaPlayer? = null
    private var incorrectMediaPlayer: MediaPlayer? = null
    private var resetMediaPlayer: MediaPlayer? = null
    private var selectedNumber: TextView? = null
    private var selectedFruit: ImageView? = null
    private lateinit var numbers: List<TextView>
    private lateinit var fruits: List<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main7)

        lineDrawer = findViewById(R.id.line_drawer)

        val backButton = findViewById<Button>(R.id.btn_back)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Initialize reset button
        val resetButton = findViewById<Button>(R.id.btn_reset1)
        resetButton.setOnClickListener {
            playResetSound() // Play reset sound
            resetExercise()
        }

        numbers = listOf(
            findViewById<TextView>(R.id.number_1),
            findViewById<TextView>(R.id.number_2),
            findViewById<TextView>(R.id.number_3),
            findViewById<TextView>(R.id.number_4),
            findViewById<TextView>(R.id.number_5)
        )

        fruits = listOf(
            findViewById<ImageView>(R.id.fruit_grape),
            findViewById<ImageView>(R.id.fruit_orange),
            findViewById<ImageView>(R.id.fruit_cherry),
            findViewById<ImageView>(R.id.fruit_watermelon),
            findViewById<ImageView>(R.id.fruit_pineapple)
        )

        numbers.forEach { number ->
            number.setOnClickListener {
                handleNumberClick(number)
            }
        }

        fruits.forEach { fruit ->
            fruit.setOnClickListener {
                handleFruitClick(fruit)
            }
        }
    }

    private fun resetExercise() {
        // Clear all drawn lines
        lineDrawer.clearLines()

        // Re-enable all numbers and fruits
        numbers.forEach { number ->
            number.isEnabled = true
            number.setBackgroundResource(R.drawable.letter_bg)
        }

        fruits.forEach { fruit ->
            fruit.isEnabled = true
            fruit.setBackgroundResource(android.R.color.transparent)
        }

        // Reset selections
        selectedNumber = null
        selectedFruit = null

        // Show reset confirmation
        Toast.makeText(this, "Exercise Reset!", Toast.LENGTH_SHORT).show()
    }

    private fun handleNumberClick(number: TextView) {
        if (selectedNumber == number) {
            // Deselect if already selected
            selectedNumber?.setBackgroundResource(R.drawable.letter_bg)
            selectedNumber = null
        } else {
            // Select new number
            selectedNumber?.setBackgroundResource(R.drawable.letter_bg)
            selectedNumber = number
            number.setBackgroundResource(R.drawable.selected_bg)

            // Check match if fruit is already selected
            selectedFruit?.let { fruit ->
                checkMatch(number, fruit)
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

            // Check match if number is already selected
            selectedNumber?.let { number ->
                checkMatch(number, fruit)
            }
        }
    }

    private fun checkMatch(number: TextView, fruit: ImageView) {
        val numberValue = number.text.toString()
        val fruitTag = fruit.tag.toString()

        if (isCorrectMatch(numberValue, fruitTag)) {
            handleCorrectMatch(number, fruit)
        } else {
            handleIncorrectMatch()
        }
    }

    private fun isCorrectMatch(number: String, fruit: String): Boolean {
        return when (number) {
            "1" -> fruit == "watermelon"
            "2" -> fruit == "pineapple"
            "3" -> fruit == "cherry"
            "4" -> fruit == "grape"
            "5" -> fruit == "orange"
            else -> false
        }
    }

    private fun handleCorrectMatch(number: TextView, fruit: ImageView) {
        playCorrectSound()
        Toast.makeText(this, "Correct Match!", Toast.LENGTH_SHORT).show()

        val numberLocation = IntArray(2)
        val fruitLocation = IntArray(2)
        val lineDrawerLocation = IntArray(2)

        number.getLocationInWindow(numberLocation)
        fruit.getLocationInWindow(fruitLocation)
        lineDrawer.getLocationInWindow(lineDrawerLocation)

        val startX = numberLocation[0] - lineDrawerLocation[0] + number.width / 2f
        val startY = numberLocation[1] - lineDrawerLocation[1] + number.height / 2f
        val endX = fruitLocation[0] - lineDrawerLocation[0] + fruit.width / 2f
        val endY = fruitLocation[1] - lineDrawerLocation[1] + fruit.height / 2f

        lineDrawer.addLine(startX, startY, endX, endY)

        number.isEnabled = false
        fruit.isEnabled = false
        resetSelections()
    }

    private fun handleIncorrectMatch() {
        playIncorrectSound()
        Toast.makeText(this, "Try Again!", Toast.LENGTH_SHORT).show()
        resetSelections()
    }

    private fun resetSelections() {
        selectedNumber?.setBackgroundResource(R.drawable.letter_bg)
        selectedFruit?.setBackgroundResource(android.R.color.transparent)
        selectedNumber = null
        selectedFruit = null
    }

    private fun playResetSound() {
        resetMediaPlayer = MediaPlayer.create(this, R.raw.sound_reset)
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
