package com.example.appmovilespedidosyacliente.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appmovilespedidosyacliente.R
import com.example.appmovilespedidosyacliente.databinding.ItemListaRestaurantesBinding
import com.example.appmovilespedidosyacliente.models.Restaurante
import com.example.appmovilespedidosyacliente.models.Restaurantes

class ListaRestauranteAdapter(private val listener: RestauranteItemListener): RecyclerView.Adapter<ListaRestauranteAdapter.RestauranteItemViewHolder>() {

    private var listaDeRestaurante: Restaurantes = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestauranteItemViewHolder {
        val binding = ItemListaRestaurantesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestauranteItemViewHolder(binding.root, listener)
    }

    override fun getItemCount(): Int {
        return listaDeRestaurante.size
    }

    override fun onBindViewHolder(holder: RestauranteItemViewHolder, position: Int) {
        holder.bind(listaDeRestaurante[position])
    }

    fun updateData(it: Restaurantes) {
        listaDeRestaurante = it
        notifyDataSetChanged()
    }

    class RestauranteItemViewHolder(itemView: View, private val listener: RestauranteItemListener) : RecyclerView.ViewHolder(itemView){

        private val lblNombre: TextView = itemView.findViewById(R.id.lblNombre)
        private val lblDireccion : TextView = itemView.findViewById(R.id.lblDireccion)
        private val imgLogo: ImageView = itemView.findViewById(R.id.imgLogo)

        fun bind(restaurante: Restaurante) {
            lblNombre.text = restaurante.name
            lblDireccion.text = restaurante.address
            Glide.with(itemView.context)
                .load(restaurante.logo)
                .into(imgLogo)

            itemView.setOnClickListener {
                listener.onRestauranteItemClicked(restaurante)
            }
        }

    }

    interface RestauranteItemListener {
        fun onRestauranteItemClicked(restaurante: Restaurante)
    }
}