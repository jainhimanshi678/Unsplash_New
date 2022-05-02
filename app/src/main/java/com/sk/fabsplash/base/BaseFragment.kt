package com.sk.fabsplash.base

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sk.fabsplash.ui.activity.MainActivity
import com.sk.fabsplash.interfaces.IMainActivity
import com.sk.fabsplash.viewModel.UnsplashViewModel

open class BaseFragment: Fragment() {

    val unsplashViewModel: UnsplashViewModel by viewModels()

    lateinit var mMainActivityListener: IMainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
            mMainActivityListener = context as MainActivity
    }

}