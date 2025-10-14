package edu.adf.adfjmg1.basedatos.adapter

import androidx.recyclerview.widget.RecyclerView
import edu.adf.adfjmg1.basedatos.entity.PersonaConDetalles
import edu.adf.adfjmg1.databinding.FilaPersonaBinding

class PersonaViewHolder(val filaPersona: FilaPersonaBinding): RecyclerView.ViewHolder(filaPersona.root) {

//    fun rellenarFila (persona: Persona)
//    {
//        this.filaPersona.idpersona.text = persona.id.toString()
//        this.filaPersona.nombre.text = persona.nombre
//        this.filaPersona.edad.text = persona.edad.toString()
//    }

    fun rellenarFila (personaConDetalles: PersonaConDetalles)
    {
        this.filaPersona.idpersona.text =  personaConDetalles.persona.id.toString() //persona.id.toString()
        this.filaPersona.nombre.text = personaConDetalles.persona.nombre //persona.nombre
        this.filaPersona.edad.text = personaConDetalles.persona.edad.toString()
        this.filaPersona.nombreEmpleo.text = personaConDetalles.empleo?.nombre;
        // this.itemView.tag = personaConDetalles.persona.id.toString() // otra idea para luego saber con gettag el id de la persona tocada
    }
}