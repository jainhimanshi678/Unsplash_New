package com.sk.fabsplash.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sk.fabsplash.databinding.CollectionItemBinding
import com.sk.fabsplash.models.collection.CollectionResultItem

class CollectionAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * Holds context
     */
    lateinit var context: Context

    /**
     * Holds recent item Photo list.
     */
    private var recentCollection: List<CollectionResultItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return CollectionItemHolder(
            CollectionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CollectionAdapter.CollectionItemHolder).bindData(recentCollection[position])
    }

    override fun getItemCount(): Int {
        return recentCollection.size
    }

    /**
     *  Class to bind data
     */
    inner class CollectionItemHolder(private val binding: CollectionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(photo: CollectionResultItem) {
            Glide.with(context).load(photo.cover_photo.urls.regular).into(binding.ivLarge)
            binding.tvCollectionName.text = photo.title
            binding.tvCuratedBy.text = photo.user.name
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
        }
    }

    /**
     * Submit collection.
     */
    @SuppressLint("NotifyDataSetChanged")
    fun submitCollection(recentCollection: List<CollectionResultItem>) {
        this.recentCollection = recentCollection
        notifyDataSetChanged()
    }
}