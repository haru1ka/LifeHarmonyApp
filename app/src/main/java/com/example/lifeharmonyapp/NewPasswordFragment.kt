package com.example.lifeharmonyapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lifeharmonyapp.databinding.FragmentNewPasswordBinding
import com.example.lifeharmonyapp.databinding.FragmentRegistrationBinding
import com.example.lifeharmonyapp.databinding.FragmentResetVerificationBinding

class NewPasswordFragment: Fragment(R.layout.fragment_new_password){
    private var _binding: FragmentNewPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewPasswordBinding.bind(view)
        setupPasswordValidation()

        binding.btnResetPassword.setOnClickListener {
            findNavController().navigate(R.id.action_newPasswordFragment_to_passwordUpdatedFragment)
        }

        //возвращение назад
        binding.tvGoToReset.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    private fun setupPasswordValidation() {
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
        val password = binding.etRecoveryPassword.text.toString()
        val confirmPassword = binding.etRecoveryConfirmPassword.text.toString()

        val isNotEmpty = password.isNotEmpty() && confirmPassword.isNotEmpty()
        val passwordsMatch = password == confirmPassword

        val isEnabled = isNotEmpty && passwordsMatch
        binding.btnResetPassword.isEnabled = isEnabled

        binding.btnResetPassword.alpha = if (isEnabled) 1.0f else 0.5f
    }
}