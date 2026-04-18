package com.example.lifeharmonyapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.lifeharmonyapp.data.AppDatabase
import com.example.lifeharmonyapp.databinding.FragmentVerificationBinding
import kotlinx.coroutines.launch

class VerificationFragment : Fragment(R.layout.fragment_verification) {
    private var _binding: FragmentVerificationBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentVerificationBinding.bind(view)

        // 1. ПОЛУЧАЕМ EMAIL ИЗ БАНДЛА
        val userEmail = arguments?.getString("user_email") ?: ""

        setupVerificationLogic()

        // 2. ОБРАБОТКА НАЖАТИЯ С ЗАПИСЬЮ В БАЗУ
        binding.btnAcceptCode.setOnClickListener {
            if (userEmail.isNotEmpty()) {
                viewLifecycleOwner.lifecycleScope.launch {
                    val db = AppDatabase.getDatabase(requireContext())

                    // Обновляем статус в базе данных
                    db.userDao().verifyUser(userEmail)
                    // НУЖНО ПОДОГНАТЬ ПОД ДИЗАЙНУ
                    Toast.makeText(requireContext(), "Аккаунт подтвержден!", Toast.LENGTH_SHORT).show()

                    // Теперь можно переходить на логин
                    findNavController().navigate(R.id.action_verification_to_login)
                }
            } else {
                // Если email почему-то не долетел
                Toast.makeText(requireContext(), "Ошибка: данные пользователя не найдены", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupVerificationLogic() {
        // Изначально кнопка выключена
        binding.btnAcceptCode.isEnabled = false
        binding.btnAcceptCode.alpha = 0.5f

        binding.etVerificationCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val code = s.toString().trim()
                val isFullCode = code.length == 6

                binding.btnAcceptCode.isEnabled = isFullCode
                binding.btnAcceptCode.alpha = if (isFullCode) 1.0f else 0.5f
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}