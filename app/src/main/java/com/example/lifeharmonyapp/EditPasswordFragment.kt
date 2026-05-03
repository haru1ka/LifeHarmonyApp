package com.example.lifeharmonyapp

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.lifeharmonyapp.data.AppDatabase
import com.example.lifeharmonyapp.data.User
import com.example.lifeharmonyapp.databinding.FragmentEditPasswordBinding
import kotlinx.coroutines.launch

class EditPasswordFragment : Fragment(R.layout.fragment_edit_password) {

    private var _binding: FragmentEditPasswordBinding? = null
    private val binding get() = _binding!!

    // Переменная для хранения текущего пользователя
    private var currentUser: User? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEditPasswordBinding.bind(view)

        // 1. Загружаем данные пользователя из базы сразу при входе
        loadUserData()

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnDone.setOnClickListener {
            // Запускаем процесс смены пароля
            changePasswordInDb()
        }

        binding.tvDontSave.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun loadUserData() {
        // Достаем ID текущего пользователя из SharedPreferences
        val sharedPref = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userId = sharedPref.getInt("current_user_id", -1)

        if (userId != -1) {
            viewLifecycleOwner.lifecycleScope.launch {
                val db = AppDatabase.getDatabase(requireContext())
                // Получаем объект User по ID
                currentUser = db.userDao().getUserById(userId)
            }
        }
    }

    private fun changePasswordInDb() {
        val oldPasswordInput = binding.etOldPassword.text.toString().trim()
        val newPasswordInput = binding.etNewPassword.text.toString().trim()
        val confirmPasswordInput = binding.etNewPassword2.text.toString().trim()

        // 2. Проверка на заполнение полей
        if (oldPasswordInput.isEmpty() || newPasswordInput.isEmpty() || confirmPasswordInput.isEmpty()) {
            showError("Заполните все поля")
            return
        }

        val user = currentUser
        if (user == null) {
            showError("Ошибка загрузки данных пользователя")
            return
        }

        // 3. Проверка: совпадает ли введенный старый пароль с тем, что в базе
        if (user.password != oldPasswordInput) {
            showError("Старый пароль введен неверно")
            return
        }

        // 4. Проверка: совпадает ли новый пароль с подтверждением
        if (newPasswordInput != confirmPasswordInput) {
            showError("Новые пароли не совпадают")
            return
        }

        // 5. Проверка: не совпадает ли новый пароль со старым
        if (newPasswordInput == oldPasswordInput) {
            showError("Новый пароль не должен совпадать со старым")
            return
        }

        // 6. Если всё ок — сохраняем в базу данных
        viewLifecycleOwner.lifecycleScope.launch {
            val db = AppDatabase.getDatabase(requireContext())

            // Создаем копию пользователя с новым паролем
            val updatedUser = user.copy(password = newPasswordInput)

            // Обновляем запись в таблице users_table
            db.userDao().updateUser(updatedUser)

            Toast.makeText(requireContext(), "Пароль успешно изменён", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}