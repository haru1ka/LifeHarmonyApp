package com.example.lifeharmonyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WishImageAdapter(
    private var images: List<Int>,
    private val onImageSelected: (Int) -> Unit,
    private val onAddPhotoClicked: () -> Unit // Новый колбэк для выбора фото из галереи
) : RecyclerView.Adapter<WishImageAdapter.ViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

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
            // ПЕРВАЯ ЯЧЕЙКА: Кнопка "Своё фото"
            holder.imageView.setImageDrawable(null)
            holder.ivCamera.visibility = View.VISIBLE
            holder.checkIcon.visibility = View.GONE
            holder.tvLabel.visibility = View.VISIBLE

            holder.itemView.setOnClickListener {
                onAddPhotoClicked()
            }
        } else {
            // ОСТАЛЬНЫЕ ЯЧЕЙКИ: Стандартные картинки (индекс минус 1)
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

    fun resetSelection() {
        this.selectedPosition = RecyclerView.NO_POSITION
        notifyDataSetChanged()
    }
}