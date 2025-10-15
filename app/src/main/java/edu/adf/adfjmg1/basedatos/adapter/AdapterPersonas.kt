package edu.adf.adfjmg1.basedatos.adapter

import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView.Adapter
import edu.adf.adfjmg1.Constantes
import edu.adf.adfjmg1.R
import edu.adf.adfjmg1.basedatos.entity.PersonaConDetalles
import edu.adf.adfjmg1.databinding.FilaPersonaBinding
import edu.adf.adfjmg1.util.LogUtil

//class AdapterPersonas (var listaPersonas:List<Persona>): RecyclerView.Adapter<PersonaViewHolder>() {
class AdapterPersonas (var listaPersonas:List<PersonaConDetalles>, val onClickFila: (PersonaConDetalles)-> Unit): Adapter<PersonaViewHolder>() {

    lateinit var itemSeleccionado:View

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
//        holder.itemView.setOnClickListener{
//            Log.d(Constantes.ETIQUETA_LOG, "Tocado fila $personaActual")
////            clickFila(personaActual)
//            onClickFila(personaActual)//TODO crear un sólo listener para todas las filas
//        }
        holder.itemView.tag = personaActual
        holder.itemView.setOnClickListener (::listenerFila)
        holder.itemView.setOnLongClickListener {
            val p = PopupMenu(it.context, it)
            p.getMenuInflater().inflate(R.menu.menu_item_personas, p.getMenu());
            p.gravity = Gravity.CENTER//No tiene efecto, el menú sale pegado a la izquierda
            p.show()
            it.setBackgroundColor(Color.GRAY)
            //TODO desmarcar
            itemSeleccionado = it
            p.setOnDismissListener {
                itemSeleccionado?.setBackgroundColor(Color.TRANSPARENT)
                // Quitar selección al cerrar el menú
                //selectedPosition = RecyclerView.NO_POSITION
                //notifyDataSetChanged()
            }
            p.setOnMenuItemClickListener {
                Log.d(Constantes.ETIQUETA_LOG, " ${LogUtil.getLogInfo()} Opción Tocada ${it.title}")
                true
            }

            true
        }
    }

    fun listenerFila(view: View)
    {
        val personaFila = view.tag as PersonaConDetalles
        onClickFila(personaFila)
    }

}