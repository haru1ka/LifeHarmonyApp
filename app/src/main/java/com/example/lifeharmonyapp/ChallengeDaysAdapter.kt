package com.example.lifeharmonyapp.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lifeharmonyapp.databinding.ItemChallengeDayBinding

// Модель данных для одного дня
data class ChallengeDay(
    val number: Int,
    var isSelected: Boolean = false
)

class ChallengeDaysAdapter(
    private val days: List<ChallengeDay>,
    private val onDayChanged: (Int) -> Unit // Ламбда для обновления счетчика во фрагменте
) : RecyclerView.Adapter<ChallengeDaysAdapter.DayViewHolder>() {

    inner class DayViewHolder(val binding: ItemChallengeDayBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val binding = ItemChallengeDayBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val day = days[position]

        with(holder.binding) {
            tvDayNumber.text = "День ${day.number}"

            // Управляем закрашиванием кружочка через состояние selected
            ivCheckCircle.isSelected = day.isSelected

            root.setOnClickListener {
                // Переключаем состояние
                day.isSelected = !day.isSelected
                ivCheckCircle.isSelected = day.isSelected

                // Считаем сколько всего отмечено и передаем во фрагмент
                val completedCount = days.count { it.isSelected }
                onDayChanged(completedCount)
            }
        }
    }

    override fun getItemCount(): Int = days.size
}