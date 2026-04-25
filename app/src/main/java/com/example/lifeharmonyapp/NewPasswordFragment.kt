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
import com.example.lifeharmonyapp.databinding.FragmentNewPasswordBinding
import kotlinx.coroutines.launch

class NewPasswordFragment: Fragment(R.layout.fragment_new_password){
    private var _binding: FragmentNewPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewPasswordBinding.bind(view)

        // 1. Получаем email из аргументов
        val userEmail = arguments?.getString("user_email")

        setupPasswordValidation()

        binding.btnResetPassword.setOnClickListener {
            val newPassword = binding.etRecoveryPassword.text.toString()

            if (userEmail != null) {
                // 2. Обновляем пароль в БД через корутину
                viewLifecycleOwner.lifecycleScope.launch {
                    val db = AppDatabase.getDatabase(requireContext())
                    db.userDao().updatePassword(userEmail, newPassword)

                    // 3. Если всё ок, летим на экран успеха
                    Toast.makeText(requireContext(), "Пароль успешно обновлен", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_newPasswordFragment_to_passwordUpdatedFragment)
                }
            } else {
                Toast.makeText(requireContext(), "Ошибка: email не найден", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvGoToReset.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupPasswordValidation() {
        // ... (твой метод без изменений)
        val passwordWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateButtonState()
            }
            override fun afterTextChanged(s: Editable?) {}
        }
        binding.etRecoveryPassword.addTextChangedListener(passwordWatcher)
        binding.etRecoveryConfirmPassword.addTextChangedListener(passwordWatcher)
    }

    private fun updateButtonState() {
        // ... (твой метод без изменений)
        val password = binding.etRecoveryPassword.text.toString()
        val confirmPassword = binding.etRecoveryConfirmPassword.text.toString()
        val isNotEmpty = password.isNotEmpty() && confirmPassword.isNotEmpty()
        val passwordsMatch = password == confirmPassword
        val isEnabled = isNotEmpty && passwordsMatch
        binding.btnResetPassword.isEnabled = isEnabled
        binding.btnResetPassword.alpha = if (isEnabled) 1.0f else 0.5f
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}