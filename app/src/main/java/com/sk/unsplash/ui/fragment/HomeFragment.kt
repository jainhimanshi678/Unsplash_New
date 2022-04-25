package com.sk.unsplash.ui.fragment

import android.os.Bundle
import android.os.Handler
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

    var searchCount = 0

    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
    }

    /**
     * Set toolbar.
     */
    private fun setToolBar() {
        //(activity as AppCompatActivity?)?.setSupportActionBar(binding.toolbar)
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
                list.let { it1 -> photoAdapter.submitHints(it1) }
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
        { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            // on scroll change we are checking when users scroll as bottom.
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                // in this method we are incrementing page number,
                // making progress bar visible and calling get data method.
                count++
                // on below line we are making our progress bar visible.
                binding.pbProgress.setVisibility(View.VISIBLE)
                if (count < 20) {
                    // on below line we are again calling
                    // a method to load data in our array list.
                    loadMore()
                }
            }
        })
    }

    /**
     *Call api for more results
     */
    private fun loadMore() {
        setPhotoResponse(count++)
    }

    /**
     *Get search item
     */
    private fun setSearchView() {
        binding.svPhoto.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                searchJob?.cancel()
                searchJob = GlobalScope.launch {
                    delay(300)
                    if (p0 != null && p0 != "") {
                        list.clear()
                        getSearchPhoto(p0, count++)
                    } else {
                        list.clear()
                        setPhotoResponse((1..10).random())
                    }
                }
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                searchJob?.cancel()
                searchJob = GlobalScope.launch {
                    delay(300)
                    if (p0 != null && p0 != "") {
                        list.clear()
                        binding.pbProgress.visibility = View.VISIBLE
                        getSearchPhoto(p0, count++)
                    } else {
                        list.clear()
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
        unsplashViewModel.getSearchResponseForPhoto(query, count) {
            binding.pbProgress.visibility = View.GONE
            if (it != null) {
                for (photo in it.results) {
                    list.add(photo)
                }
                photoAdapter.submitHints(list)
                binding.pbProgress.visibility = View.GONE
            }
        }
    }
}
