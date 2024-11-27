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


class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        // Find the button by its ID
        val backButton = findViewById<Button>(R.id.btn_back3)

        // Set click listener to navigate to MainActivity
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        var alpha = Array<String>(size = 26){""}
        var j=65
        for (i in 0..25){
            alpha[i] = Character.toString(j.toChar())
            j++
        }

        // Find gridview by ID and set adapter
        val gridView = findViewById<GridView>(R.id.GVnum) // Ensure ID matches XML
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, alpha)
        gridView.adapter = adapter

        gridView.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(applicationContext, MainActivity3::class.java)
            intent.putExtra("name", alpha[i])
            startActivity(intent)
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}