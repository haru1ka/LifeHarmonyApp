package com.example.lifeharmonyapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lifeharmonyapp.databinding.FragmentPasswordUpdatedBinding
import com.example.lifeharmonyapp.databinding.FragmentRegistrationBinding
import com.example.lifeharmonyapp.databinding.FragmentResetVerificationBinding

class PasswordUpdatedFragment: Fragment(R.layout.fragment_password_updated) {
    private var _binding: FragmentPasswordUpdatedBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPasswordUpdatedBinding.bind(view)


        binding.btnGoLogin.setOnClickListener {
            findNavController().navigate(R.id.action_passwordUpdatedFragment_to_loginFragment)
        }
    }
}