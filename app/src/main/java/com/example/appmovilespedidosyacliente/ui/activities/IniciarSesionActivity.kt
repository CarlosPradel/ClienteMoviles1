package com.example.appmovilespedidosyacliente.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appmovilespedidosyacliente.R
import com.example.appmovilespedidosyacliente.databinding.ActivityIniciarSesionBinding
import com.example.appmovilespedidosyacliente.repositories.UsuarioRepository

class IniciarSesionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIniciarSesionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityIniciarSesionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.buttonLogin.setOnClickListener {
            iniciarSesion()
        }
        binding.textViewRegister.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }

    private fun iniciarSesion() {
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        UsuarioRepository.postLogin(
            email,
            password,
            onSuccess = { token ->
                Toast.makeText(this, "Inicio de sesión exitoso. Token: $token", Toast.LENGTH_LONG).show()
                val sharedPreferences = getSharedPreferences("APP_PREFS", MODE_PRIVATE)
                sharedPreferences.edit().putString("ACCESS_TOKEN", token).apply()

                val intent = Intent(this, ListaDeRestaurantesActivity::class.java)
                startActivity(intent)
            },
            onError = { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }
}