package com.paasta.hiclass.fragment

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentTransaction
import com.paasta.hiclass.CustomDialog
import com.paasta.hiclass.LoginActivity
import com.paasta.hiclass.R
import com.paasta.hiclass.RoomCamera


class HomeFragment : Fragment() {

    private var name : String =""
    private var email : String =""

    private lateinit var enterClass: Button
//    private lateinit var manageQuiz: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, null)
        // 처리
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        enterClass = view.findViewById(R.id.btn_enter)
//        manageQuiz = view.findViewById(R.id.btn_manage_quiz)

//        name = LoginActivity.prefs.getString("name","")
//        email = LoginActivity.prefs.getString("email","")


        addEventListener()

    }

    private fun addEventListener() {
        enterClass.setOnClickListener{
            val customDialog = CustomDialog()
            getActivity()?.getSupportFragmentManager()?.let { it1 -> customDialog.show(it1,"tag") }

//            val intent = Intent(getActivity(), RoomCamera::class.java)
//            startActivity(intent)
        }
    }

}