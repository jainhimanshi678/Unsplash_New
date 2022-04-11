package com.sk.unsplash.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import com.sk.unsplash.R
import com.sk.unsplash.base.BaseFragment
import com.sk.unsplash.databinding.FragmentHomeBinding
import androidx.recyclerview.widget.GridLayoutManager
import com.sk.unsplash.ui.adapter.PhotoAdapter


class HomeFragment : BaseFragment() {

    /**
     * Holds adapter object
     */
    private lateinit var photoAdapter: PhotoAdapter

    /**
     * Holds binding
     */
    private lateinit var binding: FragmentHomeBinding


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
        setRecyclerView()
        setPhotoResponse()
        setOnClickListener()
        setSearchView()
    }

    /**
     * Set on click listener
     */
    private fun setOnClickListener() {
        photoAdapter.longPressListener {
            mMainActivityListener.setPhotoLongPressListener(it)
        }
    }

    private fun setToolBar() {
        //(activity as AppCompatActivity?)?.setSupportActionBar(binding.toolbar)
    }

    /**
     * Get and set photos from api
     */
    private fun setPhotoResponse() {
        unsplashViewModel.getPhotoResponse {
            if (it != null) {
                photoAdapter.submitHints(it)
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
     *Get search item
     */
    private fun setSearchView() {
        binding.svPhoto.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null && p0 != "") {
                    getSearchPhoto(p0)
                } else {
                    setPhotoResponse()
                }
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null && p0 != "") {
                    getSearchPhoto(p0)
                } else {
                    setPhotoResponse()
                }
                return false
            }
        })
    }

    /**
     * Get search item response
     */
    private fun getSearchPhoto(query: String) {
        unsplashViewModel.getSearchResponseForPhoto(query) {
            if (it != null) {
                photoAdapter.submitHints(it.results)
                binding.pbProgress.visibility = View.GONE
            }
        }
    }
}
