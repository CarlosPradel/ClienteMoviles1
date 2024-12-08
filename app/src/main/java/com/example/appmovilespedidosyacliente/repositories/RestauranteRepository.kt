package com.example.appmovilespedidosyacliente.repositories

import JSONPlaceHolderService
import com.example.appmovilespedidosyacliente.models.Restaurante
import com.example.appmovilespedidosyacliente.models.Restaurantes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RestauranteRepository {

    fun obtenerListaRestaurantes (
        token: String,
        onSuccess: (Restaurantes) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(JSONPlaceHolderService::class.java)

        service.obtenerRestaurantes("Bearer $token").enqueue(object : Callback<Restaurantes> {
            override fun onResponse(call: Call<Restaurantes>, response: Response<Restaurantes>) {
                if (response.isSuccessful) {
                    onSuccess(response.body()!!)
                } else {
                    val error = response.errorBody()?.string() ?: "Error desconocido"
                    onError(Throwable(error))
                }
            }

            override fun onFailure(call: Call<Restaurantes>, t: Throwable) {
                onError(t)
            }
        })
    }

    fun obtenerRestauranteById(
        token: String,
        id: Int,
        onSuccess: (Restaurante) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(JSONPlaceHolderService::class.java)

        service.obtenerRestauranteById("Bearer $token", id).enqueue(object : Callback<Restaurante> {
            override fun onResponse(call: Call<Restaurante>, response: Response<Restaurante>) {
                if (response.isSuccessful && response.body() != null) {
                    println("Respuesta de la API: ${response.body()}")
                    onSuccess(response.body()!!)
                } else {
                    val error = response.errorBody()?.string() ?: "Error desconocido"
                    println("Error en la respuesta: $error")
                    onError(Throwable(error))
                }
            }

            override fun onFailure(call: Call<Restaurante>, t: Throwable) {
                println("Error en la solicitud: ${t.message}")
                onError(t)
            }
        })
    }

}