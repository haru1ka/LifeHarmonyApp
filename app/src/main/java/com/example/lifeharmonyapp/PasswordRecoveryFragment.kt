package com.example.lifeharmonyapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.lifeharmonyapp.data.AppDatabase
import com.example.lifeharmonyapp.databinding.FragmentPasswordRecoveryBinding
import kotlinx.coroutines.launch

class PasswordRecoveryFragment : Fragment(R.layout.fragment_password_recovery) {

    private var _binding: FragmentPasswordRecoveryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPasswordRecoveryBinding.bind(view)

        setupEmailValidation()

        binding.btnRecoveryToReset.setOnClickListener {
            checkEmailAndNavigate()
        }

        binding.tvGoLogin.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun checkEmailAndNavigate() {
        val email = binding.etRecoveryEmail.text.toString().trim()

        viewLifecycleOwner.lifecycleScope.launch {
            val db = AppDatabase.getDatabase(requireContext())
            // Ищем пользователя в базе
            val user = db.userDao().getUserByEmail(email)

            if (user != null) {
                // Если нашли — идем на экран ввода кода и передаем email
                val bundle = Bundle().apply {
                    putString("user_email", email)
                }
                findNavController().navigate(
                    R.id.action_passwordRecoveryFragment_to_resetVereficationFragment,
                    bundle
                )
            } else {
                // Если нет такого email в базе
                Toast.makeText(requireContext(), "Пользователь с такой почтой не найден", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupEmailValidation() {
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val email = s.toString().trim()
                val isEmailValid = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()

                binding.btnRecoveryToReset.isEnabled = isEmailValid
                binding.btnRecoveryToReset.alpha = if (isEmailValid) 1.0f else 0.5f
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        binding.etRecoveryEmail.addTextChangedListener(watcher)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}