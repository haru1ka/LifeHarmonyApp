package com.example.lifeharmonyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class WishImageAdapter(
    private var images: List<Int>,
    private val onImageSelected: (Int) -> Unit
) : RecyclerView.Adapter<WishImageAdapter.ViewHolder>() {

    // Храним позицию выбранного элемента
    private var selectedPosition = RecyclerView.NO_POSITION

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.ivWishImage)
        val checkIcon: ImageView = view.findViewById(R.id.ivChecked)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_wish_image, parent, false)
        return ViewHolder(view)
    }

    // Тот самый метод, на который ругается компилятор.
    // Убедись, что он в файле только один!
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageRes = images[position]
        holder.imageView.setImageResource(imageRes)

        // Логика отображения галочки
        if (selectedPosition == position) {
            holder.checkIcon.visibility = View.VISIBLE
        } else {
            holder.checkIcon.visibility = View.GONE
        }

        // Обработка клика
        holder.itemView.setOnClickListener {
            val previousPosition = selectedPosition
            selectedPosition = holder.adapterPosition

            // Перерисовываем предыдущий выбранный элемент и новый
            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)

            // Вызываем колбэк, чтобы показать кнопку "Сохранить" во фрагменте
            onImageSelected(imageRes)
        }
    }

    override fun getItemCount(): Int = images.size

    fun updateData(newImages: List<Int>) {
        this.images = newImages
        this.selectedPosition = RecyclerView.NO_POSITION // Сбрасываем выбор при смене категории
        notifyDataSetChanged()
    }
}