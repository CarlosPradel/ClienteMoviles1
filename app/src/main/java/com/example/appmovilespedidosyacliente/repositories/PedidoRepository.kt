package com.example.appmovilespedidosyacliente.repositories

import JSONPlaceHolderService
import Orders
import com.example.appmovilespedidosyacliente.models.pedido.Pedido
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PedidoRepository {

    fun crearPedido(
        token: String,
        pedido: Pedido,
        onSuccess: (Pedido) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(JSONPlaceHolderService::class.java)

        service.crearPedido("Bearer $token", pedido).enqueue(object : Callback<Pedido> {
            override fun onResponse(call: Call<Pedido>, response: Response<Pedido>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        onSuccess(it)
                        println("Pedido creado con éxito: $it")
                    } ?: onError(Throwable("No se recibió el objeto de pedido"))
                } else {
                    onError(Throwable("Error en la respuesta: ${response.errorBody()?.string()}"))
                }
            }

            override fun onFailure(call: Call<Pedido>, t: Throwable) {
                onError(t)
                println("Error de red: ${t.message}")
            }
        })
    }

    fun verOrdersUsuario(
        token: String,
        onSuccess: (Orders) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(JSONPlaceHolderService::class.java)


        service.verPedidosUsuario("Bearer $token").enqueue(object : Callback<Orders> {
            override fun onResponse(call: Call<Orders>, response: Response<Orders>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        onSuccess(it)
                        println("Pedidos obtenidos con éxito: $it")
                    } ?: onError(Throwable("No se recibió la lista de pedidos"))
                } else {
                    onError(Throwable("Error en la respuesta: ${response.errorBody()?.string()}"))
                }
            }

            override fun onFailure(call: Call<Orders>, t: Throwable) {
                onError(t)
                println("Error de red: ${t.message}")
            }
        })
    }
}
