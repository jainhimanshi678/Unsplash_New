package com.sk.unsplash.base

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sk.unsplash.ui.Activity.MainActivity
import com.sk.unsplash.interfaces.IMainActivity
import com.sk.unsplash.viewModel.UnsplashViewModel

open class BaseFragment: Fragment() {

    val unsplashViewModel: UnsplashViewModel by viewModels()

    lateinit var mMainActivityListener: IMainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
            mMainActivityListener = context as MainActivity
    }

}