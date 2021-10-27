package com.paasta.hiclass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.animation.Animator
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.hardware.Camera
import android.hardware.Camera.CameraInfo
import android.hardware.Camera.PictureCallback
import android.provider.MediaStore
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class RoomCamera : AppCompatActivity() {

    var sv_viewFinder: SurfaceView? = null
    var sh_viewFinder: SurfaceHolder? = null
    var camera: Camera? = null
    var myfile: File? = null
    var btn_shutter: Button? = null
    var btn_again: Button? = null
    var room_sendlottie: Button? = null
    var iv_preview: ImageView? = null
    var fos: FileOutputStream? = null
    private var dbemail: String? = null
    private lateinit var body: MultipartBody.Part
    var inProgress = false
    var myimage: File? = null
    var index:String? =null
    var roomname:String? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_camera)
        //카메라 권한의 승인 상태 가져오기
        val cameraPermission = ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)

        if(cameraPermission != PackageManager.PERMISSION_GRANTED){
            //승인되지 않았다면 권한 요청 프로세스 진행
            requestPermission()
        }
        init();
    }

    private fun init() {

    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.CAMERA),
                1000
        )

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1000) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) { //거부
                Toast.makeText(this@RoomCamera, "카메라 권한을 허용해주세요.", Toast.LENGTH_SHORT).show()
                finish()
            } } }

            }
