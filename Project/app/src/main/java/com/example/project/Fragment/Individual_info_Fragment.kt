package com.example.project.Fragment

import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.project.R
import kotlinx.android.synthetic.main.fragment_individual_info.*
import kotlinx.android.synthetic.main.fragment_individual_info.view.*
import okhttp3.*
import java.io.IOException

class Individual_info_Fragment(var username:String) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var client= OkHttpClient()
        var view=LayoutInflater.from(context).inflate(R.layout.fragment_individual_info,container,false)

        view.setting_username.text=resources.getString(R.string.username)+":"+username

        view.btn_change_password.setOnClickListener {
            if (setting_password.text.toString()!=setting_passwordconfirm.text.toString()) {
                activity?.runOnUiThread {
                    Toast.makeText(
                        context,
                        "Password is different!!",
                        Toast.LENGTH_SHORT
                    ).show()
                    setting_password.text.clear()
                    setting_passwordconfirm.text.clear()
                }
            }
            var body= FormBody.Builder()
                .add("username",username)
                .add("password",setting_password.text.toString())
                .build()
            var request=Request.Builder()
                .post(body)
                .url("http://192.168.10.166/IOTApi/login/change")
                .build()
            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    activity?.runOnUiThread {
                    Toast.makeText(
                            context,
                            "Fail to Connected to Service!!",
                            Toast.LENGTH_SHORT
                        ).show()
                        println(e.message.toString())
                        setting_password.text.clear()
                        setting_passwordconfirm.text.clear()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    activity?.runOnUiThread {
                        Toast.makeText(
                            context,
                            "Success!!",
                            Toast.LENGTH_SHORT
                        ).show()
                        setting_password.text.clear()
                        setting_passwordconfirm.text.clear()
                    }
                }
            })
        }

        return view
    }

}