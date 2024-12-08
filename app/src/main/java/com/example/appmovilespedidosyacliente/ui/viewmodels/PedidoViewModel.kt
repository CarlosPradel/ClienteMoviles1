package com.example.appmovilespedidosyacliente.ui.viewmodels

import Orders
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appmovilespedidosyacliente.repositories.PedidoRepository

class PedidoViewModel : ViewModel() {

    private val _listaPedido = MutableLiveData<Orders>().apply {
        value = arrayListOf()
    }

    val listaPedido = _listaPedido

    fun obtenerListaPedidos(token: String) {
        PedidoRepository.verOrdersUsuario(
            token,
            onSuccess = {
                _listaPedido.value = it
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

}