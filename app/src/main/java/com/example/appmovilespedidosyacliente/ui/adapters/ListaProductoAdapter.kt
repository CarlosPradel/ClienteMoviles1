package com.example.appmovilespedidosyacliente.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appmovilespedidosyacliente.R
import com.example.appmovilespedidosyacliente.databinding.ItemListaProductosBinding
import com.example.appmovilespedidosyacliente.models.Producto
import com.example.appmovilespedidosyacliente.models.Productos

class ListaProductoAdapter(
    private val onAgregarProducto: (Producto) -> Unit
): RecyclerView.Adapter<ListaProductoAdapter.ProductoItemViewHolder>() {

    private var listaProducto: Productos = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoItemViewHolder {
        val binding = ItemListaProductosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductoItemViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return listaProducto.size
    }

    override fun onBindViewHolder(holder: ProductoItemViewHolder, position: Int) {
        holder.bind(listaProducto[position], onAgregarProducto)
    }

    fun updateData(it: Productos) {
        listaProducto = it ?: arrayListOf()
        notifyDataSetChanged()
    }

    class ProductoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val lblNombreProducto: TextView = itemView.findViewById(R.id.lblNombreProducto)
        private val lblDescripcionProducto: TextView = itemView.findViewById(R.id.lblDescripcionProducto)
        private val lblPrecioProducto: TextView = itemView.findViewById(R.id.lblPrecioProducto)
        private val imgProducto: ImageView = itemView.findViewById(R.id.imgProducto)
        private val btnAgregarProducto: Button = itemView.findViewById(R.id.btnAgregarProducto)

        fun bind(producto: Producto, onAgregarProducto: (Producto) -> Unit) {
            lblNombreProducto.text = producto.name
            lblDescripcionProducto.text = producto.description
            lblPrecioProducto.text = producto.price

            Glide.with(itemView.context)
                .load(producto.image)
                .into(imgProducto)

            btnAgregarProducto.setOnClickListener {
                onAgregarProducto(producto)
            }
        }

    }
}