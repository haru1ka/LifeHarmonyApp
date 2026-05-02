package com.example.lifeharmonyapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lifeharmonyapp.data.BottomSheetChallenge
import com.example.lifeharmonyapp.databinding.FragmentHomeBinding
import com.example.lifeharmonyapp.databinding.ItemWishAddBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Карта для быстрого доступа к ячейкам по их ID
    private lateinit var cellsMap: Map<Int, ItemWishAddBinding>

    // Список ячеек для поиска "первой свободной"
    private lateinit var cellsList: List<ItemWishAddBinding>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentHomeBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        // Инициализируем ячейки
        cellsMap = mapOf(
            R.id.cell_1_1 to binding.cell11,
            R.id.cell_1_2 to binding.cell12,
            R.id.cell_1_3 to binding.cell13,
            R.id.cell_2_1 to binding.cell21,
            R.id.cell_2_3 to binding.cell23,
            R.id.cell_3_1 to binding.cell31,
            R.id.cell_3_2 to binding.cell32,
            R.id.cell_3_3 to binding.cell33
        )

        // Список в порядке заполнения (для логики круга)
        cellsList = listOf(
            binding.cell11, binding.cell12, binding.cell13,
            binding.cell21, binding.cell23,
            binding.cell31, binding.cell32, binding.cell33
        )

        binding.gridContainer.post {
            if (_binding != null) {
                binding.gridContainer.layoutParams.height = binding.gridContainer.width
                binding.gridContainer.requestLayout()
            }
        }

        parentFragmentManager.setFragmentResultListener("new_wish", viewLifecycleOwner) { _, bundle ->
            val img = bundle.getInt("img")
            val txt = bundle.getString("txt") ?: ""
            val targetCellId = bundle.getInt("target_cell_id")

            if (targetCellId == R.id.cell_center) {
                // ЛОГИКА ДЛЯ КРУГА: Ищем первую ячейку, где текст еще не установлен (скрыт слой AddState)
                val firstEmptyCell = cellsList.find { it.layoutAddState.visibility == View.VISIBLE }
                firstEmptyCell?.let { updateCellUI(it, img, txt,bundle) }
            } else {
                // ЛОГИКА ДЛЯ КОНКРЕТНОЙ ЯЧЕЙКИ: Обновляем по ID
                cellsMap[targetCellId]?.let { updateCellUI(it, img, txt,bundle) }
            }
        }

        setupClickListeners()

        binding.tvProfile.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }

        binding.challange.setOnClickListener {
            BottomSheetChallenge().show(parentFragmentManager, "ChallengeBottomSheet")
        }
    }

    private fun updateCellUI(cell: ItemWishAddBinding, img: Int, txt: String, bundle: Bundle) {
        val customUri = bundle.getString("custom_uri")

        cell.layoutAddState.visibility = View.GONE
        cell.tvWishTitle.text = txt
        cell.tvWishTitle.visibility = View.VISIBLE
        cell.ivWishPhoto.visibility = View.VISIBLE

        if (!customUri.isNullOrEmpty()) {
            // Если пришло фото из галереи
            cell.ivWishPhoto.setImageURI(android.net.Uri.parse(customUri))
        } else {
            // Если пришла картинка из ресурсов
            cell.ivWishPhoto.setImageResource(img)
        }
    }

    private fun setupClickListeners() {
        // Клик по кругу
        binding.cellCenter.setOnClickListener {
            openBottomSheet(R.id.cell_center)
        }

        // Клики по ячейкам
        cellsMap.forEach { (id, cellBinding) ->
            cellBinding.root.setOnClickListener {
                openBottomSheet(id)
            }
        }
    }

    private fun openBottomSheet(cellId: Int) {
        val bottomSheet = BottomSheetAddWish()
        bottomSheet.arguments = Bundle().apply {
            putInt("cell_id", cellId)
        }
        bottomSheet.show(parentFragmentManager, "BottomSheetAddWish")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}