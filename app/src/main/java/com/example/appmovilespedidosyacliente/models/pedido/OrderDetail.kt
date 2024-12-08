package com.example.appmovilespedidosyacliente.models.pedido

data class OrderDetail (
    val id: Int,
    val quantity: Int,
    val price: String,
    val product: Product
)