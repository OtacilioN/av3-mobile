package com.otaciliomaia.mobileav3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ImagesAdapter(private val imageList: List<ImageItem>): RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder>(){

    inner class ImagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textView: TextView = itemView.findViewById(R.id.imageName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return ImagesViewHolder(itemView)
    }

    override fun getItemCount() = imageList.size

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        val currentItem = imageList[position]
        holder.textView.text = currentItem.name
        holder.imageView.setImageURI(currentItem.image)
    }
}