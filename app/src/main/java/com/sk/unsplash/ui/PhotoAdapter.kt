package com.sk.unsplash.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
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
            Glide.with(context).load(photo.urls.regular).into(binding.ivPhoto)

            binding.ivPhoto.setOnLongClickListener {
                Toast.makeText(context, photo.user.id, Toast.LENGTH_LONG).show()
                true
            }
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
