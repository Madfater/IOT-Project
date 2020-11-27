package com.example.project.classes

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.project.R
import kotlinx.android.synthetic.main.activity_air_contral.*
import kotlinx.android.synthetic.main.activity_door_contral.*
import kotlinx.android.synthetic.main.activity_light_contral.*
import kotlinx.android.synthetic.main.progress_dialog.view.*
import okhttp3.*
import okhttp3.Response
import java.io.IOException


class Response {

    fun onResponse(activity: Activity, Forniture: String) {
        var client = OkHttpClient()
        var request = Request(Forniture)
        val view = LayoutInflater.from(activity).inflate(R.layout.progress_dialog, null)
        var dialog = AlertDialog
            .Builder(activity)
            .setView(view)
            .setCancelable(false)
            .show()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activity.runOnUiThread {
                    short_toast(activity.resources.getString(R.string.Failconnect),activity)
                    dialog.dismiss()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                activity.runOnUiThread {
                    var result = response.body!!.string()
                    Thread {
                        var times = 0
                        while (true) {
                            Thread.sleep(500)
                            if (getResponse(result)) {
                                activity.runOnUiThread {
                                    dialog.dismiss()
                                    short_toast(activity.resources.getString(R.string.Success),activity)
                                    Door_action(Forniture,activity)
                                }
                                break
                            } else {
                                activity.runOnUiThread {
                                    Prograss_bar(times,view,activity)
                                }
                                times++
                            }
                            if (times == 12) {
                                activity.runOnUiThread {
                                    dialog.dismiss()
                                    Fail_action(Forniture, activity)
                                    short_toast(activity.resources.getString(R.string.Fail),activity)
                                }
                                break
                            }
                        }
                    }.start()
                }
            }
        })
    }

    private  fun  Door_action(forniture: String,activity: Activity)
    {
        if(forniture=="Door")
        {
            activity.btn_door.background=activity.resources.getDrawable(R.drawable.door_button_open)
            activity.btn_door.setImageResource(R.drawable.open_lock)
            Thread {
                Thread.sleep(3000)
                activity.runOnUiThread {
                    activity.btn_door.background =
                        activity.resources.getDrawable(R.drawable.door_button_close)
                    activity.btn_door.setImageResource(R.drawable.close_lock)
                }
            }.start()
        }
    }

    private  fun Prograss_bar(times:Int,view: View,activity: Activity){
        when (times % 4) {
            0 -> view.progress_text.text =
                activity.resources.getString(R.string.progress_text1)
            1 -> view.progress_text.text =
                activity.resources.getString(R.string.progress_text2)
            2 -> view.progress_text.text =
                activity.resources.getString(R.string.progress_text3)
            3 -> view.progress_text.text =
                activity.resources.getString(R.string.progress_text4)
        }
    }

    private fun Fail_action(Forniture: String, activity: Activity) {
        if (Forniture == "Aircon_open")
            activity.Switch_temp.isChecked = false
        else if (Forniture == "Aircon_close")
            activity.Switch_temp.isChecked = true
        else if(Forniture =="light_open")
            activity.Switch_light.isChecked = false
        else if(Forniture == "light_close")
            activity.Switch_light.isChecked = true
    }

    private fun Body(forniture: String): FormBody {
        var result = FormBody.Builder()
        when (forniture) {
            "Aircon_open" -> result.add("Ordername", "OpenAircon")
                .add("OrderArgument", "1")
                .add("OrderDescription", "1")

            "Aircon_close" -> result.add("Ordername", "CloseAircon")
                .add("OrderArgument", "1")
                .add("OrderDescription", "0")

            "Aircon_tempup" -> result.add("Ordername", "TempUp")
                .add("OrderArgument", "1")
                .add("OrderDescription", "2")

            "Aircon_tempdown" -> result.add("Ordername", "TempDown")
                .add("OrderArgument", "1")
                .add("OrderDescription", "-1")

            "light_open" -> result.add("Ordername", "OpenLight")
                .add("OrderArgument", "2")
                .add("OrderDescription", "1")

            "light_close" -> result.add("Ordername", "Closelight")
                .add("OrderArgument", "2")
                .add("OrderDescription", "0")

            "Aircon_fan"->result.add("Ordername","Fan")
                .add("OrderArgument","1")
                .add("OrderDescription","3")

            else -> result.add("Ordername", "OpenDoor")
                .add("OrderArgument", "3")
                .add("OrderDescription", "1")
        }
        return result.build()
    }

    private fun Request(forniture: String): Request {
        var result =
            Request.Builder().post(Body(forniture)).url("http://192.168.10.166/IOTApi/order/add")
                .build()
        return result
    }

    private fun getResponse(id: String): Boolean {
        var result = false
        var client = OkHttpClient()
        var body = FormBody.Builder()
            .add("res", id)
            .build()
        var request = Request.Builder()
            .post(body)
            .url("http://192.168.10.166/IOTApi/order/check")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                result = false
            }

            override fun onResponse(call: Call, response: Response) {
                result = true
            }
        })
        return result
    }

    fun short_toast(text:String,activity: Activity)
    {
        Toast.makeText(
            activity.applicationContext,
            text,
            Toast.LENGTH_SHORT
        ).show()
    }

    fun RS()
    {
        Thread{
           Thread.sleep(10)
        }.start()
    }
}