package com.example.kidlearn

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main4) // Keep the same layout (activity_main2)

        // Find the button by its ID
        val backButton = findViewById<Button>(R.id.btn_back1)

        // Set click listener to navigate to MainActivity
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Create an array of numbers from 1 to 20
        val numbers = Array<String>(20) { (it + 1).toString() }

        // Find gridview by ID and set adapter
        val gridView = findViewById<GridView>(R.id.GVnum) // Ensure ID matches XML
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, numbers)
        gridView.adapter = adapter

        // Set up click listener for grid items
        gridView.setOnItemClickListener { adapterView, view, i, l ->
            // When an item is clicked, send the selected number to MainActivity5
            val intent = Intent(applicationContext, MainActivity5::class.java)
            intent.putExtra("number", i + 1)  // Pass the number (1-20)
            startActivity(intent)
        }

        // Handle system bars and edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
