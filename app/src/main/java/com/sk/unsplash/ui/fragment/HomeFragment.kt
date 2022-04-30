package com.sk.unsplash.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import com.sk.unsplash.R
import com.sk.unsplash.base.BaseFragment
import com.sk.unsplash.databinding.FragmentHomeBinding
import com.sk.unsplash.models.photo.PhotoResponseItem
import com.sk.unsplash.ui.adapter.PhotoAdapter
import kotlinx.coroutines.*


class HomeFragment : BaseFragment() {

    /**
     * Holds adapter object
     */
    private lateinit var photoAdapter: PhotoAdapter

    /**
     * Holds binding
     */
    private lateinit var binding: FragmentHomeBinding

    var count = 0

    /**
     *List to hold photo response item
     */
    private var list = arrayListOf<PhotoResponseItem>()

    /**
     * Holds search query.
     */
    var searchQuery = ""

    private var searchJob: Job? = null

    private var isFirstTime = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        setToolBar()
        setPhotoResponse((1..10).random())
        setRecyclerView()
        setOnClickListener()
        setSearchView()
        setOnScrollListener()
    }

    /**
     * Set on click listener.
     */
    private fun setOnClickListener() {
        photoAdapter.longPressListener {
            mMainActivityListener.setPhotoLongPressListener(it)
        }

        photoAdapter.clickListener {
            mMainActivityListener.setPhotoOnClickListener(it)
        }
    }

    /**
     * Set toolbar.
     */
    private fun setToolBar() {
    }

    /**
     * Get and set photos from api.
     */
    private fun setPhotoResponse(count: Int) {
        unsplashViewModel.getPhotoResponse(count) {
            if (it != null) {
                for (photo in it) {
                    list.add(photo)
                }
                list.let { it1 -> photoAdapter.submitPhotos(it1) }
                binding.pbProgress.visibility = View.GONE
            }
        }
    }

    /**
     * Function to set recycler view.
     */
    private fun setRecyclerView() {
        photoAdapter = PhotoAdapter()
        binding.rvPhoto.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = photoAdapter
        }
    }

    /**
     * Set on scroll listener.
     */
    private fun setOnScrollListener() {
        binding.nsvHome.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener
        { v, _, scrollY, _, _ ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                count++
                binding.pbProgress.visibility = View.VISIBLE
                if (count < 20) {
                    isFirstTime = false
                    loadMore()
                }
            }
        })
    }

    /**
     *Call api for more results
     */
    private fun loadMore() {
        if (searchQuery == "") setPhotoResponse(count++)
        else getSearchPhoto(searchQuery, count++)
    }

    /**
     *Get search item
     */
    private fun setSearchView() {
        binding.svPhoto.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                count = 0
                searchQuery = p0 ?: ""
                searchJob?.cancel()
                isFirstTime = true
                searchJob = GlobalScope.launch {
                    list.clear()
                    delay(300)
                    if (p0 != null && p0 != "") {
                        getSearchPhoto(p0, count++)
                    } else {
                        setPhotoResponse((1..10).random())
                    }
                }
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                binding.pbProgress.visibility = View.VISIBLE
                searchQuery = p0 ?: ""
                count = 0
                searchJob?.cancel()
                isFirstTime = true
                searchJob = GlobalScope.launch {
                    list.clear()
                    delay(300)
                    if (p0 != null && p0 != "") {
                        getSearchPhoto(p0, count++)
                    } else {
                        setPhotoResponse((1..10).random())
                    }
                }
                return false
            }
        })
    }

    /**
     * Get search item response
     */
    private fun getSearchPhoto(query: String, count: Int) {
        if (isFirstTime) {
            list.clear()
        }
        unsplashViewModel.getSearchResponseForPhoto(query, count) {
            binding.pbProgress.visibility = View.GONE
            if (it != null) {
                for (photo in it.results) {
                    list.add(photo)
                }
                photoAdapter.submitPhotos(list)
                binding.pbProgress.visibility = View.GONE
            }
        }
    }
}
