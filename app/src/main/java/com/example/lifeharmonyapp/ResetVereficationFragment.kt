package com.example.lifeharmonyapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lifeharmonyapp.databinding.FragmentResetVerificationBinding

class ResetVereficationFragment : Fragment(R.layout.fragment_reset_verification) {
    private var _binding: FragmentResetVerificationBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentResetVerificationBinding.bind(view)

        // 1. Получаем email из предыдущего фрагмента
        val userEmail = arguments?.getString("user_email")

        setupVerificationLogic()

        binding.btnNextReset.setOnClickListener {
            // 2. Передаем этот же email в следующий фрагмент (где будет ввод нового пароля)
            val bundle = Bundle().apply {
                putString("user_email", userEmail)
            }
            findNavController().navigate(
                R.id.action_resetVereficationFragment_to_newPasswordFragment,
                bundle
            )
        }

        binding.tvGoToRecovery.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupVerificationLogic() {
        // Изначально выключаем кнопку
        binding.btnNextReset.isEnabled = false
        binding.btnNextReset.alpha = 0.5f

        binding.etRecoveryCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val code = s.toString().trim()
                val isFullCode = code.length == 6

                binding.btnNextReset.isEnabled = isFullCode
                binding.btnNextReset.alpha = if (isFullCode) 1.0f else 0.5f
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}