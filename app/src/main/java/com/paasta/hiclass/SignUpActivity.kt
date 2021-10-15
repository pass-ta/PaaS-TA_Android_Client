package com.paasta.hiclass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.paasta.hiclass.databinding.ActivitySignUpBinding
import com.paasta.hiclass.model.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private  var mBinding:ActivitySignUpBinding?=null
    private  val binding get()=mBinding!!
    private var role:String="teacher"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        init()
    }
    private fun init(){
        mBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addEventListener()

    }
    private fun addEventListener() {
        binding.btnSignup.setOnClickListener {

            binding.radiobtnLogin.setOnCheckedChangeListener { radioGroup, i ->
                when(i){
                    R.id.radiobtn_teacher -> role="teacher"
                    R.id.radiobtn_student -> role="student"
                }
            }
            signUp(binding.editEmail?.text.toString(),binding.editPassword?.text.toString(),binding.editName?.text.toString(),role)
        }
    }
    private fun signUp(
        email: String,
        password: String,
        name: String,
        role: String
    ) {
        RetrofitClient.retrofitservice.requestSignUp(
            email,
            password,
            name,
            role,
        ).enqueue(object : Callback<UserData> {
            override fun onFailure(call: Call<UserData>, t: Throwable) {
                //실패시 작업 -> 간단하게 Dialog 띄어주기
                Toast.makeText(
                    applicationContext,
                    "회원가입 실패",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                //데이터를 받아서 저장해줌.

                val body = response.body()
                if(response.body()?.name!="Fail") {
                    val loginDB = LoginDB(context = applicationContext)
                    loginDB.insertDB(body!!.email,body!!.password,body!!.name,body!!.image)
                    Toast.makeText(applicationContext, "홈 화면으로 이동합니다", Toast.LENGTH_LONG).show()
                    startActivity(Intent(applicationContext, MainActivity::class.java))
//                    progressDialog.cancel()
                }else {
                    Toast.makeText(applicationContext, "해당 이메일은 이미 가입되어 있습니다", Toast.LENGTH_SHORT).show()
//                    progressDialog.cancel()
                }

            }
        })


    }
}