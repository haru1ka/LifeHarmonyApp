package com.example.lifeharmonyapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lifeharmonyapp.databinding.FragmentLoginBinding
import com.example.lifeharmonyapp.databinding.FragmentProfileBinding

class ProfileFragment: Fragment(R.layout.fragment_profile){
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)


        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.ivEditPencil.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

        binding.btnChangePassword.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editPasswordFragment)
        }

        binding.btnTheme.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_themeSettingsFragment)
        }

    }
}