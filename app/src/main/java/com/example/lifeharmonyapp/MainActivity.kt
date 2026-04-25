package com.example.lifeharmonyapp

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.lifeharmonyapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.fragment_verification)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

         */
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // 2. Находим саму панель
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // 3. Магия связки: теперь кнопки сами переключают фрагменты
        bottomNav.setupWithNavController(navController)

        // 4. Управляем видимостью (показываем только на главном экране)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.homeFragment) {
                bottomNav.visibility = View.VISIBLE
            } else {
                // Скрываем на логине, регистрации и т.д.
                bottomNav.visibility = View.GONE
            }
        }
        }
    }
