package com.example.project.ControlPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.project.R
import kotlinx.android.synthetic.main.activity_door_contral.*
import com.example.project.classes.Response

class Door_contral : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_door_contral)
        init()
    }

    private fun init() {
        btn_back_door.setOnClickListener {
            finish()
        }
        btn_door.setOnClickListener {
            Response().onResponse(this,"Door")
        }
    }
}