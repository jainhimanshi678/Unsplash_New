package com.sk.unsplash.UI

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sk.unsplash.databinding.MainPhotoItemBinding

class PhotoAdapter :RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PhotoItemHolder(
            MainPhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PhotoItemHolder).bindData(recentHints[position] as FoodHint)
    }

    override fun getItemCount(): Int {
        return recentHints.size
    }

    inner class PhotoItemHolder(private val binding: MainPhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(hints: FoodHint) {
            binding.tvFoodTitle.text = hints.food.label

        }
        }

    }