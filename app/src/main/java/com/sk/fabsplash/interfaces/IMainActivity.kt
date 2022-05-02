package com.sk.fabsplash.interfaces

import androidx.fragment.app.Fragment
import com.sk.fabsplash.models.photo.PhotoResponseItem

interface IMainActivity {
    /***
     * Set long press listener for homefragment
     */
    fun setPhotoLongPressListener(photo: PhotoResponseItem)

    /***
     * Set long press listener for homefragment
     */
    fun setPhotoOnClickListener(photo: PhotoResponseItem)

    /**
     * Send photo url
     */
    fun sendLink(photo: String)

    /**
     * Switch fragment
     */
    fun switchFragment(fragment: Fragment)
}