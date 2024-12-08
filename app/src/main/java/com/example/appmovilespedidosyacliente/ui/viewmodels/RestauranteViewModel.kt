package com.example.appmovilespedidosyacliente.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appmovilespedidosyacliente.models.Productos
import com.example.appmovilespedidosyacliente.models.Restaurantes
import com.example.appmovilespedidosyacliente.repositories.RestauranteRepository

class RestauranteViewModel: ViewModel() {

    private val _listaRestaurantes = MutableLiveData<Restaurantes>().apply {
        value = arrayListOf()
    }
    val listaRestaurantes: LiveData<Restaurantes> = _listaRestaurantes

    private val _listaProductos = MutableLiveData<Productos>().apply {
        value = arrayListOf()
    }
    val listaProductos: LiveData<Productos> = _listaProductos

    fun obtenerListaRestaurantes(token: String) {
        RestauranteRepository.obtenerListaRestaurantes(
            token,
            onSuccess ={
                _listaRestaurantes.value = it
            },
            onError ={
                it.printStackTrace()
            }
        )
    }

    fun obtenerProductoPorRestaurante(token: String, id: Int) {
        RestauranteRepository.obtenerRestauranteById(
            token,
            id,
            onSuccess = {
                _listaProductos.value = it.products
            },
            onError = {
                it.printStackTrace()
            }
        )
    }
}