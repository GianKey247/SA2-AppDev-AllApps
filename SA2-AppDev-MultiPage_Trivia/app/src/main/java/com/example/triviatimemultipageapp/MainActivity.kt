package com.example.triviatimemultipageapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var latestScore_Button : Button
    private lateinit var begin_Button : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        latestScore_Button = findViewById(R.id.LatestScore_Button)
        latestScore_Button.setOnClickListener(){
            val intent = Intent(this@MainActivity, LatestScoreScreen::class.java)
            startActivity(intent)
        }
        begin_Button = findViewById(R.id.BeginTrivia_Button)
        begin_Button.setOnClickListener(){
            val intent = Intent(this@MainActivity, TriviaSettingsScreen::class.java)
            startActivity(intent)
        }
    }
}