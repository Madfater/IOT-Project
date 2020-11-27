package com.example.project.ControlPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.project.R
import kotlinx.android.synthetic.main.activity_air_contral.*
import com.example.project.classes.Response

class Air_contral : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_air_contral)
        init()
    }
    private fun init() {
        btn_back_air.setOnClickListener {
            finish()
        }
        Switch_temp.setOnClickListener {
            Response().RS()
            if (Switch_temp.isChecked)
                Response().onResponse(this, "Aircon_open")
            else
                Response().onResponse(this, "Aircon_close")
        }
        btn_temp_up.setOnClickListener {
            isOpen("Aircon_tempup")
        }
        btn_temp_down.setOnClickListener {
            isOpen("Aircon_tempdown")
        }
        btn_fan.setOnClickListener {
            isOpen("Aircon_fna")
        }
    }
    private  fun isOpen(Forniture:String)
    {
        Response().RS()
        if(Switch_temp.isChecked)
            Response().onResponse(this, Forniture)
        else
            Response().short_toast( resources.getString(R.string.aircon_not_open),this)
    }
}
