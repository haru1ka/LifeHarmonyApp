package com.example.lifeharmonyapp.data

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.lifeharmonyapp.R
import com.example.lifeharmonyapp.databinding.BottomSheetChallengeBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetChallenge : BottomSheetDialogFragment() {
    private var _binding: BottomSheetChallengeBinding? = null
    private val binding get() = _binding!!

    // Устанавливаем стиль с прозрачным фоном, чтобы работали закругления
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
        // Если у тебя нет готового стиля, можно использовать дефолтный
        // или создать в themes.xml с "android:navigationBarColor"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = BottomSheetChallengeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnClose.setOnClickListener { dismiss() }

        binding.btnStart.setOnClickListener {
            findNavController().navigate(R.id.action_global_challengeProgressFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}