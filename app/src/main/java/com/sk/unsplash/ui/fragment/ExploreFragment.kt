package com.sk.unsplash.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sk.unsplash.R
import com.sk.unsplash.base.BaseFragment
import com.sk.unsplash.constants.StringConstants
import com.sk.unsplash.databinding.FragmentExploreBinding
import com.sk.unsplash.models.photo.PhotoResponseItem
import com.sk.unsplash.ui.adapter.PhotoListAdapter

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
        binding.tbExplore.setNavigationOnClickListener{
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
    private fun setRecyclerView() {
        photoAdapter = PhotoListAdapter()
        binding.rvPhotolist.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = photoAdapter
        }
    }


}