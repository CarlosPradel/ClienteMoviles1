package com.example.appmovilespedidosyacliente.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.appmovilespedidosyacliente.models.Usuario
import com.example.appmovilespedidosyacliente.repositories.UsuarioRepository

class UsuarioViewModel: ViewModel() {

    fun crearUsuario(usuario: Usuario, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        UsuarioRepository.postCrearUsuario(
            usuario,
            onSuccess = {
                onSuccess()
            },
            onError = {
                onError(it)
            }
        )
    }

}