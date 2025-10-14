package edu.adf.adfjmg1.basedatos.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import edu.adf.adfjmg1.Constantes
import edu.adf.adfjmg1.basedatos.entity.PersonaConDetalles
import edu.adf.adfjmg1.databinding.FilaPersonaBinding

//class AdapterPersonas (var listaPersonas:List<Persona>): RecyclerView.Adapter<PersonaViewHolder>() {
class AdapterPersonas (var listaPersonas:List<PersonaConDetalles>, val onClickFila: (PersonaConDetalles)-> Unit): Adapter<PersonaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonaViewHolder {
        var filaPersona = FilaPersonaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonaViewHolder(filaPersona)
    }

    override fun getItemCount(): Int {
        return listaPersonas.size
    }

    override fun onBindViewHolder(holder: PersonaViewHolder, position: Int) {
        val personaActual = this.listaPersonas[position]
        holder.rellenarFila(personaActual)
        holder.itemView.setOnClickListener{
            Log.d(Constantes.ETIQUETA_LOG, "Tocado fila $personaActual")
//            clickFila(personaActual)
            onClickFila(personaActual)//TODO crear un sólo listener para todas las filas
        }
    }

}