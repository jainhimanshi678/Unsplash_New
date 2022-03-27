package com.sk.unsplash.UI.Fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.sk.unsplash.R
import com.sk.unsplash.base.BaseFragment
import com.sk.unsplash.databinding.FragmentHomeBinding
import androidx.appcompat.app.AppCompatActivity




class HomeFragment : BaseFragment() {

    lateinit var binding:FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentHomeBinding.bind(view)
        setToolBar()
    }

    private fun setToolBar(){
        //(activity as AppCompatActivity?)?.setSupportActionBar(binding.toolbar)
    }
}