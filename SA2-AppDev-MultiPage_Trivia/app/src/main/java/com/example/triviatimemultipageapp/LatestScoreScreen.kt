package com.example.triviatimemultipageapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LatestScoreScreen : AppCompatActivity() {
    private lateinit var returnToMenu_Button : Button
    private lateinit var numberOfQuestions_TextView : TextView
    private lateinit var category_TextView : TextView
    private lateinit var difficulty_TextView : TextView
    private lateinit var type_TextView : TextView
    private lateinit var score_TextView : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_latest_score_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        numberOfQuestions_TextView = findViewById(R.id.LS_Number_Of_Q_Display)
        category_TextView = findViewById(R.id.LS_Category_Display)
        difficulty_TextView = findViewById(R.id.LS_Difficulty_Display)
        type_TextView = findViewById(R.id.LS_Question_Type_Display)
        score_TextView = findViewById(R.id.LS_Score_Display)
        returnToMenu_Button = findViewById(R.id.Return_MainMenu_From_LS)

        val sharedPreferences = this.getSharedPreferences("Trivia Settings", Context.MODE_PRIVATE)
        var score = sharedPreferences.getInt("CorrectAnsScore", 0)
        val number_of_questions = sharedPreferences.getInt("number of questions", 5)
        var category = sharedPreferences.getString("category", "Any")
        var difficulty = sharedPreferences.getString("difficulty", "Any")
        var type = sharedPreferences.getString("type", "Any")

        println("score is : ${score}")
        numberOfQuestions_TextView.setText("${number_of_questions}")
        category_TextView.setText("${category}")
        difficulty_TextView.setText("${difficulty}")
        type_TextView.setText("${type}")
        score_TextView.setText("${score} / ${number_of_questions}")

        returnToMenu_Button.setOnClickListener(){
            val intent = Intent (this@LatestScoreScreen, MainActivity::class.java)
            startActivity(intent)
        }
    }
}