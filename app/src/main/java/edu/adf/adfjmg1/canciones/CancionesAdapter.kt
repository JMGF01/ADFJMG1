package edu.adf.adfjmg1.canciones

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.adf.adfjmg1.R
import edu.adf.adfjmg1.productos.ProductoViewHolder

class CancionesAdapter(var resultadoCanciones: ResultITunes): RecyclerView.Adapter<CancionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CancionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cardViewCancion = layoutInflater.inflate(R.layout.fila_cancion, parent, false)
        val cancionViewHolder = CancionViewHolder(cardViewCancion)
        return cancionViewHolder
    }

    override fun getItemCount(): Int {
        return resultadoCanciones.resultCount
    }

    override fun onBindViewHolder(holder: CancionViewHolder, position: Int) {
        val cancionActual:Result = this.resultadoCanciones.results.get(position)
        holder.rellenarCardViewCancion(cancionActual)
    }

}