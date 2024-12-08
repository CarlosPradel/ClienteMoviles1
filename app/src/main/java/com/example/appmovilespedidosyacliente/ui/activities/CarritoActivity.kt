package com.example.appmovilespedidosyacliente.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmovilespedidosyacliente.R
import com.example.appmovilespedidosyacliente.databinding.ActivityCarritoBinding
import com.example.appmovilespedidosyacliente.models.Carrito
import com.example.appmovilespedidosyacliente.models.pedido.DetallePedido
import com.example.appmovilespedidosyacliente.models.pedido.Pedido
import com.example.appmovilespedidosyacliente.repositories.PedidoRepository
import com.example.appmovilespedidosyacliente.ui.adapters.ListaCarritoProductoAdapter

class CarritoActivity : AppCompatActivity() {

    private lateinit var carritoCompras: ArrayList<Carrito>
    private lateinit var binding: ActivityCarritoBinding
    private var restaurante_id: Int = -1
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCarritoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sharedPreference = getSharedPreferences("APP_PREFS", MODE_PRIVATE)
        token = sharedPreference.getString("ACCESS_TOKEN", null)


        carritoCompras = intent.getParcelableArrayListExtra("carrito_compras") ?: arrayListOf()
        restaurante_id = intent.getIntExtra("restaurant_id", -1)
        setupEventListener()
        setupRecyclerView()
    }

    private fun setupEventListener() {
        binding.btnAgregarUbicacion.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivityForResult(intent, 1) // '1' es el código de solicitud
        }

        binding.btnCrearPedido.setOnClickListener {
            crearPedido()
        }
    }

    private fun crearPedido() {
        val detalles = carritoCompras.map {
            DetallePedido(
                product_id = it.product_id,
                qty = it.qty,
                price = it.price.toString()
            )
        }

        val pedido = Pedido(
            restaurant_id = restaurante_id,
            total = calculateTotalForConsole(),
            address = binding.txtDireccion.text.toString(),
            latitude = binding.lblLatitud.text.toString(),
            longitude = binding.lblLongitud.text.toString(),
            details = detalles
        )

        println("Pedido a enviar: $pedido")

        token?.let {
            PedidoRepository.crearPedido(
                token = it,
                pedido = pedido,
                onSuccess = { pedidoCreado ->
                    println("Pedido creado exitosamente: $pedidoCreado")
                    runOnUiThread {
                        Toast.makeText(this, "Pedido creado exitosamente!", Toast.LENGTH_LONG).show()
                    }
                },
                onError = { error ->
                    println("Error al crear el pedido: ${error.message}")
                    runOnUiThread {
                        Toast.makeText(this, "Error al crear el pedido: ${error.message}", Toast.LENGTH_LONG).show()
                    }
                }
            )
        } ?: runOnUiThread {
            Toast.makeText(this, "Autenticación no encontrada, por favor inicie sesión de nuevo.", Toast.LENGTH_LONG).show()
        }
    }

    private fun calculateTotalForConsole(): Double {
        var total = 0.0
        for (carrito in carritoCompras) {
            total += carrito.price * carrito.qty
        }
        return total
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            data?.let {
                val latitude = it.getDoubleExtra("latitude", 0.0)
                val longitude = it.getDoubleExtra("longitude", 0.0)

                // Actualizar los TextViews con los datos de latitud y longitud
                val lblLatitud = findViewById<TextView>(R.id.lblLatitud)
                val lblLongitud = findViewById<TextView>(R.id.lblLongitud)
                lblLatitud.text = latitude.toString()
                lblLongitud.text = longitude.toString()
            }
        }
    }

    private fun setupRecyclerView() {
        val adapter = ListaCarritoProductoAdapter(carritoCompras) { calcularTotalProductos() }
        binding.rvCarrito.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@CarritoActivity)
        }
        calcularTotalProductos()
    }

    private fun calcularTotalProductos() {
        var total = 0.0
        for (carrito in carritoCompras) {
            total += carrito.price * carrito.qty
        }
        binding.lblTotal.text = "$total bs"
    }
}
