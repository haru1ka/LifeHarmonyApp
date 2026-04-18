package com.example.lifeharmonyapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.lifeharmonyapp.data.AppDatabase
import com.example.lifeharmonyapp.data.User // ДОБАВИЛИ ИМПОРТ ТУТ
import com.example.lifeharmonyapp.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        // Кнопка войти
        binding.button.setOnClickListener { // Если в XML id: button, оставляем так
            loginUser()
        }

        binding.tvGoToRegistration.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        binding.tvGoToReset.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_passwordRecoveryFragment)
        }
    }

    private fun loginUser() {
        // ПРОВЕРЬ: если в XML id другие (напр. editTextEmail), замени etEmail на них
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
            return
        }

        viewLifecycleOwner.lifecycleScope.launch {
            val db = AppDatabase.getDatabase(requireContext())
            val user = db.userDao().getUserByEmail(email)

            if (user != null) {
                if (user.password == password) {
                    if (user.isVerified == 1) {
                        Toast.makeText(requireContext(), "Приятного пользования!", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    } else {
                        Toast.makeText(requireContext(), "Подтвердите почту!", Toast.LENGTH_LONG).show()
                        val bundle = Bundle().apply { putString("user_email", email) }
                        // ПРОВЕРЬ ID ЭТОГО ПЕРЕХОДА В nav_graph.xml
                        findNavController().navigate(R.id.action_loginFragment_to_verificationFragment, bundle)
                    }
                } else {
                    Toast.makeText(requireContext(), "Неверный пароль", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Пользователь не найден", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}