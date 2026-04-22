package com.example.lifeharmonyapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetAddWish : BottomSheetDialogFragment() {

    private lateinit var imageAdapter: WishImageAdapter
    private lateinit var btnSave: View
    private var selectedImageRes: Int? = null
    private val educationImages = listOf(R.drawable.book1, R.drawable.book2)
    private val travelImages = listOf(R.drawable.plane1, R.drawable.plane2)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_add_wish, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация UI элементов
        btnSave = view.findViewById(R.id.btnSaveWish)
        val etDescription = view.findViewById<EditText>(R.id.etWishDescription)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvImages)
        val btnClose = view.findViewById<View>(R.id.btnClose)

        //Настройка адаптера
        imageAdapter = WishImageAdapter(educationImages) { selectedImage ->
            selectedImageRes = selectedImage
            btnSave.visibility = View.VISIBLE
        }
        recyclerView.adapter = imageAdapter


        setupSphereClick(view, R.id.sphere1, educationImages)
        setupSphereClick(view, R.id.sphere2, travelImages)

        //Кнопки управления
        btnClose.setOnClickListener {
            val text = etDescription.text.toString()
            val cellId = arguments?.getString("cellId")

            if (selectedImageRes != null && text.isNotEmpty()) {
                val result = Bundle().apply {
                    putInt("bundle_image", selectedImageRes!!)
                    putString("bundle_text", text)
                    putString("bundle_cell_id", cellId)
                }
                parentFragmentManager.setFragmentResult("wish_added", result)
                dismiss()
            }
        }

        btnSave.setOnClickListener {
            val text = etDescription.text.toString()

            if (selectedImageRes != null && text.isNotEmpty()) {
                val result = Bundle().apply {
                    putInt("img", selectedImageRes!!) // Ключ "img"
                    putString("txt", text)            // Ключ "txt"
                }
                // КЛЮЧ ДОЛЖЕН БЫТЬ "new_wish"
                parentFragmentManager.setFragmentResult("new_wish", result)
                dismiss()
            }
        }

        // Установка начальных уровней прогресса
        setupSphereProgress(view, R.id.arcProgress1, 7500)
    }

    private fun setupSphereClick(root: View, sphereId: Int, images: List<Int>) {
        root.findViewById<FrameLayout>(sphereId).setOnClickListener {
            imageAdapter.updateData(images)
            btnSave.visibility = View.GONE // Скрываем кнопку, так как в новой категории ничего не выбрано
            resetSpheresAlpha(root)
            it.alpha = 1.0f // Делаем активную сферу яркой
        }
    }

    private fun resetSpheresAlpha(root: View) {
        val ids = listOf(R.id.sphere1, R.id.sphere2, R.id.sphere3, R.id.sphere4, R.id.sphere5, R.id.sphere6)
        ids.forEach { id ->
            root.findViewById<View>(id)?.alpha = 0.5f
        }
    }

    private fun setupSphereProgress(rootView: View, arcId: Int, progressLevel: Int) {
        val arc = rootView.findViewById<ImageView>(arcId)
        arc?.setImageLevel(progressLevel)
    }
}