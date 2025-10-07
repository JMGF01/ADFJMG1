package edu.adf.adfjmg1.basedatos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.adf.adfjmg1.basedatos.entity.Persona
import edu.adf.adfjmg1.databinding.FilaPersonaBinding

class AdapterPersonas (var listaPersonas:List<Persona>): RecyclerView.Adapter<PersonaViewHolder>() {
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
    }
}