package com.example.project.ControlPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.project.Fragment.Aboutus_Fragment
import com.example.project.Fragment.Individual_info_Fragment
import com.example.project.R
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.fragment_individual_info.*

class Setting : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        init()
    }
    private fun init()
    {
        var info_Fragment:Fragment=Individual_info_Fragment(intent.getStringExtra("username").toString())
        var aboutus_Fragment:Fragment=Aboutus_Fragment()
        var info:Boolean=false
        var aboutus:Boolean=false
        txt_info.setOnClickListener {
            info=!info
            if(info) {
                setFragment(info_Fragment,R.id.layout_info)
            }
            else{
                removeFragment(info_Fragment)
            }
        }
        txt_setting.setOnClickListener {
            //我不想寫辣冰鳥
            //我太難了
        }
        txt_aboutus.setOnClickListener {
            aboutus=!aboutus
            if(aboutus) {
                setFragment(aboutus_Fragment,R.id.layout_aboutus)
            }
            else{
                removeFragment(aboutus_Fragment)
            }
        }
        btn_back_setting.setOnClickListener {
            finish()
        }
    }

    private fun setFragment(fragment: Fragment,id:Int)
    {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(id,fragment)
        transaction.commit()
    }

    private fun removeFragment(fragment: Fragment)
    {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.remove(fragment)
        transaction.commit()
    }
}