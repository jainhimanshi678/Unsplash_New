package com.sk.unsplash.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sk.unsplash.UI.PhotoAdapter
import com.sk.unsplash.viewModel.UnsplashViewModel

open class BaseFragment: Fragment() {

    val unsplashViewModel: UnsplashViewModel by viewModels()



}