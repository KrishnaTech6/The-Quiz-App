package com.example.thequizapp

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.withStyledAttributes
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import java.sql.Time
import kotlin.system.exitProcess

class QuizWindow : AppCompatActivity(), View.OnClickListener {

    var score:Int=0
    var i: Int = 0
    var totalQuestion= QuestionAnswers.questions.size
    var currentQuestionIndex:Int =0
    var selectedAns: String =""
    lateinit var questionstv: TextView
    lateinit var totalquestionstv: TextView
    lateinit var ansa: Button
    lateinit var ansb: Button
    lateinit var ansc: Button
    lateinit var ansd: Button
    lateinit var submitbtn: Button
    lateinit var hello: Button

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_window)
        //setting variables and calling them

        totalquestionstv= findViewById(R.id.totalQuestionstv)
        ansa = findViewById(R.id.ans_a)
        ansb = findViewById(R.id.ans_b)
        ansc = findViewById(R.id.ans_c)
        ansd = findViewById(R.id.ans_d)

        submitbtn = findViewById(R.id.submit)
        submitbtn.text = "Next"

        questionstv = findViewById(R.id.question)

        loadNewQuestions()

        ansa.setOnClickListener (this)
        ansb.setOnClickListener (this)
        ansc.setOnClickListener (this)
        ansd.setOnClickListener (this)
        submitbtn.setOnClickListener(this)

    }

    @SuppressLint("SetTextI18n")
    override fun onClick(view: View) {

       ansa.setBackgroundColor(WHITE)
       ansb.setBackgroundColor(WHITE)
       ansc.setBackgroundColor(WHITE)
        ansd.setBackgroundColor(WHITE)

        val clickedButton:Button = view as Button

        if (clickedButton == submitbtn){
            val actualAnswer = QuestionAnswers.answers[currentQuestionIndex]
            if (selectedAns == actualAnswer){
                score++ }
            else{score }  //otherwise it was sometimes showing score to 1, if no option was clicked

            when (selectedAns) {
                actualAnswer -> {
                    hello.setBackgroundColor(GREEN) }
                "" -> {
                    hello.setBackgroundColor(WHITE)
                    if (i==0) {Toast.makeText(this, "No Option Chosen", Toast.LENGTH_SHORT).show()
                    i=2} // i is used to show toast one time only if multiple time button is clicked
                }
                else -> {
                    hello.setBackgroundColor(RED)
                    when(actualAnswer){   //to show the right answer after submit
                        ansa.text.toString() -> ansa.setBackgroundColor(GREEN)
                        ansb.text.toString() -> ansb.setBackgroundColor(GREEN)
                        ansc.text.toString() -> ansc.setBackgroundColor(GREEN)
                        ansd.text.toString() -> ansd.setBackgroundColor(GREEN)
                    }
                }
            }



            //Toast.makeText(this , "Answer: $actualAnswer \n score: $score/$totalQuestion ", Toast.LENGTH_SHORT).show()


            if (selectedAns =="") {
                currentQuestionIndex}
            else {
                currentQuestionIndex++
                i=0
                submitbtn.visibility = View.INVISIBLE

                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        if (currentQuestionIndex >= totalQuestion-1){
                            submitbtn.text = "Submit"
                        }

                        loadNewQuestions()
                        ansa.setBackgroundColor(WHITE)
                        ansb.setBackgroundColor(WHITE)
                        ansc.setBackgroundColor(WHITE)
                        ansd.setBackgroundColor(WHITE)

                        submitbtn.visibility = View.VISIBLE
                    },
                    1000 // value in milliseconds
                )
            }
        }

        else{
            selectedAns = clickedButton.text.toString()
            clickedButton.setBackgroundColor(MAGENTA)
            hello = clickedButton
        }
    }


    @SuppressLint("SetTextI18n")
    private fun loadNewQuestions(){

        selectedAns =""  //so that when no option is chosen then string remains empty -> doesn't contain previous value
                        // doesn't show red color on submit button press ___ LINE 83

        if (currentQuestionIndex == totalQuestion){
            finishQuiz()
            return
        }
        totalquestionstv.text = "${currentQuestionIndex+1} out of $totalQuestion"

        questionstv.text = QuestionAnswers.questions[currentQuestionIndex]
        ansa.text = QuestionAnswers.options[currentQuestionIndex][0]
        ansb.text = QuestionAnswers.options[currentQuestionIndex][1]
        ansc.text = QuestionAnswers.options[currentQuestionIndex][2]
        ansd.text = QuestionAnswers.options[currentQuestionIndex][3]

    }

    private fun finishQuiz(){
        val passStatus = if ((score >= totalQuestion*0.6 )&& (score < totalQuestion*0.75)){
            "Good, You can Improve"
        } else if ((score >= totalQuestion*0.75 )&& (score < totalQuestion*0.9))
            "Very Good"
        else if ((score >= totalQuestion*0.9 )&& (score <= totalQuestion))
            "Excellent!!!"
        else{
            "You Can Improve"
        }

        AlertDialog.Builder(this)
            .setTitle(passStatus)
            .setMessage("Score is $score out of $totalQuestion")
            .setNegativeButton("Exit"){_,_ -> exitQuiz()}
            .setPositiveButton("Restart") { _, _ -> restartQuiz() }
            .setCancelable(false)
            .show() 
    }

     @SuppressLint("SetTextI18n")
     private fun restartQuiz() {
        score=0
        currentQuestionIndex=0
         submitbtn.text = "Next"
        loadNewQuestions()
    }

    private fun exitQuiz(){
        finish()
        exitProcess(0)
    }

//So that user can't go back during quiz
    override fun onBackPressed() {
        AlertDialog.Builder(this).setMessage("Quiz is Running!!").setNegativeButton("OK", null).show()
    }
}

