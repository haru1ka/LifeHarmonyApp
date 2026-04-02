package com.example.lifeharmonyapp

import android.os.Bundle
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

        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_newPasswordFragment_to_passwordUpdatedFragment)
        }

        //возвращение назад
        binding.tvGoToReset.setOnClickListener {
            findNavController().popBackStack()
        }


    }
}