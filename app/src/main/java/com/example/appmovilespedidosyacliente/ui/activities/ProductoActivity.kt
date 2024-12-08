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
import com.example.appmovilespedidosyacliente.databinding.ActivityProductoBinding
import com.example.appmovilespedidosyacliente.models.Carrito
import com.example.appmovilespedidosyacliente.models.Producto
import com.example.appmovilespedidosyacliente.ui.adapters.ListaProductoAdapter
import com.example.appmovilespedidosyacliente.ui.viewmodels.RestauranteViewModel

class ProductoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductoBinding
    private val viewModel: RestauranteViewModel by viewModels()
    private val carritoCompras: ArrayList<Carrito> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        setupViewModelObservers()

        val sharedPreference = getSharedPreferences("APP_PREFS", MODE_PRIVATE)
        val token = sharedPreference.getString("ACCESS_TOKEN", null)
        val restauranteId = intent.getIntExtra("restaurant_id", -1)

        if (token.isNullOrEmpty() || restauranteId == -1) {
            Toast.makeText(this, "Error al obtener los datos necesarios", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.obtenerProductoPorRestaurante(token, restauranteId)

        // Manejar clic en el botón flotante para ir al carrito
        binding.fabCarrito.setOnClickListener {
            val intent = Intent(this, CarritoActivity::class.java)
            intent.putParcelableArrayListExtra("carrito_compras", carritoCompras)
            intent.putExtra("restaurant_id", restauranteId)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        val adapter = ListaProductoAdapter { producto ->
            agregarProductoAlCarrito(producto)
        }
        binding.rvListaProductos.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@ProductoActivity)
        }
    }

    private fun agregarProductoAlCarrito(producto: Producto) {

        val itemExistente = carritoCompras.any { it.product_id == producto.id }

        if (itemExistente) {
            return
        }

        val item = Carrito(
            product_id = producto.id,
            nombre = producto.name,
            qty = 1,
            price = producto.price.toDouble()
        )
        carritoCompras.add(item)
        Toast.makeText(this, "Producto agregado al carrito", Toast.LENGTH_SHORT).show()
    }

    private fun setupViewModelObservers() {
        viewModel.listaProductos.observe(this) { productos ->
            if (productos != null) {
                val adapter = binding.rvListaProductos.adapter as ListaProductoAdapter
                adapter.updateData(productos)
            } else {
                Toast.makeText(this, "No se encontraron productos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}