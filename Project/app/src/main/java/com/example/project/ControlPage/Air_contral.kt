package com.example.project.ControlPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.project.R
import kotlinx.android.synthetic.main.activity_air_contral.*
import kotlinx.android.synthetic.main.activity_light_contral.*

class Air_contral : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_air_contral)
        init()
    }
    private fun init() {
        btn_back_air.setOnClickListener {
            finish()
        }
    }
}