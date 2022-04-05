package com.sk.unsplash.UI.Fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.sk.unsplash.R
import com.sk.unsplash.base.BaseFragment
import com.sk.unsplash.databinding.FragmentHomeBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sk.unsplash.UI.PhotoAdapter
import com.sk.unsplash.viewModel.UnsplashViewModel
import androidx.recyclerview.widget.StaggeredGridLayoutManager





class HomeFragment : BaseFragment() {

    lateinit var photoAdapter: PhotoAdapter
    lateinit var binding:FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentHomeBinding.bind(view)
        setToolBar()
        setRecyclerView()
        setPhotoResponse()
    }

    private fun setToolBar(){
        //(activity as AppCompatActivity?)?.setSupportActionBar(binding.toolbar)
    }

    private fun setPhotoResponse(){
        unsplashViewModel.getPhotoResponse {
            if(it!=null){
               photoAdapter.submitHints(it)
            }
        }
    }

    private fun setRecyclerView(){
        photoAdapter = PhotoAdapter()
        binding.rvPhoto.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = photoAdapter
        }
        /*val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.rvPhoto.setLayoutManager(staggeredGridLayoutManager)
        binding.rvPhoto.adapter=photoAdapter*/
    }
}