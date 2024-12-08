package com.example.appmovilespedidosyacliente.models

data class Producto (
    val id: Int,
    val name: String,
    val description: String,
    val price: String,
    val restaurant_id: Int,
    val image: String
)
typealias Productos = ArrayList<Producto>