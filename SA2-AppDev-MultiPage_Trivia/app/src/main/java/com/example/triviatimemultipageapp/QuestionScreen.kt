package com.example.triviatimemultipageapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale
fun String.title(): String = split(" ").map { it.capitalize(Locale.ROOT) }.joinToString(" ")
class QuestionScreen : AppCompatActivity() {
    private lateinit var difficulty_TextView : TextView
    private lateinit var category_TextView : TextView
    private lateinit var question_TextView : TextView
    private lateinit var questionTracker_TextView : TextView
    private lateinit var answers_LinearLayout : LinearLayout
    private lateinit var result_TextView : TextView
//    private lateinit var nextQuestion_Button : TextView
    private var question_index:Int=0
    private var correct_score:Int=0
    private var wrong_score:Int=0
    private var user_difficullty:String = "Any"

    lateinit var questionsData:ArrayList<Question>



    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_question_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        difficulty_TextView = findViewById(R.id.QS_Difficulty_Display)
        category_TextView = findViewById(R.id.QS_Category_Display)
        question_TextView = findViewById(R.id.Question_Display)
        questionTracker_TextView = findViewById(R.id.QuestionNumber_Tracker)
        answers_LinearLayout = findViewById(R.id.answersLayout)
        result_TextView = findViewById(R.id.Question_Result_Display)
//        nextQuestion_Button = findViewById(R.id.Next_Question)
        questionsData= intent.getParcelableArrayListExtra("questions",Question::class.java)!!
        setQuestionDetails(questionsData[question_index])
//        nextQuestion_Button.setOnClickListener{
//            question_index++
//            if (question_index == questionsData.size){
//                handleQuizFinish()
//            }
//            setQuestionDetails(questionsData[question_index])
//
//        }

    }




    fun setQuestionDetails(question_data:Question){
        var difficulty = question_data.difficulty.title()
        var category = question_data.category.decodeHtml()
        var question = question_data.question.decodeHtml()
        var result=""
        result_TextView.setBackgroundColor(Color.TRANSPARENT)
        result_TextView.setText(result)
        var number_Of_Questions : Int = questionsData.size
        answers_LinearLayout.removeAllViews()
        difficulty_TextView.setText("Difficulty : ${difficulty}")
        category_TextView.setText("Category : ${category}")
        question_TextView.setText("${question_index+1}. ${question}")
        questionTracker_TextView.setText("${question_index+1} / ${number_Of_Questions}")
        question_data.getAnswersList().forEach{ answer ->
            run {
                val button = Button(this)
                button.setText(answer)
                button.setOnClickListener{
                    if (question_data.checkIfUserAns(answer)){
                        result = "Correct"
                        correct_score+=1
                        result_TextView.setBackgroundColor(Color.parseColor("#8000FA00"))
                    }
                    else {
                        result = "Wrong"
                        wrong_score+=1
                        result_TextView.setBackgroundColor(Color.parseColor("#80FF0000"))
                    }
                    result_TextView.setText(result)
                    val handler = Handler(Looper.getMainLooper())
                    handler.postDelayed({
                        if (question_index == number_Of_Questions-1){
                            println("${question_index}")
                            handleQuizFinish()
                        }else {
                            question_index++
                            println("Question index after increment: ${question_index}")
                            setQuestionDetails(questionsData[question_index])
                        }
                    }, 3000)

                }
                answers_LinearLayout.addView(button)
            }
        }
//        questionTracker_TextView.setText("Question Number ${currentQuestionNumber} / ${totalQuestionNumber}")
    }
    private fun handleQuizFinish() {
        val sharedPreferences = this.getSharedPreferences("Trivia Settings", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putInt("CorrectAnsScore", correct_score)
            apply()
        }
        val intent = Intent(this@QuestionScreen, FinalScoreScreen::class.java)
        intent.putExtra("NumberOfQuestions", questionsData.size)
        intent.putExtra("CorrectAnsScore", correct_score)
        intent.putExtra("WrongAnsScore", wrong_score)

        startActivity(intent)
    }
}