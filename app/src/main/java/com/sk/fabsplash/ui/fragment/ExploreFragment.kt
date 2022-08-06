package com.sk.fabsplash.ui.fragment

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.sk.fabsplash.R
import com.sk.fabsplash.base.BaseFragment
import com.sk.fabsplash.constants.StringConstants
import com.sk.fabsplash.databinding.FragmentExploreBinding
import com.sk.fabsplash.models.photo.PhotoResponseItem
import com.sk.fabsplash.ui.adapter.PhotoListAdapter

class ExploreFragment : BaseFragment() {

    fun newInstance(photo: PhotoResponseItem): ExploreFragment {
        val args = Bundle()
        val fragment = ExploreFragment()
        args.putSerializable(StringConstants.PHOTO, photo)
        fragment.arguments = args
        return fragment
    }

    /**
     * Binding to xml view, this is used to access all the views presents inside xml ui.
     */
    private lateinit var binding: FragmentExploreBinding

    /**
     * Holds Photo.
     */
    private var photo: PhotoResponseItem? = null

    /**
     *List to hold photo response item
     */
    private var list = arrayListOf<PhotoResponseItem>()

    /**
     * Holds adapter object
     */
    private lateinit var photoAdapter: PhotoListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentExploreBinding.bind(view)
        photo = arguments?.getSerializable(StringConstants.PHOTO) as PhotoResponseItem?
        setToolbar()
        setRecyclerView()
        exploreCollectionResponse()
    }

    /**
     * Set toolbar
     */
    private fun setToolbar() {
        binding.tbExplore.title = resources.getString(R.string.explore)
        binding.tbExplore.setNavigationOnClickListener {
            mMainActivityListener.switchFragment(HomeFragment())
        }
    }

    /**
     * Get explore result
     */
    private fun exploreCollectionResponse() {
        photo?.let { list.add(it) }
        unsplashViewModel.getPhotoResponse((1..100).random()) {
            if (it != null) {
                for (photo in it) {
                    list.add(photo)
                }
                list.let { it1 -> photoAdapter.submitPhotos(it1) }
            }
        }
        photoAdapter.submitPhotos(list)
    }

    /**
     * Function to set recycler view.
     */
    @RequiresApi(Build.VERSION_CODES.N)
    private fun setRecyclerView() {
        photoAdapter = PhotoListAdapter()
        binding.rvPhotolist.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = photoAdapter
        }

        photoAdapter.shareClickListener { photo ->
            mMainActivityListener.sendLink(photo.urls.regular)
        }

        photoAdapter.downloadClickListener { photo ->
            try {
                Toast.makeText(context, "Downloading Started!!", Toast.LENGTH_LONG).show()
                binding.pbDownload.visibility = View.VISIBLE
                binding.pbDownload.cancelDragAndDrop()
                Glide.with(requireContext())
                    .asBitmap()
                    .load(photo.urls.full)
                    .into(object : CustomTarget<Bitmap?>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap?>?
                        ) {
                            unsplashViewModel.saveImage(resource, photo) {
                                if (it) {
                                    binding.pbDownload.visibility = View.GONE
                                    Toast.makeText(context, "Image Saved!!", Toast.LENGTH_LONG)
                                        .show()
                                }
                            }
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                        }
                    })
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }

    }

}