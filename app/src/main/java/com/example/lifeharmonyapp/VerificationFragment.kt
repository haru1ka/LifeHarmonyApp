package com.example.lifeharmonyapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lifeharmonyapp.databinding.FragmentVerificationBinding

class VerificationFragment : Fragment(R.layout.fragment_verification) {
    private var _binding: FragmentVerificationBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentVerificationBinding.bind(view)
        setupVerificationLogic()

        //(переход на логин)
        binding.btnAcceptCode.setOnClickListener {
            findNavController().navigate(R.id.action_verification_to_login)
        }
    }
    private fun setupVerificationLogic() {
        binding.etVerificationCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val code = s.toString().trim()
                binding.btnAcceptCode.isEnabled = code.length == 6

                binding.btnAcceptCode.alpha = if (code.length == 6) 1.0f else 0.5f
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}