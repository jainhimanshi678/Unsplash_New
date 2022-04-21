package com.sk.unsplash.ui.fragment

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.sk.unsplash.R
import com.sk.unsplash.constants.StringConstants
import com.sk.unsplash.databinding.FragmentPhotoDialogBinding
import com.sk.unsplash.models.photo.PhotoResponseItem
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*


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
            Glide.with(requireContext())
                .asBitmap()
                .load("YOUR_URL")
                .into(object : SimpleTarget<Bitmap?>(100, 100) {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap?>?
                    ) {
                        saveImage(resource)
                    }
                })
        }

    }

    private fun saveImage(image: Bitmap): String? {
        var savedImagePath: String? = null
        val imageFileName = "JPEG_" + "FILE_NAME" + ".jpg"
        val storageDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .toString() + "/YOUR_FOLDER_NAME"
        )
        var success = true
        if (!storageDir.exists()) {
            success = storageDir.mkdirs()
        }
        if (success) {
            val imageFile = File(storageDir, imageFileName)
            savedImagePath = imageFile.getAbsolutePath()
            try {
                val fOut: OutputStream = FileOutputStream(imageFile)
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
                fOut.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            // Add the image to the system gallery
           // galleryAddPic(savedImagePath)
            Toast.makeText(context, "IMAGE SAVED", Toast.LENGTH_LONG).show()
        }
        return savedImagePath
    }

    private fun galleryAddPic(imagePath: String) {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val f = File(imagePath)
        val contentUri: Uri = Uri.fromFile(f)
        mediaScanIntent.data = contentUri
       // sendBroadcast(mediaScanIntent)
    }
}