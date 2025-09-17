package edu.adf.adfjmg1.canciones

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.adf.adfjmg1.R

class CancionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val tvAutor: TextView = itemView.findViewById<TextView>(R.id.autor)

    fun rellenarCardViewCancion(cancion: Result) {

        tvAutor.text = cancion.artistName
    }

}