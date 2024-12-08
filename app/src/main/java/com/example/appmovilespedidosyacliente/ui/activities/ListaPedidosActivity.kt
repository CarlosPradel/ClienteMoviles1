package com.example.appmovilespedidosyacliente.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmovilespedidosyacliente.R
import com.example.appmovilespedidosyacliente.databinding.ActivityListaPedidosBinding
import com.example.appmovilespedidosyacliente.ui.adapters.ListaPedidoAdapter
import com.example.appmovilespedidosyacliente.ui.viewmodels.PedidoViewModel

class ListaPedidosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaPedidosBinding
    private val viewModel: PedidoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityListaPedidosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupViewModelObservers()
        setupRecyclerView()

        val sharedPreference = getSharedPreferences("APP_PREFS", MODE_PRIVATE)
        val token = sharedPreference.getString("ACCESS_TOKEN", "") ?: ""
        if (token != null){
            viewModel.obtenerListaPedidos(token)
        } else {
            Toast.makeText(this, "No se pudo obtener el token", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupViewModelObservers() {
        viewModel.listaPedido.observe(this) { pedido ->
            val adapter = binding.rvListaPedidos.adapter as ListaPedidoAdapter
            adapter.updateData(pedido)
        }
    }

    private fun setupRecyclerView() {
        val adapter = ListaPedidoAdapter()
        binding.rvListaPedidos.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@ListaPedidosActivity)
        }
    }


}