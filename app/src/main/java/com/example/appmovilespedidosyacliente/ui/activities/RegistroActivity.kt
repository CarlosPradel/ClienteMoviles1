package com.example.appmovilespedidosyacliente.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appmovilespedidosyacliente.R
import com.example.appmovilespedidosyacliente.databinding.ActivityRegistroBinding
import com.example.appmovilespedidosyacliente.models.Usuario
import com.example.appmovilespedidosyacliente.ui.viewmodels.UsuarioViewModel

class RegistroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding
    private val viewModel: UsuarioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.buttonRegister.setOnClickListener {
            registrarUsuario()
        }
    }

    private fun registrarUsuario() {
        val usuario = Usuario(
            name = binding.editTextName.text.toString(),
            email = binding.editTextEmail.text.toString(),
            password = binding.editTextPassword.text.toString(),
            role = 1
        )

        viewModel.crearUsuario(usuario,
            onSuccess = {
                Toast.makeText(this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, IniciarSesionActivity::class.java)
                startActivity(intent)
            },
            onError = {
                Toast.makeText(this, "Error al crear usuario", Toast.LENGTH_SHORT).show()
            }
        )
    }
}