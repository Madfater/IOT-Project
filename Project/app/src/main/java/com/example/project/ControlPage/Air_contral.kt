package com.example.project.ControlPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.project.R
import kotlinx.android.synthetic.main.activity_air_contral.*
import kotlinx.android.synthetic.main.activity_light_contral.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.progress_dialog.view.*
import okhttp3.*
import java.io.IOException

class Air_contral : AppCompatActivity(){

    var response_=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_air_contral)
        init()
    }
    private fun init() {
        val view=LayoutInflater.from(this).inflate(R.layout.progress_dialog,null)
        var client= OkHttpClient()
        var builder= Request.Builder()
        btn_back_air.setOnClickListener {
            finish()
        }
        Switch_temp.setOnCheckedChangeListener{ compoundButton: CompoundButton, b: Boolean ->
            if (b)
            {
                var body= FormBody.Builder()
                    .add("Ordername","OpenAircon")
                    .add("OrderArgument","1")
                    .add("OrderDescription","1")
                    .build()
                var request=builder
                    .post(body)
                    .url("http://192.168.10.166/IOTApi/order/add")
                    .build()
                client.newCall(request).enqueue(object :Callback{
                    override fun onFailure(call: Call, e: IOException) {
                        runOnUiThread {
                            Toast.makeText(
                                applicationContext,
                                "Fail to Connected to Service!!",
                                Toast.LENGTH_SHORT
                            ).show()
                            println(e.message.toString())
                        }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        runOnUiThread {
                            showview(view,response.body!!.string())
                            AlertDialog
                                .Builder(this@Air_contral)
                                .setView(view)
                                .show()
                        }
                    }
                })
            }
            else
            {
                var body= FormBody.Builder()
                    .add("Ordername","CloseAircon")
                    .add("OrderArgument","1")
                    .add("OrderDescription","0")
                    .build()
                var request=builder
                    .post(body)
                    .url("http://192.168.10.166/IOTApi/order/add")
                    .build()
                client.newCall(request).enqueue(object :Callback{
                    override fun onFailure(call: Call, e: IOException) {
                        runOnUiThread {
                            Toast.makeText(
                                applicationContext,
                                "Fail to Connected to Service!!",
                                Toast.LENGTH_SHORT
                            ).show()
                            println(e.message.toString())
                        }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        runOnUiThread {
                            showview(view,response.body!!.string())
                            AlertDialog
                                .Builder(this@Air_contral,android.R.style.Theme_Material_Light_NoActionBar_Fullscreen)
                                .setView(view)
                                .show()
                        }
                    }
                })
            }
        }
    }
    private fun Response(id:String)
    {
        var client= OkHttpClient()
        var builder= Request.Builder()
        var body= FormBody.Builder()
            .add("res",id)
            .build()
        var request=builder
            .post(body)
            .url("http://192.168.10.166/IOTApi/order/check")
            .build()
        client.newCall(request).enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                response_="fail"
            }

            override fun onResponse(call: Call, response: Response) {
                response_=response.body!!.string()
            }
        })
    }
    private fun showview(view: View,id:String)
    {
        var times=0
        view.progressBar.visibility=VISIBLE
        Thread{
            while(true)
            {
                Thread.sleep(100)
                Response(id)
                if (response_=="success")
                {
                    runOnUiThread {
                        view.progressBar.visibility=GONE
                        Toast.makeText(
                            applicationContext,
                            "Success!",
                            Toast.LENGTH_SHORT
                        )
                    }
                    response_=""
                    break
                }
                else
                    times++
                if (times==50)
                {
                    runOnUiThread {
                        view.progressBar.visibility=GONE
                        Toast.makeText(
                            applicationContext,
                            "Fail!",
                            Toast.LENGTH_SHORT
                        )
                    }
                    response_=""
                    break
                }
            }
        }.start()
    }
}