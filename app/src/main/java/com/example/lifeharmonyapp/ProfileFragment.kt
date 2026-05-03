package com.example.lifeharmonyapp

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.lifeharmonyapp.data.AppDatabase
import com.example.lifeharmonyapp.databinding.FragmentProfileBinding
import kotlinx.coroutines.launch

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        // Кнопка назад
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        // Переход к редактированию (карандаш)
        binding.ivEditPencil.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

        // Смена пароля
        binding.btnChangePassword.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editPasswordFragment)
        }

        // Настройки темы
        binding.btnTheme.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_themeSettingsFragment)
        }
    }

    // Это гарантирует, что данные обновятся сразу после возврата из редактирования
    override fun onResume() {
        super.onResume()
        loadUserProfile()
    }

    private fun loadUserProfile() {
        // Достаем ID текущего пользователя
        val sharedPref = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userId = sharedPref.getInt("current_user_id", -1)

        if (userId != -1) {
            viewLifecycleOwner.lifecycleScope.launch {
                val db = AppDatabase.getDatabase(requireContext())
                val user = db.userDao().getUserById(userId)

                user?.let {
                    // Обновляем поля, используя точные ID из твоего XML
                    binding.tvValueName.text = it.name
                    binding.tvValueEmail.text = it.email
                    binding.tvValueNick.text = it.nickname ?: "Не указан"

                    // Теперь эти поля будут отображать актуальные данные из БД
                    binding.tvValueBirthdate.text = it.birthDate ?: "Не указана"
                    binding.tvValueCountry.text = it.country ?: "Не указана"
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}