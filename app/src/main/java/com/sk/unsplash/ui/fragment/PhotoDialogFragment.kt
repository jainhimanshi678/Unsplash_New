package com.sk.unsplash.ui.fragment

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.sk.unsplash.R
import com.sk.unsplash.constants.StringConstants
import com.sk.unsplash.databinding.FragmentPhotoDialogBinding
import com.sk.unsplash.interfaces.IMainActivity
import com.sk.unsplash.models.photo.PhotoResponseItem
import com.sk.unsplash.ui.activity.MainActivity
import com.sk.unsplash.viewModel.UnsplashViewModel


class PhotoDialogFragment : DialogFragment() {
    fun newInstance(photo: PhotoResponseItem): PhotoDialogFragment {
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

    /**
     *Holds unsplash view model object.
     */
    val unsplashViewModel: UnsplashViewModel by viewModels()

    /**
     * Holds MainActivity object.
     */
    lateinit var mActivityListener: IMainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivityListener = context as MainActivity
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.WRAP_CONTENT
        )
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
        setupView()
        setOnClickListener()
    }

    /**
     * Sets the views.
     */
    @SuppressLint("SetTextI18n")
    private fun setupView() {
        Glide.with(requireContext()).load(photo?.urls?.regular).into(binding.ivLarge)
        binding.tvLikes.text = photo?.likes.toString() + resources.getString(R.string.likes)
        Glide.with(requireContext()).load(photo?.user?.profile_image?.small).into(binding.ivProfile)
        binding.tvUserName.text = photo?.user?.bio
        binding.tvProfile.text = photo?.user?.name
    }

    private fun setOnClickListener() {
        binding.ivDownload.setOnClickListener {
            saveImage()
        }
        binding.ivSend.setOnClickListener {
            sendLink()
        }
    }

    private fun saveImage() {
        binding.pbDownload.visibility = View.VISIBLE
        binding.tvDownloading.visibility = View.VISIBLE
        binding.rvTop.visibility = View.GONE
        binding.llDetail.visibility = View.GONE
        Glide.with(requireContext())
            .asBitmap()
            .load(photo?.urls?.full ?: photo?.urls?.thumb)
            .into(object : SimpleTarget<Bitmap?>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    unsplashViewModel.saveImage(resource, photo) {
                        if (it) {
                            binding.pbDownload.visibility = View.GONE
                            binding.tvDownloading.visibility = View.GONE
                            binding.rvTop.visibility = View.VISIBLE
                            binding.llDetail.visibility = View.VISIBLE
                            Toast.makeText(context, "Image Saved!!", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            })
    }

    /**
     *Send photo url.
     */
    private fun sendLink() {
        mActivityListener.sendLink(photo?.urls?.regular.toString())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Toast.makeText(context, "Send", Toast.LENGTH_SHORT).show()
    }
}