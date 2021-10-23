package com.paasta.hiclass.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.paasta.hiclass.LoginActivity
import com.paasta.hiclass.R
import com.paasta.hiclass.model.PreferenceUtil

class HomeFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("이메일",LoginActivity.prefs.getString("email",""))
        Log.d("비번",LoginActivity.prefs.getString("password",""))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, null)
        // 처리
        return view
    }
}