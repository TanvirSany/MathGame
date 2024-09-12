package com.example.mathgame

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    lateinit var textScore: TextView
    lateinit var textLife: TextView
    lateinit var textTIme: TextView

    lateinit var textQuestion: TextView
    lateinit var editTextAnswer: EditText

    lateinit var buttonOK: Button
    lateinit var buttonNext: Button

    var correctAnswer = 0
    var userScore = 0
    var userLife = 3

    lateinit var timer: CountDownTimer
    private val startTImerInMillis: Long = 20000
    var timeLeftInMillis: Long = startTImerInMillis



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar!!.title = "Addition"

        textScore = findViewById(R.id.textViewScore)
        textLife = findViewById(R.id.textViewlife)
        textTIme = findViewById(R.id.textViewTime)
        textQuestion = findViewById((R.id.textViewQuestion))
        editTextAnswer = findViewById(R.id.editTextAnswer)
        buttonOK = findViewById(R.id.buttonOk)
        buttonNext = findViewById(R.id.buttonNext)


        getQus()

        buttonOK.setOnClickListener{
            val input = editTextAnswer.text.toString()

            if(input == ""){
                Toast.makeText(applicationContext, "Please write an answer or click next button", Toast.LENGTH_SHORT).show()
            } else {

                pauseTime()

                val userAnswer = input.toInt()

                if(userAnswer == correctAnswer){
                    userScore = userScore + 10
                    textQuestion.text = "Congratulation your answer is correct"
                    textScore.text = userScore.toString()
                } else {
                    userLife--
                    textQuestion.text = "Sorry, your answer is wrong"
                    textLife.text = userLife.toString()

                }

            }


        }

        buttonNext.setOnClickListener{

            pauseTime()
            resetTimer()
            getQus()
            editTextAnswer.setText("")

        }

    }
    fun getQus(){
        val number1 = Random.nextInt(0,100)
        val number2 = Random.nextInt(0,100)

        textQuestion.text = "$number1 + $number2"

        correctAnswer = number1 + number2

        startTimer()
    }

    fun startTimer() {

        timer = object:CountDownTimer(timeLeftInMillis,1000){
            override fun onTick(milisUntilFinished: Long) {

                timeLeftInMillis = milisUntilFinished
                updateText()

            }

            override fun onFinish() {
                pauseTime()
                resetTimer()
                updateText()

                userLife--
                textLife.text = userLife.toString()
                textQuestion.text = "Sorry, your time is up"
            }

        }.start()

    }

    fun updateText(){

        val remainingTime: Int = (timeLeftInMillis/1000).toInt()
        textTIme.text = String.format(Locale.getDefault(),"%02d", remainingTime)


    }

    fun pauseTime() {

        timer.cancel()

    }

    fun resetTimer() {

        timeLeftInMillis = startTImerInMillis
        updateText()

    }

}