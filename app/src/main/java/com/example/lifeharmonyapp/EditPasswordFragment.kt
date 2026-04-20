package com.example.lifeharmonyapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lifeharmonyapp.databinding.FragmentEditPasswordBinding

class EditPasswordFragment : Fragment(R.layout.fragment_edit_password) {

    private var _binding: FragmentEditPasswordBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentEditPasswordBinding.bind(view)
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnDone.setOnClickListener {
            if (validateAndSavePassword()) {
                Toast.makeText(requireContext(), "Пароль изменён", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
        binding.tvDontSave.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun validateAndSavePassword(): Boolean {
        val oldPassword = binding.etOldPassword.text.toString().trim()
        val newPassword = binding.etNewPassword.text.toString().trim()
        val confirmPassword = binding.etNewPassword2.text.toString().trim()

        if (oldPassword.isEmpty()) {
            showError("Введите старый пароль")
            binding.etOldPassword.requestFocus()
            return false
        }

        if (newPassword != confirmPassword) {
            showError("Пароли не совпадают")
            binding.etNewPassword2.requestFocus()
            return false
        }

        if (newPassword == oldPassword) {
            showError("Новый пароль должен отличаться от старого")
            binding.etNewPassword.requestFocus()
            return false
        }

        android.util.Log.d("ChangePassword", "Пароль успешно изменён")
        return true
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        // Обнуляем binding, чтобы избежать утечки памяти
        // После этого обращаться к binding нельзя
        _binding = null
    }
}