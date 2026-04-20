package com.example.lifeharmonyapp

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lifeharmonyapp.databinding.FragmentColorThemeBinding

class ThemeSettingsFragment : Fragment(R.layout.fragment_color_theme) {
    private var _binding: FragmentColorThemeBinding? = null
    private val binding get() = _binding!!
    private companion object {
        private const val PREFS_NAME = "app_settings"
        private const val KEY_THEME = "selected_theme"

        private const val THEME_LIGHT = "light"
        private const val THEME_DARK = "dark"
        private const val THEME_SYSTEM = "system"
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentColorThemeBinding.bind(view)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        val currentTheme = getSavedTheme()
        updateCheckmarks(currentTheme)

        binding.rowLight.setOnClickListener { applyTheme(THEME_LIGHT) }
        binding.rowDark.setOnClickListener { applyTheme(THEME_DARK) }
        binding.rowSystem.setOnClickListener { applyTheme(THEME_SYSTEM) }
    }
    private fun applyTheme(theme: String) {
        saveTheme(theme)

        when (theme) {
            THEME_LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            THEME_DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            THEME_SYSTEM -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }

        updateCheckmarks(theme)
    }
    private fun updateCheckmarks(selectedTheme: String) {
        binding.ivCheckLight.visibility = if (selectedTheme == THEME_LIGHT) View.VISIBLE else View.GONE
        binding.ivCheckDark.visibility = if (selectedTheme == THEME_DARK) View.VISIBLE else View.GONE
        binding.ivCheckSystem.visibility = if (selectedTheme == THEME_SYSTEM) View.VISIBLE else View.GONE
    }
    private fun saveTheme(theme: String) {
        requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_THEME, theme)
            .apply()
    }
    private fun getSavedTheme(): String {
        return requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_THEME, THEME_LIGHT) ?: THEME_LIGHT
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}