package com.example.project.AccountPage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.project.R
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.*
import java.io.IOException

class Login : AppCompatActivity() {

    //var result:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }
    fun init()
    {
        btn_Login.setOnClickListener {
            var client=OkHttpClient()
            var builder=Request.Builder()
            if (editText_password.text==null){
                Toast.makeText(
                    applicationContext,
                    "Password can't be empty!!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if (editText_user==null){
                Toast.makeText(
                    applicationContext,
                    "Username can't be empty!!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else
            {
                val body=FormBody.Builder()
                    .add("username",editText_user.text.toString())
                    .add("password",editText_password.text.toString())
                    .build()
                var request=builder
                    .post(body)
                    .url("http://192.168.31.1/IOTApi/login/check")
                    .build()

                client.newCall(request).enqueue(object :Callback{
                    override fun onFailure(call: Call, e: IOException) {
                        runOnUiThread(Runnable {
                            Toast.makeText(
                                applicationContext,
                                "Fail to Connected to Service!!",
                                Toast.LENGTH_SHORT
                            ).show()
                            println(e.message.toString())
                        })
                    }

                    override fun onResponse(call: Call, response: Response) {
                        var result=response.body!!.string()
                        runOnUiThread(Runnable {
                            Toast.makeText(
                                applicationContext,
                                "Login Success!!",
                                Toast.LENGTH_SHORT
                            ).show()
                            if(result=="true")
                            {
                                var data = Intent().apply { putExtra("User",editText_user.text.toString()) }
                                setResult(1,data)
                                finish()
                            }
                            else
                            {
                                Toast.makeText(
                                    applicationContext,
                                    "Login Fail!!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                    }
                })
            }
        }//btn_Login
        btn_register.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }
    }
}