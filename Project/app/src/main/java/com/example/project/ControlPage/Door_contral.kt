package com.example.project.ControlPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.project.R
import kotlinx.android.synthetic.main.activity_air_contral.*
import kotlinx.android.synthetic.main.activity_door_contral.*
import kotlinx.android.synthetic.main.activity_light_contral.*
import kotlinx.android.synthetic.main.progress_dialog.view.*
import okhttp3.*
import java.io.IOException

class Door_contral : AppCompatActivity() {
    var response_=""

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
                var client= OkHttpClient()
                var builder= Request.Builder()
                var body= FormBody.Builder()
                    .add("Ordername","OpenDoor")
                    .add("OrderArgument","3")
                    .add("OrderDescription","1")
                    .build()
                var request=builder
                    .post(body)
                    .url("http://192.168.10.166/IOTApi/order/add")
                    .build()
                val view=LayoutInflater.from(this).inflate(R.layout.progress_dialog,null)
                var dialog=AlertDialog
                    .Builder(this)
                    .setView(view)
                    .setCancelable(false)
                    .show()
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
                        var result =response.body!!.string()
                        Thread{
                            var times=0
                            while(true)
                            {
                                Thread.sleep(500)
                                Response(result)
                                if (response_=="success") {
                                    runOnUiThread {
                                        dialog.dismiss()
                                        Toast.makeText(
                                            applicationContext,
                                            "Success!!",
                                            Toast.LENGTH_SHORT).show()
                                        btn_door.background=resources.getDrawable(R.drawable.door_button_open)
                                        btn_door.setImageResource(R.drawable.open_lock)
                                        }
                                    Thread {
                                        Thread.sleep(3000)
                                        runOnUiThread {
                                            btn_door.background =
                                                resources.getDrawable(R.drawable.door_button_close)
                                            btn_door.setImageResource(R.drawable.close_lock)
                                        }
                                    }.start()
                                    response_=""
                                    break
                                }
                                else
                                {
                                    runOnUiThread {
                                        when (times % 4) {
                                            0 -> view.progress_text.text =
                                                resources.getString(R.string.progress_text1)
                                            1 -> view.progress_text.text =
                                                resources.getString(R.string.progress_text2)
                                            2 -> view.progress_text.text =
                                                resources.getString(R.string.progress_text3)
                                            3 -> view.progress_text.text =
                                                resources.getString(R.string.progress_text4)
                                        }
                                    }
                                    times++
                                }
                                if (times==12) {
                                    runOnUiThread {
                                        dialog.dismiss()
                                        Toast.makeText(
                                            applicationContext,
                                            "Fail!!",
                                            Toast.LENGTH_SHORT).show()
                                    }
                                    response_=""
                                    break
                                }
                            }
                        }.start()
                    }
                }
            })
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
}