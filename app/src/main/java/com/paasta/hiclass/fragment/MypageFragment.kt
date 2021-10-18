package com.paasta.hiclass.fragment

import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.loader.content.CursorLoader
import com.paasta.hiclass.R
import com.paasta.hiclass.databinding.FragmentMypageBinding
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.lang.Exception

class MypageFragment : Fragment() {

    val Gallery =0

    private  var mBinding: FragmentMypageBinding?=null
    private  val binding get()=mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_mypage, null)
        // 처리
        mBinding = FragmentMypageBinding.inflate(layoutInflater)
        addEventListener()
        return view
    }

    private fun addEventListener() {
        binding.btnAddImage.setOnClickListener {

            loadImage()

//            val intent = Intent(Intent.ACTION_PICK)
//            intent.type = MediaStore.Images.Media.CONTENT_TYPE
//            startActivityForResult(intent, SELECT_PICTURE)
//            binding.imageSettingSpaceAdd.setVisibility(View.VISIBLE)
        }
        binding.btn1.setOnClickListener {
            loadImage()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Gallery){
            var dataUri = data?.data
            val contentResolver: ContentResolver? = getActivity()?.getContentResolver()
            try{
                var bitmap : Bitmap = MediaStore.Images.Media.getBitmap(contentResolver,dataUri)
                binding.imgProfile.setImageBitmap(bitmap)
            }catch (e:Exception){

            }
        }else{

        }
    }

    private fun loadImage(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action= Intent.ACTION_GET_CONTENT

        startActivityForResult(Intent.createChooser(intent,"Load Picture"),Gallery)
    }
}