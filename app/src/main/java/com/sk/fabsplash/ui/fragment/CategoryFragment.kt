package com.sk.fabsplash.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.sk.fabsplash.R
import com.sk.fabsplash.base.BaseFragment
import com.sk.fabsplash.databinding.FragmentCategoryBinding
import com.sk.fabsplash.models.collection.CollectionResultItem
import com.sk.fabsplash.ui.adapter.CollectionAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CategoryFragment : BaseFragment() {

    /**
     * Holds binding
     */
    private lateinit var binding: FragmentCategoryBinding

    /**
     * Holds adapter object
     */
    private lateinit var collectionAdapter: CollectionAdapter

    /**
     *List to hold photo response item
     */
    private var list = arrayListOf<CollectionResultItem>()

    var count = 0

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentCategoryBinding.bind(view)
        setCollectionResponse((1..10).random())
      //  setOnClickListener()
        setSearchView()
        setOnScrollListener()
        setRecyclerView()
    }

    /**
     * Function to set recycler view.
     */
    private fun setRecyclerView() {
        collectionAdapter = CollectionAdapter()
        binding.rvPhoto.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = collectionAdapter
        }
    }

    /**
     * Get and set collections from api.
     */
    private fun setCollectionResponse(count: Int) {
        unsplashViewModel.getCollectionResponse(count) {
            if (it != null) {
                for (collectionResultItem in it) {
                    list.add(collectionResultItem)
                }
                list.let { it1 -> collectionAdapter.submitCollection(it1) }
                binding.pbProgress.visibility = View.GONE
            }
        }
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
                        setCollectionResponse((1..10).random())
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
                        setCollectionResponse((1..10).random())
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
        unsplashViewModel.getSearchCollection(query) {
            binding.pbProgress.visibility = View.GONE
            if (it != null) {
                for (photo in it.results) {
                 //   list.add(photo)
                }
                collectionAdapter.submitCollection(list)
                binding.pbProgress.visibility = View.GONE
            }
        }
    }

    /**
     * Set on scroll listener.
     */
    private fun setOnScrollListener() {
        binding.nsvCollection.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener
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
        if (searchQuery == "") setCollectionResponse(count++)
        else getSearchPhoto(searchQuery, count++)
    }

}