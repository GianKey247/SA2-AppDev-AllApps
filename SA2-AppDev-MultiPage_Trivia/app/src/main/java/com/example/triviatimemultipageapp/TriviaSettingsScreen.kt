package com.example.triviatimemultipageapp

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.text.Html
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.ArrayList

import kotlinx.parcelize.Parcelize
@Parcelize
data class Question(
    val type: String,
    val difficulty: String,
    val category: String,
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>
): Parcelable
{
    fun getAnswersList (): List<String> {
        val answer_List = incorrect_answers.toMutableList()
        answer_List.add(correct_answer)
        if(type=="boolean") {
            answer_List.sortDescending()
        }
        else {
            answer_List.shuffle()
        }
        return answer_List.map { it.decodeHtml() }
    }
    fun  checkIfUserAns(userAns : String): Boolean {
        return userAns == correct_answer
    }
}
fun String.decodeHtml(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        Html.fromHtml(this).toString()
    }
}

class TriviaSettingsScreen : AppCompatActivity() {
    private lateinit var userNumberOfQuestion_EditText: EditText
    private lateinit var userCategory_Spinner: Spinner
    private lateinit var userDifficulty_Spinner: Spinner
    private lateinit var userType_Spinner: Spinner
    private lateinit var startTrivia_Button : Button
    val dictCategories = mapOf(
        "Any" to 0,
        "General Knowledge" to 9,
        "Entertainment: Books" to 10,
        "Entertainment: Film" to 11,
        "Entertainment: Music" to 12,
        "Entertainment: Musicals & Theatres" to 13,
        "Entertainment: Television" to 14,
        "Entertainment: Video Games" to 15,
        "Entertainment: Board Games" to 16,
        "Science & Nature" to 17,
        "Science: Computers" to 18,
        "Science: Mathematics" to 19,
        "Mythology" to 20,
        "Sports" to 21,
        "Geography" to 22,
        "History" to 23,
        "Politics" to 24,
        "Art" to 25,
        "Celebrities" to 26,
        "Animals" to 27,
        "Vehicles" to 28,
        "Entertainment: Comics" to 29,
        "Science: Gadgets" to 30,
        "Entertainment: Japanese Anime & Manga" to 31,
        "Entertainment: Cartoon & Animations" to 32
    )
    val listDifficulty = listOf("Any", "easy", "medium", "hard")
    val dictTypes = mapOf("Any" to "Any", "Muliple Choice" to "multiple" , "True or False" to "boolean")


    var selectedCatagory : String? = ""
    var selectedDifficulty : String? = ""
    var selectedType : String? = ""

    data class TriviaResponse(
        val response_code: Int,
        val results: List<Question>
    )


    interface TriviaApiService {
        @GET("api.php")
        fun getQuestions(
            @Query("amount") amount: Int,
            @Query("category") category: Int? = null,
            @Query("difficulty") difficulty: String? = null,
            @Query("type") type: String? = null
        ): Call<TriviaResponse>
    }

    object RetrofitClient {
        val instance: TriviaApiService by lazy {
            Retrofit.Builder()
                .baseUrl("https://opentdb.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TriviaApiService::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_trivia_settings_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        userNumberOfQuestion_EditText = findViewById(R.id.User_Number_of_Questions)
        userCategory_Spinner = findViewById(R.id.User_Category)
        userDifficulty_Spinner = findViewById(R.id.User_Difficulty)
        userType_Spinner = findViewById(R.id.User_Type)
        startTrivia_Button = findViewById(R.id.Begin_Trivia_Button)

        userNumberOfQuestion_EditText.setText("5")

        startTrivia_Button.setOnClickListener{
            handleStartTriviaButton()
        }

        setDropDownMenus()

    }

    private fun setDropDownMenus() {
        val listCategories = dictCategories.keys.toList()
        val adapterCategory =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listCategories)
        userCategory_Spinner.adapter = adapterCategory
        userCategory_Spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                println(listCategories[position])
                selectedCatagory = listCategories[position]!!
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        val adapterDifficulty = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            listDifficulty.map { it.title() })
        userDifficulty_Spinner.adapter = adapterDifficulty
        userDifficulty_Spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    println(listDifficulty[position])
                    selectedDifficulty = listDifficulty[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        val listType = dictTypes.keys.toList()
        val adapterType =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listType)
        userType_Spinner.adapter = adapterType
        userType_Spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                println(listType[position])
                selectedType = listType[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun handleStartTriviaButton() {
        val number_of_questions = userNumberOfQuestion_EditText.text.toString().toInt()
        if (number_of_questions > 50 || number_of_questions < 0) {
            print("Invalid Input : Number of Questions")
            Toast.makeText(this, "Invalid Input : Number of Questions", Toast.LENGTH_SHORT).show()
            userNumberOfQuestion_EditText.setText("5")
        }
        selectedCatagory = if (selectedCatagory != "Any") selectedCatagory else null
        selectedDifficulty = if (selectedDifficulty != "Any") selectedDifficulty else null
        selectedType = if (selectedType != "Any") selectedType else null

        val call = RetrofitClient.instance.getQuestions(
            number_of_questions,
            dictCategories[selectedCatagory],
            selectedDifficulty,
            dictTypes[selectedType]
        )

        call.enqueue(object : Callback<TriviaResponse> {
            override fun onResponse(
                call: Call<TriviaResponse>,
                response: Response<TriviaResponse>
            ) {
                if (response.isSuccessful) {
                    val questions = response.body()?.results ?: emptyList()
                    saveTriviaStats()
                    val intent = Intent(this@TriviaSettingsScreen, QuestionScreen::class.java)
                    intent.putParcelableArrayListExtra("questions", ArrayList(questions))
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<TriviaResponse>, t: Throwable) {
                Toast.makeText(
                    this@TriviaSettingsScreen,
                    "Failed: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    fun saveTriviaStats () {
        var number_of_questions:Int = userNumberOfQuestion_EditText.text.toString().toInt()
        var category:String? = selectedCatagory
        var difficulty:String? = selectedDifficulty
        var type:String? = selectedType

        // Get SharedPreferences instance
        val sharedPreferences = this.getSharedPreferences("Trivia Settings", Context.MODE_PRIVATE)


        with(sharedPreferences.edit()) {
            putInt("number of questions", number_of_questions)
            putString("category", category)
            putString("difficulty", difficulty?.title())
            putString("type", type)
            apply()
        }




    }


}


