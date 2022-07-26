package com.example.thequizapp

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Color.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.withStyledAttributes

class QuizWindow : AppCompatActivity(), View.OnClickListener {

    var score:Int=0
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
        questionstv = findViewById(R.id.question)

        totalquestionstv.text= "Total Questions: $totalQuestion"

        loadNewQuestions()

        ansa.setOnClickListener (this)
        ansb.setOnClickListener (this)
        ansc.setOnClickListener (this)
        ansd.setOnClickListener (this)
        submitbtn.setOnClickListener(this)

    }

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

            Toast.makeText(this , "Answer: $actualAnswer \n score: $score/$totalQuestion ", Toast.LENGTH_SHORT).show()
            currentQuestionIndex++

            if (currentQuestionIndex >= totalQuestion-1){
                submitbtn.text = "Submit"
            }
            loadNewQuestions()


        }
        else{
            selectedAns = clickedButton.text.toString()
            clickedButton.setBackgroundColor(MAGENTA)

//            button shows green color if right else red
//            if (selectedAns == QuestionAnswers.answers[currentQuestionIndex] ){
//                clickedButton.setBackgroundColor(GREEN) }
//            else{
//                clickedButton.setBackgroundColor(RED)
//            }


        }
    }


    private fun loadNewQuestions(){

        if (currentQuestionIndex == totalQuestion){
            finishQuiz()
            return
        }

        questionstv.text = QuestionAnswers.questions[currentQuestionIndex]
        ansa.text = QuestionAnswers.options[currentQuestionIndex][0]
        ansb.text = QuestionAnswers.options[currentQuestionIndex][1]
        ansc.text = QuestionAnswers.options[currentQuestionIndex][2]
        ansd.text = QuestionAnswers.options[currentQuestionIndex][3]

    }

    private fun finishQuiz(){
        var passStatus  = "" ;
        passStatus = if ((score >= totalQuestion*0.6 )&& (score < totalQuestion*0.75)){
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
            .setPositiveButton("Restart") { dialogInterface, i -> restartQuiz() }
            .setCancelable(false)
            .show() 
    }
     private fun restartQuiz() {
        score=0
        currentQuestionIndex=0
         submitbtn.text = "Next"
        loadNewQuestions()
    }

}

