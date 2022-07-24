package com.example.thequizapp

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Color.MAGENTA
import android.graphics.Color.WHITE
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

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

        submitbtn.text = "Next"

    }

    override fun onClick(view: View) {

        ansa.setBackgroundColor(WHITE)
        ansb.setBackgroundColor(WHITE)
        ansc.setBackgroundColor(WHITE)
        ansd.setBackgroundColor(WHITE)

        var clickedButton:Button = view as Button

        if (clickedButton == submitbtn){
            if (selectedAns == QuestionAnswers.answers[currentQuestionIndex]){
                score++
            }

            currentQuestionIndex++

            if (currentQuestionIndex >= totalQuestion-1){
                submitbtn.text = "Submit"
            }

            loadNewQuestions()

        }
        else{
            selectedAns = clickedButton.text.toString()
            clickedButton.setBackgroundColor(MAGENTA)

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

    fun finishQuiz(){
        var passStatus  = "" ;
        if (score >= totalQuestion*0.6){
            passStatus = "Passed"
        }
        else{
            passStatus = "Failed"
        }

        AlertDialog.Builder(this)
            .setTitle(passStatus)
            .setMessage("Score is $score out of $totalQuestion")
            .setPositiveButton("Restart", { dialogInterface, i -> restartQuiz() })
            .setCancelable(false)
            .show() ;
    }
     private fun restartQuiz() {
        score=0
        currentQuestionIndex=0
         submitbtn.text = "Next"
        loadNewQuestions()
    }

}

