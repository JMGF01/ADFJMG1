package edu.adf.adfjmg1.productos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.adf.adfjmg1.R

class ProductosAdapter (var listaProductos: List<ListaProductosItem>): RecyclerView.Adapter<ProductoViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val filaProducto = layoutInflater.inflate(R.layout.fila_producto, parent, false)
        val productoViewHolder = ProductoViewHolder(filaProducto)
        return productoViewHolder
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val productoActual = this.listaProductos.get(position)
        holder.rellenarFilaProducto(productoActual)
    }

    override fun getItemCount(): Int {
        return this.listaProductos.size
    }

}