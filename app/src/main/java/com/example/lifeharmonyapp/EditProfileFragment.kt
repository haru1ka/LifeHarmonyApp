package com.example.lifeharmonyapp

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lifeharmonyapp.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEditProfileBinding.bind(view)

        // Настройка Spinner для выбора страны
        val countries = arrayOf("Россия", "Казахстан", "Беларусь", "Украина", "Другая")
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item,
            countries
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCountry.adapter = adapter
        binding.spinnerCountry.setSelection(0)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnDone.setOnClickListener {
            saveUserData()
            Toast.makeText(requireContext(), "Данные сохранены", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }

        binding.tvDontSave.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun saveUserData() {
        // Получаем текст из каждого поля и преобразуем в String
        val name = binding.etName.text.toString()
        val nickname = binding.etNickname.text.toString()
        val email = binding.etEmail.text.toString()
        val birthDate = binding.etBirthDate.text.toString()
        val country = binding.spinnerCountry.selectedItem.toString()

        android.util.Log.d("EditProfile", "Сохраняем данные:")
        android.util.Log.d("EditProfile", "  Имя: $name")
        android.util.Log.d("EditProfile", "  Ник: $nickname")
        android.util.Log.d("EditProfile", "  Email: $email")
        android.util.Log.d("EditProfile", "  Дата: $birthDate")
        android.util.Log.d("EditProfile", "  Страна: $country")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}