package edu.adf.adfjmg1.basedatos.adapter

import androidx.recyclerview.widget.RecyclerView
import edu.adf.adfjmg1.basedatos.entity.Persona
import edu.adf.adfjmg1.databinding.FilaPersonaBinding

class PersonaViewHolder(val filaPersona: FilaPersonaBinding): RecyclerView.ViewHolder(filaPersona.root) {

    fun rellenarFila (persona: Persona)
    {
        this.filaPersona.idpersona.text = persona.id.toString()
        this.filaPersona.nombre.text = persona.nombre
        this.filaPersona.edad.text = persona.edad.toString()
    }
}