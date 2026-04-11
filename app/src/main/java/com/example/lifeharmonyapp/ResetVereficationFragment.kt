package com.example.lifeharmonyapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
        setupVerificationLogic()

        binding.btnNextReset.setOnClickListener {
            findNavController().navigate(R.id.action_resetVereficationFragment_to_newPasswordFragment)
        }
        //возвращение назад
        binding.tvGoToRecovery.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupVerificationLogic() {
        binding.etRecoveryCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val code = s.toString().trim()
                binding.btnNextReset.isEnabled = code.length == 6

                binding.btnNextReset.alpha = if (code.length == 6) 1.0f else 0.5f
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }
}

