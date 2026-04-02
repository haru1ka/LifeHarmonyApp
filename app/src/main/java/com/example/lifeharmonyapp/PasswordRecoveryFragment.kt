package com.example.lifeharmonyapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lifeharmonyapp.databinding.FragmentPasswordRecoveryBinding
import com.example.lifeharmonyapp.databinding.FragmentRegistrationBinding

class PasswordRecoveryFragment : Fragment(R.layout.fragment_password_recovery) {

    private var _binding: FragmentPasswordRecoveryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPasswordRecoveryBinding.bind(view)

        binding.btnRecoveryToReset.setOnClickListener {
            findNavController().navigate(R.id.action_passwordRecoveryFragment_to_resetVereficationFragment)
        }

        binding.tvGoLogin.setOnClickListener {
            findNavController().popBackStack()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}