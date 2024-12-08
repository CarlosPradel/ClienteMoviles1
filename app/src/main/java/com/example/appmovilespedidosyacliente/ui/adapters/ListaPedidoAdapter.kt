package com.example.appmovilespedidosyacliente.ui.adapters

import Order
import Orders
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.appmovilespedidosyacliente.R
import com.example.appmovilespedidosyacliente.databinding.ItemListaPedidosBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListaPedidoAdapter : RecyclerView.Adapter<ListaPedidoAdapter.PedidoItemViewHolder>() {

    private var listaDePedidos: Orders = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoItemViewHolder {
        val binding = ItemListaPedidosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PedidoItemViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return listaDePedidos.size
    }

    override fun onBindViewHolder(holder: PedidoItemViewHolder, position: Int) {
        holder.bind(listaDePedidos[position])
    }

    fun updateData(it: Orders) {
        listaDePedidos = it
        notifyDataSetChanged()
    }

    class PedidoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var lblNumeroPedido: TextView = itemView.findViewById(R.id.lblNumeroPedido)
        private var lblRestauranteNombrePedido: TextView = itemView.findViewById(R.id.lblRestauranteNombrePedido)
        private var lblPrecioProductoPedido: TextView = itemView.findViewById(R.id.lblPrecioProductoPedido)
        private var lblEstadoPedido: TextView = itemView.findViewById(R.id.lblEstadoPedido)


        private var fabDetallePedido: FloatingActionButton = itemView.findViewById(R.id.fabDetallePedido)
        private var fabMapaRecorrido: FloatingActionButton = itemView.findViewById(R.id.fabMapaRecorrido)

        fun bind(pedido: Order) {
            lblNumeroPedido.text = pedido.id.toString()
            lblRestauranteNombrePedido.text = pedido.restaurant_id.toString()
            lblPrecioProductoPedido.text = pedido.total + " bs"
            lblEstadoPedido.text = getEstadoDescripcion(pedido.status)

            fabDetallePedido.setOnClickListener {
                Toast.makeText(itemView.context, "Detalle Pedido", Toast.LENGTH_SHORT).show()
            }

            fabMapaRecorrido.setOnClickListener {
                Toast.makeText(itemView.context, "Mapa Recorrido", Toast.LENGTH_SHORT).show()
            }
        }

        private fun getEstadoDescripcion(status: String): String {
            return when (status) {
                "1" -> "Solicitado"
                "2" -> "Aceptado"
                "3" -> "En camino"
                "4" -> "Finalizado"
                else -> "Desconocido"
            }
        }
    }
}
