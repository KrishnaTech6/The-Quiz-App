package com.example.thequizapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart: Button = findViewById(R.id.btn_start)
        val et_Name: EditText = findViewById(R.id.et_name)
        btnStart.setOnClickListener {

            if (et_Name.text.toString() == "" ){
                AlertDialog.Builder(this)
                    .setTitle("This Field Can't Be Empty")
                    .setMessage("Please Enter your Name!!")
                    .show();
            }
            else{
                val intent = Intent(this, QuizWindow::class.java)
                startActivity(intent)

            }



        }
    }
}