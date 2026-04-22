package com.example.lifeharmonyapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lifeharmonyapp.databinding.FragmentHomeBinding
import com.example.lifeharmonyapp.databinding.ItemWishAddBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Список всех квадратных ячеек (include) в порядке их заполнения
    private lateinit var cells: List<ItemWishAddBinding>

    // Карта для отслеживания занятых ячеек (индекс ячейки -> занята или нет)
    private val filledStatus = mutableMapOf<Int, Boolean>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentHomeBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        // 1. Инициализируем список ячеек из твоего fragment_home.xml
        cells = listOf(
            binding.cell11, binding.cell12,binding.cell13,
            binding.cell21, binding.cell23,
            binding.cell31, binding.cell32, binding.cell33
        )

        // Изначально все ячейки свободны
        if (filledStatus.isEmpty()) {
            cells.indices.forEach { filledStatus[it] = false }
        }

        // 2. Настраиваем сетку, чтобы она была квадратной
        binding.gridContainer.post {
            if (_binding != null) {
                binding.gridContainer.layoutParams.height = binding.gridContainer.width
                binding.gridContainer.requestLayout()
            }
        }

        // 3. Слушаем результат из BottomSheetAddWish (ключ "new_wish")
        parentFragmentManager.setFragmentResultListener("new_wish", viewLifecycleOwner) { _, bundle ->
            val img = bundle.getInt("img")
            val txt = bundle.getString("txt") ?: ""

            // Ищем индекс первой свободной ячейки
            val firstEmptyIndex = cells.indices.find { filledStatus[it] == false }

            firstEmptyIndex?.let { index ->
                val cell = cells[index]

                // Скрываем слой "Добавить" и показываем фото с текстом
                cell.layoutAddState.visibility = View.GONE
                cell.ivWishPhoto.setImageResource(img)
                cell.ivWishPhoto.visibility = View.VISIBLE

                // Если ты добавила слой затемнения viewGradient в item_wish_add.xml:
                // cell.viewGradient.visibility = View.VISIBLE

                cell.tvWishTitle.text = txt
                cell.tvWishTitle.visibility = View.VISIBLE

                // Помечаем ячейку как занятую
                filledStatus[index] = true
            }
        }

        // 4. Настраиваем клики
        setupClickListeners()

        binding.tvProfile.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
    }

    private fun setupClickListeners() {
        // Клик по центральному кругу — только открывает шторку
        binding.cellCenter.setOnClickListener {
            openBottomSheet()
        }

        // Клики по остальным ячейкам — тоже открывают шторку
        cells.forEach { cellBinding ->
            cellBinding.root.setOnClickListener {
                openBottomSheet()
            }
        }
    }

    private fun openBottomSheet() {
        val bottomSheet = BottomSheetAddWish()
        bottomSheet.show(parentFragmentManager, "BottomSheetAddWish")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}