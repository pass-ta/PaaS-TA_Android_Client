package com.paasta.hiclass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.paasta.hiclass.model.UserData
import com.paasta.hiclass.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.RadioButton
import android.widget.RadioGroup
import com.paasta.hiclass.model.PreferenceUtil

class LoginActivity : AppCompatActivity() {

    private  var mBinding:ActivityLoginBinding?=null
    private  val binding get()=mBinding!!
    private var role:String="teacher"


    companion object { lateinit var prefs: PreferenceUtil }

    override fun onCreate(savedInstanceState: Bundle?) {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate(savedInstanceState)


        init()
    }
    private fun init(){
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addEventListener()

    }

    private fun addEventListener() {

        binding.radiobtnLogin.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.radiobtn_teacher -> role="teacher"
                R.id.radiobtn_student -> role="student"
            }
        }

        binding.btnLogin.setOnClickListener {

            login()
        }
        binding.txtSignup.setOnClickListener {
            startActivity(Intent(applicationContext, SignUpActivity::class.java))
        }
    }

    //로그인
    private fun login() {

        if (binding.editEmail?.text.toString()!="" && binding.editEmail?.text.toString()!="") {
////            val progressDialog: ProgressDialog = ProgressDialog(this)
////            progressDialog.setTitle("로그인중...")
////            progressDialog.show()
            RetrofitClient.retrofitservice.requestLogin(
                binding.editEmail.text.toString(),
                binding.editPassword.text.toString(),role
            ).enqueue(object : Callback<UserData> {
                override fun onFailure(call: Call<UserData>, t: Throwable) {
                    Toast.makeText(applicationContext,"통신 실패",Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                    //로그인 성공시 홈으로 이동
                    val body = response.body()
                    Log.d("통신응답", response.body()?.name.toString())
                    if(response.body()?.name!="Fail") {
                        if(response.body()?.name=="no"){
                            Toast.makeText(applicationContext, "해당 이메일은 가입되어 있지 않습니다.", Toast.LENGTH_SHORT).show()
////                            progressDialog.cancel()
                        }else {
                            prefs.setString("email",binding.editEmail.text.toString())
                            prefs.setString("password",binding.editPassword.text.toString())
                            prefs.setString("role",role)
                            prefs.setString("name", body?.name.toString())

                            Toast.makeText(applicationContext, "홈 화면으로 이동합니다", Toast.LENGTH_LONG)
                                .show()
                            startActivity(Intent(applicationContext, MainActivity::class.java))
                        }
                    }else {
                        Toast.makeText(applicationContext, "이메일 및 패스워드가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
////                        progressDialog.cancel()
                    }
                }
            })
        } else {
            Toast.makeText(this, "이메일과 패스워드를 모두 기입해주세요", Toast.LENGTH_SHORT).show()
        }

    }

}