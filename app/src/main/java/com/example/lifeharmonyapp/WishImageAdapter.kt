package com.example.lifeharmonyapp

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WishImageAdapter(
    private var images: List<Int>,
    private val onImageSelected: (Int) -> Unit,
    private val onAddPhotoClicked: () -> Unit
) : RecyclerView.Adapter<WishImageAdapter.ViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION
    private var customImageUri: String? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.ivWishImage)
        val checkIcon: ImageView = view.findViewById(R.id.ivChecked)
        val tvLabel: TextView = view.findViewById(R.id.tvPhotoLabel)
        val ivCamera: ImageView = view.findViewById(R.id.ivCamera)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_wish_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 0) {
            if (customImageUri != null) {
                holder.imageView.setImageURI(Uri.parse(customImageUri))
                holder.ivCamera.visibility = View.GONE
                holder.tvLabel.visibility = View.GONE
            } else {
                holder.imageView.setImageDrawable(null)
                holder.ivCamera.visibility = View.VISIBLE
                holder.tvLabel.visibility = View.VISIBLE
            }

            holder.checkIcon.visibility = if (selectedPosition == position) View.VISIBLE else View.GONE

            holder.itemView.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = 0
                notifyItemChanged(previousPosition)
                notifyItemChanged(0)
                onAddPhotoClicked()
            }
        } else {
            val imageRes = images[position - 1]
            holder.imageView.setImageResource(imageRes)
            holder.ivCamera.visibility = View.GONE
            holder.tvLabel.visibility = View.GONE
            holder.checkIcon.visibility = if (selectedPosition == position) View.VISIBLE else View.GONE

            holder.itemView.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = holder.adapterPosition
                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)

                customImageUri = null
                notifyItemChanged(0)
                onImageSelected(imageRes)
            }
        }
    }

    override fun getItemCount(): Int = images.size + 1

    fun updateData(newImages: List<Int>) {
        this.images = newImages
        this.selectedPosition = RecyclerView.NO_POSITION
        notifyDataSetChanged()
    }

    fun setCustomImage(uri: String) {
        this.customImageUri = uri
        this.selectedPosition = 0
        notifyDataSetChanged()
    }

    fun resetCustomImage() {
        this.customImageUri = null
        this.selectedPosition = RecyclerView.NO_POSITION
        notifyDataSetChanged()
    }
}