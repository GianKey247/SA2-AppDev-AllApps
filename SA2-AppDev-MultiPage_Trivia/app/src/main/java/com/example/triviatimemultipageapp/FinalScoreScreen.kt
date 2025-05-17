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

class FinalScoreScreen : AppCompatActivity() {
    private lateinit var numberOfQuestions_TextView : TextView
    private lateinit var correctScore_TextView : TextView
    private lateinit var wrongScore_TextView : TextView
    private lateinit var difficulty_TextView : TextView
    private lateinit var returnToMain_Button : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_final_score_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        numberOfQuestions_TextView=findViewById(R.id.FS_Number_Of_Q_Display)
        correctScore_TextView=findViewById(R.id.FS_Correct_Display)
        wrongScore_TextView=findViewById(R.id.FS_Wrong_Display)
        difficulty_TextView=findViewById(R.id.FS_Difficulty_Display)
        returnToMain_Button=findViewById(R.id.Return_MainMenu_From_FS)
        val sharedPreferences = this.getSharedPreferences("Trivia Settings", Context.MODE_PRIVATE)
        val number_of_questions=intent.getIntExtra("NumberOfQuestions",0)
        val correct_score = intent.getIntExtra("CorrectAnsScore", 0)
        val wrong_score = intent.getIntExtra("WrongAnsScore", 0)
        var difficulty = sharedPreferences.getString("difficulty", "Any")
        numberOfQuestions_TextView.setText("$number_of_questions")
        correctScore_TextView.setText("$correct_score")
        wrongScore_TextView.setText("$wrong_score")
        difficulty_TextView.setText("$difficulty")
        returnToMain_Button.setOnClickListener(){
            var intent = Intent(this@FinalScoreScreen, MainActivity::class.java)
            startActivity(intent)
        }

    }
}