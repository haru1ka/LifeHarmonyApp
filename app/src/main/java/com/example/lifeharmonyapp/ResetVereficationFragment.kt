package com.example.lifeharmonyapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lifeharmonyapp.databinding.FragmentLoginBinding
import com.example.lifeharmonyapp.databinding.FragmentRegistrationBinding
import com.example.lifeharmonyapp.databinding.FragmentResetVerificationBinding
import com.example.lifeharmonyapp.databinding.FragmentVerificationBinding


class ResetVereficationFragment : Fragment(R.layout.fragment_reset_verification) {
    private var _binding: FragmentResetVerificationBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentResetVerificationBinding.bind(view)


        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_resetVereficationFragment_to_newPasswordFragment)
        }

        //возвращение назад
        binding.tvGoToRecovery.setOnClickListener {
            findNavController().popBackStack()
        }


        }
}

