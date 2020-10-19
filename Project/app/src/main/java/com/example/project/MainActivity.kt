package com.example.project

import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.project.ControlPage.Air_contral
import com.example.project.ControlPage.Door_contral
import com.example.project.ControlPage.Light_contral
import com.example.project.ControlPage.Setting
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Attributes

class MainActivity : AppCompatActivity() {
    var USER_NAME=1
    var NAME_GET:String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //startActivityForResult(Intent(this,Login::class.java),USER_NAME)
        btn_air.setOnClickListener {
            startActivity(Intent(this,Air_contral::class.java))
        }
        btn_door.setOnClickListener {
            startActivity(Intent(this,Door_contral::class.java))
        }
        btn_light.setOnClickListener {
            startActivity(Intent(this,Light_contral::class.java))
        }
        btn_setting.setOnClickListener {
            startActivity(Intent(this,Setting::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(resultCode)
        {
            1->NAME_GET=data!!.getStringExtra("User")
        }
    }

}