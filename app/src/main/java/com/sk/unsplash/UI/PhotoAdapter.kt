package com.sk.unsplash.UI

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.sk.unsplash.databinding.MainPhotoItemBinding
import com.sk.unsplash.models.photo.PhotoResponseItem

class PhotoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * Holds recent item Photo list.
     */
    var recentPhotos: List<PhotoResponseItem> = listOf()
    lateinit var requestManager: RequestManager
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context=parent.context
        return PhotoItemHolder(
            MainPhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PhotoItemHolder).bindData(recentPhotos[position] as PhotoResponseItem)
    }

    override fun getItemCount(): Int {
        return recentPhotos.size
    }


    inner class PhotoItemHolder(private val binding: MainPhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(photo: PhotoResponseItem) {
            Glide.with(context).load(photo.urls.regular).into(binding.ivPhoto)
        }
    }

    /**
     * Submit photos.
     */
    fun submitHints(recentPhotos: List<PhotoResponseItem>) {
        this.recentPhotos = recentPhotos
        notifyDataSetChanged()
    }
}
