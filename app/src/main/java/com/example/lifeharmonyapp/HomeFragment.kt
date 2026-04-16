package com.example.lifeharmonyapp

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.gridlayout.widget.GridLayout
import com.example.lifeharmonyapp.databinding.FragmentHomeBinding
import com.example.lifeharmonyapp.databinding.FragmentLoginBinding

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var gridLayout: GridLayout
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentHomeBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        val gridContainer = binding.gridContainer // Используй уже инициализированный binding

        gridContainer.post {
            if (_binding != null) {
                val width = gridContainer.width
                if (width > 0) {
                    gridContainer.layoutParams.height = width
                    gridContainer.requestLayout()
                }
            }
        }

        // Для сетки по клику СДЕЛАТЬ ЦИКЛ!!!!!
        binding.cell13.setOnClickListener {
            val bottomSheet = BottomSheetAddWish()
            bottomSheet.show(parentFragmentManager, "BottomSheetAddWish")
        }
        binding.cell11.root.setOnClickListener {
            val bottomSheet = BottomSheetAddWish()
            bottomSheet.show(parentFragmentManager, "BottomSheetAddWish")
        }
        binding.cell12.root.setOnClickListener {
            val bottomSheet = BottomSheetAddWish()
            bottomSheet.show(parentFragmentManager, "BottomSheetAddWish")
        }
        binding.cell21.root.setOnClickListener {
            val bottomSheet = BottomSheetAddWish()
            bottomSheet.show(parentFragmentManager, "BottomSheetAddWish")
        }
        binding.cell23.root.setOnClickListener {
            val bottomSheet = BottomSheetAddWish()
            bottomSheet.show(parentFragmentManager, "BottomSheetAddWish")
        }
        binding.cell31.root.setOnClickListener {
            val bottomSheet = BottomSheetAddWish()
            bottomSheet.show(parentFragmentManager, "BottomSheetAddWish")
        }
        binding.cell32.root.setOnClickListener {
            val bottomSheet = BottomSheetAddWish()
            bottomSheet.show(parentFragmentManager, "BottomSheetAddWish")
        }
        binding.cell33.root.setOnClickListener {
            val bottomSheet = BottomSheetAddWish()
            bottomSheet.show(parentFragmentManager, "BottomSheetAddWish")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
