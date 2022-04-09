package com.sk.unsplash.ui.fragment

import android.app.ActionBar
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.sk.unsplash.R
import com.sk.unsplash.constants.StringConstants
import com.sk.unsplash.databinding.FragmentPhotoDialogBinding
import com.sk.unsplash.models.photo.PhotoResponseItem

class PhotoDialogFragment : DialogFragment() {
    fun newInstance(photo:PhotoResponseItem): PhotoDialogFragment {
        val filterDialogFragment = PhotoDialogFragment()
        val bundle = Bundle()
        bundle.putSerializable(StringConstants.PHOTO, photo)
        filterDialogFragment.arguments = bundle
        return filterDialogFragment
    }

    /**
     * Binding to xml view, this is used to access all the views presents inside xml ui.
     */
    private lateinit var binding: FragmentPhotoDialogBinding

    /**
     * Holds Photo.
     */
    private var photo: PhotoResponseItem? = null

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.WRAP_CONTENT
        );
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPhotoDialogBinding.bind(view)
        photo = arguments?.getSerializable(StringConstants.PHOTO) as PhotoResponseItem?
        dialog?.setCancelable(true)
        Log.d("imgitem", "onViewCreated: $photo")
        setupView(view)
    }

    /**
     * Sets the views.
     */
    private fun setupView(view: View) {
            Glide.with(requireContext()).load(photo?.urls?.regular?:resources.getColor(R.color.teal_200)).into(binding.ivLarge)
    }


}