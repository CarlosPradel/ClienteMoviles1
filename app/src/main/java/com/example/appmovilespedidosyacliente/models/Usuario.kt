package com.example.appmovilespedidosyacliente.models

data class Usuario (
    val name: String,
    val email: String,
    val password: String,
    val role: Int
)
typealias Usuarios = ArrayList<Usuario>