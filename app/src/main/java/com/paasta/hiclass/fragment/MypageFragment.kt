package com.paasta.hiclass.fragment

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.loader.content.CursorLoader
import com.paasta.hiclass.LoginActivity
import com.paasta.hiclass.MainActivity
import com.paasta.hiclass.R
import com.paasta.hiclass.RetrofitClient
import com.paasta.hiclass.databinding.FragmentMypageBinding
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL


class MypageFragment : Fragment() {


    val Gallery =1

    private  var mBinding: FragmentMypageBinding?=null
    private  val binding get()=mBinding!!

    private var imgUri: Uri? = null
    lateinit var bodyFile: RequestBody
    lateinit var body: MultipartBody.Part
    var contentResolver: ContentResolver? = null

    private lateinit var addImageBtn: ImageView
    private lateinit var newImage: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_mypage,container, false)

        getProfileImg()
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addImageBtn = view.findViewById(R.id.btn_add_image)
        newImage = view.findViewById(R.id.img_profile)
        addEventListener()
    }

    private fun addEventListener() {

        addImageBtn.setOnClickListener{loadImage()}

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Gallery){
            imgUri = data?.data
            contentResolver = getActivity()?.getContentResolver()
            try{
                var bitmap : Bitmap = MediaStore.Images.Media.getBitmap(contentResolver,imgUri)
                newImage.setImageBitmap(bitmap)
                addProfileImg()
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

    private fun addProfileImg(){

        val file: File = File(getFullPathFromUri(imgUri))
        Log.d("방 사진1", file.toString())
        bodyFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        Log.d("방 사진2", bodyFile.toString())
        body = MultipartBody.Part.createFormData("image", (LoginActivity.prefs.getString("email","") + ".jpg"), bodyFile)

        RetrofitClient.retrofitservice.requestAddProfileImage(body).enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(
                    getActivity(),
                    "통신 실패",
                    Toast.LENGTH_LONG
                ).show()

            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                Toast.makeText(
                    getActivity(),
                    "변경 완료",
                    Toast.LENGTH_LONG
                ).show()
                val body = response.body()
                Log.d("변경", body.toString())

            }
        })
    }

    private fun getProfileImg(){
        RetrofitClient.retrofitservice.requestProfileImage(LoginActivity.prefs.getString("email","")).enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(
                    getActivity(),
                    "통신 실패",
                    Toast.LENGTH_LONG
                ).show()

            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                Toast.makeText(
                    getActivity(),
                    "변경 완료",
                    Toast.LENGTH_LONG
                ).show()
                val body = response.body()
//                val is = response.body().byteStream()
//                val bitmap = BitmapFactory.decodeStream(is)
                Log.d("이미지 수신", body.toString())
//                newImage.setImageBitmap(ImageLoader.loadImage(img))


            }
        })
    }

    //이미지 절대경로 구하기
    fun getFullPathFromUri(fileUri: Uri?): String? {
        var fullPath: String? = null
        val column = "_data"
        var cursor: Cursor? = contentResolver!!.query(fileUri!!, null, null, null, null)
        if (cursor != null) {
            cursor.moveToFirst()
            var document_id = cursor.getString(0)
            if (document_id == null) {
                for (i in 0 until cursor.columnCount) {
                    if (column.equals(cursor.getColumnName(i), ignoreCase = true)) {
                        fullPath = cursor.getString(i)
                        break
                    }
                }
            } else {
                document_id = document_id.substring(document_id.lastIndexOf(":") + 1)
                cursor.close()
                val projection = arrayOf(column)
                try {
                    cursor = contentResolver!!.query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        MediaStore.Images.Media._ID + " = ? ",
                        arrayOf(document_id),
                        null
                    )
                    if (cursor != null) {
                        cursor.moveToFirst()
                        fullPath = cursor.getString(cursor.getColumnIndexOrThrow(column))
                    }
                } finally {
                    if (cursor != null) cursor.close()
                }
            }
        }
        return fullPath
    }

}
//object ImageLoader{
//
//    fun loadImage(imageUrl: String): Bitmap? {
//        val bmp: Bitmap? = null
//        try {
//
//            val url = URL(imageUrl)
//            val stream = url.openStream()
//
//            return BitmapFactory.decodeStream(stream)
//
//        } catch (e: MalformedURLException) {
//            e.printStackTrace()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        return bmp
//    }
//
//}