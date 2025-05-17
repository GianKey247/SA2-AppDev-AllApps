package com.example.minecraftpotionapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class InstructionsPopup : AppCompatActivity() {
    private lateinit var ReturnHome_ImageButton : ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_instructions_popup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        ReturnHome_ImageButton = findViewById(R.id.Return_button)
        ReturnHome_ImageButton.setOnClickListener{
            var intent = Intent(this@InstructionsPopup, MainActivity::class.java)
            startActivity(intent)
        }
    }
}