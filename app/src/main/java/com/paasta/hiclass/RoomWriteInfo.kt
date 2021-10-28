package com.paasta.hiclass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.paasta.hiclass.databinding.ActivityClassWriteInfoBinding
import com.paasta.hiclass.databinding.ActivityLoginBinding
import com.paasta.hiclass.databinding.ActivityRoomWriteInfoBinding

class RoomWriteInfo : AppCompatActivity() {

    private  var mBinding: ActivityRoomWriteInfoBinding?=null
    private  val binding get()=mBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_write_info)

        mBinding = ActivityRoomWriteInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.check.setOnClickListener {

            startActivity(Intent(applicationContext, RoomCamera::class.java))
        }

    }
}