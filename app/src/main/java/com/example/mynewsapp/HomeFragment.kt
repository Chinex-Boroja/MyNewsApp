package com.example.mynewsapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {


    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var viewHomeFragment: View = inflater.inflate(R.layout.home_fragment, null)
        //return super.onCreateView(inflater, container, savedInstanceState)
        return viewHomeFragment
    }
}