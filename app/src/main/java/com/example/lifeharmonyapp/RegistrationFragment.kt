package com.example.lifeharmonyapp

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.lifeharmonyapp.data.AppDatabase
import com.example.lifeharmonyapp.data.User
import com.example.lifeharmonyapp.databinding.FragmentRegistrationBinding
import kotlinx.coroutines.launch

class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegistrationBinding.bind(view)

        setupRegistrationLogic()

        // Нажатие на кнопку регистрации
        binding.btnNext.setOnClickListener {
            registerNewUser()
        }

        // Переход на логин
        binding.tvAlreadyHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_registration_to_login)
        }
    }

    private fun registerNewUser() {
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString()

        // Запускаем корутину для работы с базой данных
        viewLifecycleOwner.lifecycleScope.launch {
            val db = AppDatabase.getDatabase(requireContext())
            val user = User(name = name, email = email, password = password)

            // Room возвращает ID новой строки. Если email занят, вернет -1.
            val resultId = db.userDao().registerUser(user)

            if (resultId != -1L) {
                // СОХРАНЕНИЕ СЕССИИ:
                // Записываем ID пользователя в SharedPreferences, чтобы профиль мог подтянуть данные.
                val sharedPref = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                sharedPref.edit().putInt("current_user_id", resultId.toInt()).apply()

                // Кладем email в Bundle для экрана верификации
                val bundle = Bundle().apply {
                    putString("user_email", email)
                }

                // Переход на следующий экран
                findNavController().navigate(R.id.action_registration_to_verification, bundle)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Пользователь с таким email уже существует",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupRegistrationLogic() {
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkFields()
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        binding.etName.addTextChangedListener(watcher)
        binding.etEmail.addTextChangedListener(watcher)
        binding.etPassword.addTextChangedListener(watcher)
        binding.etConfirmPassword.addTextChangedListener(watcher)
    }

    private fun checkFields() {
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val pass = binding.etPassword.text.toString()
        val confirm = binding.etConfirmPassword.text.toString()

        // Условие активности кнопки: поля не пусты и пароли совпадают
        val isValid = name.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty() && pass == confirm

        binding.btnNext.isEnabled = isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}