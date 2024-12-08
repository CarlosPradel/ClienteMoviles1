package com.example.appmovilespedidosyacliente.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appmovilespedidosyacliente.R
import com.example.appmovilespedidosyacliente.databinding.ItemListaCarritoProductoBinding
import com.example.appmovilespedidosyacliente.models.Carrito

class ListaCarritoProductoAdapter(
    private val carritoCompras: ArrayList<Carrito>,
    private val onQuantityChanged: () -> Unit
) : RecyclerView.Adapter<ListaCarritoProductoAdapter.CarritoProductoItemViewHolder>()  {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CarritoProductoItemViewHolder {
        val binding = ItemListaCarritoProductoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarritoProductoItemViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return carritoCompras.size
    }

    override fun onBindViewHolder(holder: CarritoProductoItemViewHolder, position: Int) {
        holder.bind(carritoCompras[position]) { carrito, change ->
            carrito.qty += change
            notifyItemChanged(position)
            onQuantityChanged() // Notifica a la actividad para recalcular el total
        }
    }

    class CarritoProductoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val lblNombreRestaurante: TextView = itemView.findViewById(R.id.lblNombreRestaurante)
        private val lblNombreRestauranteProducto: TextView = itemView.findViewById(R.id.lblNombreRestauranteProducto)
        private val lblCantidadCarritoProducto: TextView = itemView.findViewById(R.id.lblCantidadCarritoProducto)
        private val lblPrecioProductoCarrito: TextView = itemView.findViewById(R.id.lblPrecioProductoCarrito)
        private val btnIncrease: Button = itemView.findViewById(R.id.button3)
        private val btnDecrease: Button = itemView.findViewById(R.id.button4)

        fun bind(carrito: Carrito, onQuantityChanged: (Carrito, Int) -> Unit) {
            lblNombreRestaurante.text = carrito.nombre
            lblNombreRestauranteProducto.text = carrito.nombre
            lblCantidadCarritoProducto.text = carrito.qty.toString()
            updatePriceDisplay(carrito)

            btnIncrease.setOnClickListener {
                onQuantityChanged(carrito, 1)
            }

            btnDecrease.setOnClickListener {
                if (carrito.qty > 1) {
                    onQuantityChanged(carrito, -1)
                }
            }
        }

        private fun updatePriceDisplay(carrito: Carrito) {
            val totalPrice = carrito.price * carrito.qty
            lblPrecioProductoCarrito.text = "${totalPrice} bs"
        }
    }

}