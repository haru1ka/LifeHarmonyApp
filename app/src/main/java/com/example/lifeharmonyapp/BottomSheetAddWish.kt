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
    private var targetCellId: Int = -1

    private val educationImages = listOf(R.drawable.education1, R.drawable.education2,R.drawable.education2,
        R.drawable.education3,R.drawable.education4,R.drawable.education5,R.drawable.education6,R.drawable.education7,
        R.drawable.education8,R.drawable.education9,R.drawable.education10,R.drawable.education11)
    private val travelImages = listOf(R.drawable.travel1, R.drawable.travel2,R.drawable.travel3,R.drawable.travel4,
            R.drawable.travel5,R.drawable.travel6,R.drawable.travel7,R.drawable.travel8,R.drawable.travel9,R.drawable.travel10,
            R.drawable.travel11)

    private val spiritualityImages = listOf(R.drawable.spirituality1,R.drawable.spirituality2,R.drawable.spirituality3,
            R.drawable.spirituality4,R.drawable.spirituality5,R.drawable.spirituality6,R.drawable.spirituality7,
        R.drawable.spirituality8,R.drawable.spirituality9,R.drawable.spirituality10,R.drawable.spirituality11)

    private val careerImages = listOf(R.drawable.career1,R.drawable.career2,R.drawable.career3,
        R.drawable.career4,R.drawable.career5,R.drawable.career7,R.drawable.career8,
        R.drawable.career9,R.drawable.career10,R.drawable.career11)

    private val familyImages = listOf(R.drawable.family1,R.drawable.family2,R.drawable.family3,R.drawable.family4,
        R.drawable.family5,R.drawable.family6,R.drawable.family7,R.drawable.family8,R.drawable.family9,R.drawable.family10,
        R.drawable.family11,)

    private val healthImages = listOf(R.drawable.health1,R.drawable.health2,R.drawable.health3,R.drawable.health4,
        R.drawable.health5,R.drawable.health6,R.drawable.health7,R.drawable.health8,R.drawable.health9,
        R.drawable.health10,R.drawable.health11)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_add_wish, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        targetCellId = arguments?.getInt("cell_id") ?: -1

        btnSave = view.findViewById(R.id.btnSaveWish)
        val etDescription = view.findViewById<EditText>(R.id.etWishDescription)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvImages)
        val btnClose = view.findViewById<View>(R.id.btnClose)

        imageAdapter = WishImageAdapter(educationImages) { selectedImage ->
            selectedImageRes = selectedImage
            btnSave.visibility = View.VISIBLE
        }
        recyclerView.adapter = imageAdapter

        setupSphereClick(view, R.id.sphere1, educationImages)
        setupSphereClick(view, R.id.sphere2, travelImages)
        setupSphereClick(view, R.id.sphere3, spiritualityImages)
        setupSphereClick(view, R.id.sphere4, careerImages)
        setupSphereClick(view, R.id.sphere5, familyImages)
        setupSphereClick(view, R.id.sphere6, healthImages)


        btnClose.setOnClickListener { dismiss() }

        btnSave.setOnClickListener {
            val text = etDescription.text.toString()

            if (selectedImageRes != null && text.isNotEmpty()) {
                val result = Bundle().apply {
                    putInt("img", selectedImageRes!!)
                    putString("txt", text)
                    putInt("target_cell_id", targetCellId)
                }
                parentFragmentManager.setFragmentResult("new_wish", result)
                dismiss()
            }
        }

        setupSphereProgress(view, R.id.arcProgress1, 7500)
    }

    private fun setupSphereClick(root: View, sphereId: Int, images: List<Int>) {
        val sphere = root.findViewById<FrameLayout>(sphereId)
        root.findViewById<FrameLayout>(sphereId).setOnClickListener {
            imageAdapter.updateData(images)
            btnSave.visibility = View.GONE
            resetSpheresAlpha(root)
            sphere.setBackgroundResource(R.drawable.shape_sphere_selected)
            sphere.alpha = 1.0f
            it.alpha = 1.0f
        }
    }

    private fun resetSpheresAlpha(root: View) {
        val ids = listOf(R.id.sphere1, R.id.sphere2, R.id.sphere3, R.id.sphere4, R.id.sphere5, R.id.sphere6)
        ids.forEach { id ->
            val view = root.findViewById<View>(id)
            // Убираем фон (делаем прозрачным) и возвращаем прозрачность 0.5
            view?.background = null

        }
    }

    private fun setupSphereProgress(rootView: View, arcId: Int, progressLevel: Int) {
        val arc = rootView.findViewById<ImageView>(arcId)
        arc?.setImageLevel(progressLevel)
    }
}