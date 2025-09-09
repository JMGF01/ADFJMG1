package edu.adf.adfjmg1.productos

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.adf.adfjmg1.R
import edu.adf.adfjmg1.ejercicio1.binding
import org.w3c.dom.Text
import com.bumptech.glide.Glide

class ProductoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val tvIdProducto: TextView = itemView.findViewById<TextView>(R.id.idProducto)
    val tvNombreProducto: TextView = itemView.findViewById<TextView>(R.id.nombreProducto)
    val tvPrecioProducto: TextView = itemView.findViewById<TextView>(R.id.precioProducto)
    val ivImagenProducto: ImageView = itemView.findViewById<ImageView>(R.id.imagenProducto)


    fun rellenarFilaProducto(producto: ListaProductosItem)
    {
        this.tvIdProducto.text = producto.id.toString()
        this.tvNombreProducto.text = producto.name
        this.tvPrecioProducto.text = producto.price
        //this.ivImagenProducto.setImageResource(R.drawable.imc_ideal)
        Glide.with(itemView.context)
            .load(producto.imageUrl)
            .into(ivImagenProducto)
    }

}