package com.example.lifeharmonyapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lifeharmonyapp.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegistrationBinding.bind(view)

        setupRegistrationLogic()

        //переход на верификацию
        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_registration_to_verification)
        }

        //переход на логин
        binding.tvAlreadyHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_registration_to_login)
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

        val isValid = name.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty() && pass == confirm

        binding.btnNext.isEnabled = isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}