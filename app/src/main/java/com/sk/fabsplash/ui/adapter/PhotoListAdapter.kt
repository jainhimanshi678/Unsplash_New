package com.sk.fabsplash.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sk.fabsplash.R
import com.sk.fabsplash.databinding.ExploreListItemBinding
import com.sk.fabsplash.models.photo.PhotoResponseItem

class PhotoListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
    private var photoLongPressListener: ((PhotoResponseItem) -> Unit)? = null

    /**
     * Set on click listener
     */
    private var downloadOnClickListener: ((PhotoResponseItem) -> Unit)? = null

    /**
     * Set on click listener
     */
    private var shareOnClickListener: ((PhotoResponseItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return PhotoItemHolder(
            ExploreListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
    inner class PhotoItemHolder(private val binding: ExploreListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindData(photo: PhotoResponseItem) {
            Glide.with(context).load(photo.urls.regular).into(binding.ivLarge)
            Glide.with(context).load(photo.user.profile_image.small)
                .placeholder(R.drawable.ic_baseline_person_24).into(binding.ivProfile)
            binding.tvProfile.text = photo.user.name
            if (photo.user.bio != null) {
                binding.tvUserName.text = photo.user.bio
                binding.tvUserName.visibility = View.VISIBLE
            }
            binding.tvLikes.text =
                photo.likes.toString() + context.resources.getString(R.string.likes)
            /*binding.ivPhoto.setOnLongClickListener {
                photoLongPressListener?.let { onClick ->
                    onClick(photo)
                }
                true
            }
            binding.ivPhoto.setOnClickListener {
                photoOnClickListener?.let { onClick ->
                    onClick(photo)
                }
            }*/

            binding.ivDownload.setOnClickListener {
                downloadOnClickListener?.let { onClick ->
                    onClick(photo)
                }
            }

            binding.ivSend.setOnClickListener {
                shareOnClickListener?.let { onClick ->
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
     * Set the download on click listener
     */
    fun downloadClickListener(listener: (PhotoResponseItem) -> Unit) {
        downloadOnClickListener = listener
    }

    /**
     * Set the share on click listener
     */
    fun shareClickListener(listener: (PhotoResponseItem) -> Unit) {
        shareOnClickListener = listener
    }
}
