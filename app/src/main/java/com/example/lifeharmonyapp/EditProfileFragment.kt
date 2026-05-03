package com.example.lifeharmonyapp

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.lifeharmonyapp.data.AppDatabase
import com.example.lifeharmonyapp.data.User
import com.example.lifeharmonyapp.databinding.FragmentEditProfileBinding
import kotlinx.coroutines.launch

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    // Переменная для хранения текущего объекта пользователя из базы
    private var currentUser: User? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEditProfileBinding.bind(view)

        // 1. Настройка Spinner для выбора страны
        setupSpinner()

        // 2. Загружаем данные текущего пользователя, чтобы заполнить поля
        loadUserDataFromDb()

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnDone.setOnClickListener {
            // 3. Сохраняем данные в БД и только потом выходим
            saveUserDataToDb()
        }

        binding.tvDontSave.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupSpinner() {
        val countries = arrayOf("Россия", "Казахстан", "Беларусь", "Украина", "Другая")
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item, // Используем твой кастомный макет для айтема
            countries
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCountry.adapter = adapter
    }

    private fun loadUserDataFromDb() {
        val sharedPref = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userId = sharedPref.getInt("current_user_id", -1)

        if (userId != -1) {
            viewLifecycleOwner.lifecycleScope.launch {
                val db = AppDatabase.getDatabase(requireContext())
                currentUser = db.userDao().getUserById(userId)

                // Заполняем поля данными из объекта User
                currentUser?.let { user ->
                    binding.etName.setText(user.name)
                    binding.etNickname.setText(user.nickname ?: "")
                    binding.etEmail.setText(user.email)
                    binding.etBirthDate.setText(user.birthDate ?: "")

                    // Устанавливаем страну в Spinner, если она уже была выбрана ранее
                    user.country?.let { savedCountry ->
                        val adapter = binding.spinnerCountry.adapter as ArrayAdapter<String>
                        val position = adapter.getPosition(savedCountry)
                        if (position >= 0) binding.spinnerCountry.setSelection(position)
                    }
                }
            }
        }
    }

    private fun saveUserDataToDb() {
        val name = binding.etName.text.toString().trim()
        val nickname = binding.etNickname.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val birthDate = binding.etBirthDate.text.toString().trim()
        val country = binding.spinnerCountry.selectedItem.toString()

        if (name.isEmpty() || email.isEmpty()) {
            Toast.makeText(requireContext(), "Имя и Email не могут быть пустыми", Toast.LENGTH_SHORT).show()
            return
        }

        viewLifecycleOwner.lifecycleScope.launch {
            val db = AppDatabase.getDatabase(requireContext())

            currentUser?.let { user ->
                // Создаем обновленную копию объекта User с новыми данными
                val updatedUser = user.copy(
                    name = name,
                    nickname = nickname,
                    email = email,
                    birthDate = birthDate,
                    country = country
                )

                // 4. Вызываем метод обновления в UserDao (пункт №7 из нашего списка)
                db.userDao().updateUser(updatedUser)

                Toast.makeText(requireContext(), "Данные сохранены", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}