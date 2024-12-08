package com.example.appmovilespedidosyacliente.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmovilespedidosyacliente.R
import com.example.appmovilespedidosyacliente.databinding.ActivityListaDeRestaurantesBinding
import com.example.appmovilespedidosyacliente.models.Restaurante
import com.example.appmovilespedidosyacliente.ui.adapters.ListaRestauranteAdapter
import com.example.appmovilespedidosyacliente.ui.viewmodels.RestauranteViewModel

class ListaDeRestaurantesActivity : AppCompatActivity(), ListaRestauranteAdapter.RestauranteItemListener {

    private lateinit var binding: ActivityListaDeRestaurantesBinding
    private val viewModel: RestauranteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityListaDeRestaurantesBinding.inflate(layoutInflater)
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
            viewModel.obtenerListaRestaurantes(token)
        } else {
            Toast.makeText(this, "No se pudo obtener el token", Toast.LENGTH_SHORT).show()
        }

        setupEventListeners()
    }

    private fun setupEventListeners() {
        binding.fabVerPedidos.setOnClickListener {
            val intent = Intent(this, ListaPedidosActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupViewModelObservers() {
        viewModel.listaRestaurantes.observe(this) { restaurante ->
            val adapter = binding.rvListaDeRestaurantes.adapter as ListaRestauranteAdapter
            adapter.updateData(restaurante)
        }
    }

    private fun setupRecyclerView() {
        val adapter = ListaRestauranteAdapter(this)
        binding.rvListaDeRestaurantes.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@ListaDeRestaurantesActivity)
        }
    }

    override fun onRestauranteItemClicked(restaurante: Restaurante) {
        val intent = Intent(this, ProductoActivity::class.java).apply {
            putExtra("restaurant_id", restaurante.id)
        }
        startActivity(intent)
    }
}