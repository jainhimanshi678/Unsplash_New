package com.sk.unsplash.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sk.unsplash.databinding.MainPhotoItemBinding
import com.sk.unsplash.models.photo.PhotoResponseItem

class PhotoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * Holds recent item Photo list.
     */
    private var recentPhotos: List<PhotoResponseItem> = listOf()

    /**
     * Holds context
     */
    lateinit var context: Context

    /**
     * Set on long press listener
     */
    var photoLongPressListener: ((PhotoResponseItem) -> Unit)? = null

    /**
     * Set on click listener
     */
    var photoOnClickListener: ((PhotoResponseItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return PhotoItemHolder(
            MainPhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PhotoItemHolder).bindData(recentPhotos[position])
    }

    override fun getItemCount(): Int {
        return recentPhotos.size
    }

    /**
     *  Class to bind data
     */
    inner class PhotoItemHolder(private val binding: MainPhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(photo: PhotoResponseItem) {
            Glide.with(context).load(photo.urls.small).into(binding.ivPhoto)
            binding.tvName.text = photo.description
            binding.ivPhoto.setOnLongClickListener {
                photoLongPressListener?.let { onClick ->
                    onClick(photo)
                }
                true
            }
            binding.ivPhoto.setOnClickListener {
                photoOnClickListener?.let { onClick ->
                    onClick(photo)
                }
            }
        }
    }

    /**
     * Submit photos.
     */
    @SuppressLint("NotifyDataSetChanged")
    fun submitPhotos(recentPhotos: List<PhotoResponseItem>) {
        this.recentPhotos = recentPhotos
        notifyDataSetChanged()
    }

    /**
     * Set the photo long press listener
     */
    fun longPressListener(listener: (PhotoResponseItem) -> Unit) {
        photoLongPressListener = listener
    }

    /**
     * Set the photo on click listener
     */
    fun clickListener(listener: (PhotoResponseItem) -> Unit) {
        photoOnClickListener = listener
    }
}
