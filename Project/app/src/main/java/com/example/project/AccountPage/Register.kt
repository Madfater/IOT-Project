package com.example.project.AccountPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.project.R
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.*
import java.io.IOException

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        btn_Registerfinish.setOnClickListener {
            println("qqq")
            if (editText_password_register.text.isEmpty() || editText_repassword_register.text.isEmpty() || editText_user_register.text.isEmpty())
            {
                Toast.makeText(
                    applicationContext,
                    "It can't be empty!!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if(editText_password_register.text.toString()!=editText_repassword_register.text.toString())
            {
                Toast.makeText(
                    applicationContext,
                    "Confirm password is different from Password!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else
            {
                var client=OkHttpClient()
                var builder=Request.Builder()
                var body=FormBody.Builder()
                    .add("username",editText_user_register.text.toString())
                    .add("password",editText_password_register.text.toString())
                    .build()
                var request=builder
                    .post(body)
                    .url("http://192.168.31.1/IOTApi/reg/do")
                    .build()
                client.newCall(request).enqueue(object :Callback{
                    override fun onFailure(call: Call, e: IOException) {
                        runOnUiThread(
                            Runnable {
                                Toast.makeText(
                                    applicationContext,
                                    "Fail to Connected to Service!!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                println(e.message.toString())
                            }
                        )
                    }

                    override fun onResponse(call: Call, response: Response) {
                        var result=response.body!!.string()
                        if(result=="true")
                        {
                            runOnUiThread( Runnable {
                                Toast.makeText(
                                    applicationContext,
                                    "Register Success!!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                println(result)
                                finish()})

                        }
                        else
                        {
                            runOnUiThread( Runnable {
                                Toast.makeText(
                                    applicationContext,
                                    "Register Fail!!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                println(result)}
                            )
                        }
                    }
                })

            }
        }
        btn_Cancel.setOnClickListener {
            finish()
        }
    }
}