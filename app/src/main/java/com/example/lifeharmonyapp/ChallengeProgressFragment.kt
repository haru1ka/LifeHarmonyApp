package com.example.lifeharmonyapp.data

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lifeharmonyapp.R
import com.example.lifeharmonyapp.databinding.FragmentChallengeProgressBinding

class ChallengeProgressFragment : Fragment(R.layout.fragment_challenge_progress) {

    private var _binding: FragmentChallengeProgressBinding? = null
    private val binding get() = _binding!!

    private val totalDays = 30

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentChallengeProgressBinding.bind(view)

        // 1. Создаем данные (список от 1 до 30)
        val daysList = List(totalDays) { index -> ChallengeDay(index + 1) }

        // 2. Создаем адаптер
        val challengeAdapter = ChallengeDaysAdapter(daysList) { completedCount ->
            // Эта функция вызывается при каждом клике по кружочку
            updateDaysRemaining(completedCount)
        }

        // 3. Настраиваем RecyclerView
        binding.rvChallengeDays.apply {
            // spanCount = 2, так как на макете две колонки (День 1, День 2...)
            layoutManager = GridLayoutManager(context, 4)
            adapter = challengeAdapter
        }

        // Устанавливаем начальное значение счетчика
        updateDaysRemaining(0)
    }

    private fun updateDaysRemaining(completed: Int) {
        val remaining = totalDays - completed
        // Обновляем TextView сверху (как на макете image_a22a23.png)
        binding.tvDaysLeft.text = "Осталось $remaining дней"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}