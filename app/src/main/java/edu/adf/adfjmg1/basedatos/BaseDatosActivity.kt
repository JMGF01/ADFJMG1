package edu.adf.adfjmg1.basedatos

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import edu.adf.adfjmg1.Constantes
import edu.adf.adfjmg1.R
import edu.adf.adfjmg1.basedatos.adapter.AdapterPersonas
import edu.adf.adfjmg1.basedatos.entity.Empleo
import edu.adf.adfjmg1.basedatos.entity.Persona
import edu.adf.adfjmg1.basedatos.entity.PersonaConDetalles
import edu.adf.adfjmg1.basedatos.viewmodel.PersonaViewModel
import edu.adf.adfjmg1.databinding.ActivityBaseDatosBinding
import edu.adf.adfjmg1.util.LogUtil
import kotlinx.coroutines.launch
import java.util.Date

/**
 * ///////////////////////////////////////////////////////////////////////
 *
 * https://codeshare.io/2WkxeW
 * 1 ) //añadimos esta línea en plugins del gradle del proyecto manualmente
 * id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
 *
 * 2) ahora en el del módulo, igual en plugins, sólo el id manualmente
 *
 * id("com.google.devtools.ksp")
 *
 * 3 ) Ahora También como dependencia desde el buscador : Androidx.lifecycle  --> lifecycle-livedata  --> versión 2.9.3
 *
 * 4 ) Añadir como dependencia desde el buscador : androidx.room --> room-common --> 2.7.2
 *
 * 6 ) Añadir como dependencia desde el buscador : androidx.room --> room-compiler --> 2.7.2
 *
 *     En esta una vez añadida cambiar dentro del fichero build.graddle en lugar de
 *     implementation(libs.androidx.room-compiler) poner -->  ksp (libs.androidx.room-compiler) y
 *
 * 7 ) Añadir como dependencia desde el buscador : androidx.room --> room-ktx --> 2.7.2
 *
 * 8) --- EMNPEZAR EL PROYECTO CARPETA APARTE Nuevo package --> basedatos
 *
 * Nueva Actividad vacia BaseDatosActivity
 *
 * Dentro del package otra carpeta para meter el adapter --> la llamamos adapter
 *
 * Dentro del package otra carpeta para meter el entity --> la llamamos entity
 * 	Dentro de esta una DataClass Persona
 *
 * La estructura Quedaría : 	basedatos (carpeta)
 * 														adapter (carpeta)
 *                             dao (carpeta donde iran las operaciones)
 *                             		PersonaDao (Interfaz Kotlin donde iran descritas las operaciones)
 *                             db (carpeta)
 *                             		AppDatabase (clase de Kotlin)
 *                             entity (capeta)
 *                             		Persona (Data class de Kotlin)
 *                             BaseDatosActivity.kt (Actividad)
 *
 *
 *
 */

class BaseDatosActivity : AppCompatActivity() {

//    var personas:MutableList<Persona> = mutableListOf() // creamos la lista de personas vacía
var personas:MutableList<PersonaConDetalles> = mutableListOf()//creamos la lista de personas vacía
    lateinit var binding: ActivityBaseDatosBinding
    lateinit var adapterPersonas: AdapterPersonas

    //al insntaciar este atributo, se ejecuta la sección init de PersonaViewModel
    val personaViewModel: PersonaViewModel by viewModels() // aquí guardamos los datos de la pantalla..

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseDatosBinding.inflate(layoutInflater)
        setContentView(binding.root)


        adapterPersonas = AdapterPersonas(personas, ::clickFila)
        binding.recview.adapter = adapterPersonas
        binding.recview.layoutManager = LinearLayoutManager(this)
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recview)

        //ligamos las actualizaciones automáticas de la lista.

//        personaViewModel.personas.observe(this, { //Cuando se detecta que hay un cambio, se llama a la función anónima.
        personaViewModel.personasDetalles.observe(this, {
            personas ->
            //Log.d(Constantes.ETIQUETA_LOG, "Personas = $personas")
            personas?.let{
                Log.d(Constantes.ETIQUETA_LOG, "Personas = ${personas.size} = $personas")
                adapterPersonas.listaPersonas = it
                //TODO deberíamos controlar si la lista se ha actualizado por borrar
                // o por insertar y en qué posición, para así usar
                // notifyItemRemoved(posicion_elemento_eliminado);
                // o notifyItemInserted(posicion_elemento_insertado);
                //y repintar sólo esa posición de la fila
                // adapterPersonas.notifyDataSetChanged()
//                adapterPersonas.notifyDataSetChanged()
                when (personaViewModel.ultimaOperacionBD)
                {
                    UltimaOperacionBD.INSERTAR -> {
                        adapterPersonas.notifyItemInserted(personaViewModel.posicionAfectada)
                        Log.d(Constantes.ETIQUETA_LOG,"Lista actualizada tras inserción en pos $personaViewModel.posicionAfectada"
                        )
                    }
                    UltimaOperacionBD.BORRAR -> {
                        adapterPersonas.notifyItemRemoved(personaViewModel.posicionAfectada)
                        Log.d(Constantes.ETIQUETA_LOG,"Lista actualizada tras inserción en pos $personaViewModel.posicionAfectada"
                        )
                    }
                    UltimaOperacionBD.NINGUNA -> {
                        adapterPersonas.notifyDataSetChanged()
                        Log.d(Constantes.ETIQUETA_LOG, "Lista actualizada sin inserción ni borrado")
                    }
                }
                personaViewModel.ultimaOperacionBD = UltimaOperacionBD.NINGUNA//actualizamos

            }
        })
    }

    // para generar numero aleatorio
    fun generarNumeroAleatorio(): Int {
        return (1..100).random()
    }

    // para generar nombrea aleatorios
    fun generarNombre(): String {
        val silabas = listOf(
            "ma", "ri", "an", "jo", "se", "la", "lu", "mi", "el", "no",
            "da", "na", "so", "le", "pe", "ro", "car", "al", "be", "vi"
        )

        val cantidadSilabas = (2..3).random() // nombres de 2 o 3 sílabas

        val nombre = StringBuilder()
        repeat(cantidadSilabas) {
            nombre.append(silabas.random())
        }

        // Capitalizar la primera letra
        return nombre.toString().replaceFirstChar { it.uppercaseChar() }
    }

    fun clickFila(personaConDetalles: PersonaConDetalles)
    {
        Log.d(Constantes.ETIQUETA_LOG, "Tocado fila $personaConDetalles")
        lifecycleScope.launch {
            var par = personaViewModel.obtenerCochesPersona(personaConDetalles.persona.id)
            Log.d(Constantes.ETIQUETA_LOG, "${LogUtil.getLogInfo()} Persona con id = ${par.first}")
            Log.d(Constantes.ETIQUETA_LOG, "${LogUtil.getLogInfo()} Coches = ${par.first}")
        }
    }

//    fun insertarPersona(view: View) {
//        personaViewModel.insertar(Persona(nombre="Andrés", edad = 25))
//        personaViewModel.contarPersonas()
//    }

//    fun insertarPersona(view: View) {
//        personaViewModel.insertar(Persona(nombre =generarNombre(), edad =generarNumeroAleatorio()), personaViewModel.personas.value!!.size)
//        personaViewModel.contarPersonas()
//    }
    fun insertarPersona(view: View) {
        personaViewModel.insertar(Persona(nombre =generarNombre(), edad =generarNumeroAleatorio()), personaViewModel.personasDetalles.value!!.size)
        personaViewModel.contarPersonas()
    }

//    fun insertarPersonaYEmpleo(view: View) {
////        val personaAux = Persona(nombre="Andrés", edad = 25)
//        val personaAux = Persona(nombre =generarNombre(), edad =generarNumeroAleatorio())
//        val empleoAux = Empleo(0,0,"BARRENDERO", Date(), 1500.0, TipoContrato.TEMPORAL)
//        personaViewModel.insertarPersonaYEmpleo(personaAux, personaViewModel.personas.value.size!!, empleoAux)
//        personaViewModel.contarPersonas()
//    }

    fun insertarPersonaYEmpleo(view: View) {
        val personaAux = Persona(nombre =generarNombre(), edad =generarNumeroAleatorio())
        val empleoAux = Empleo(0, 0, "BARRENDERO", Date(), 1500.0, Empleo.TipoContrato.TEMPORAL)
        personaViewModel.insertarPersonaYEmpleo(personaAux, personaViewModel.personasDetalles.value!!.size, empleoAux)
//        personaViewModel.contarPersonas()
    }

    val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
        ): Boolean {
            return false // No necesitamos mover los elementos, solo manejar el deslizamiento
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
//            val persona = this@BaseDatosActivity.adapterPersonas.listaPersonas[position] // Método que debes crear en tu adaptador
            val persona = this@BaseDatosActivity.adapterPersonas.listaPersonas[position].persona // Método que debes crear en tu adaptador
            val empleo = this@BaseDatosActivity.adapterPersonas.listaPersonas[position].empleo

            if (direction == ItemTouchHelper.LEFT) {
                // Aquí es donde eliminamos el ítem
                personaViewModel.borrar(persona, position)

                // Mostrar Snackbar para deshacer la eliminación
                Snackbar.make(this@BaseDatosActivity.binding.recview,"Persona eliminada",Snackbar.LENGTH_LONG)
                    .setAction("Deshacer") {
                        // Si el usuario quiere deshacer, simplemente reinsertamos el ítem
                        personaViewModel.insertarPersonaYEmpleo(persona, position, empleo!!)
                    }
                    .show()
            } else if (direction == ItemTouchHelper.RIGHT) {
                Log.d(Constantes.ETIQUETA_LOG, "Swiped right - favorito")
                //this@BaseDatosActivity.binding.textView.text = persona.nombre
                adapterPersonas.notifyItemChanged(position) //repintamos el azul a su original
                Snackbar.make(binding.recview, "Marcado como favorito", Snackbar.LENGTH_SHORT).show()
            }
        }

        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float, // desplazamiento horizontal
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

            // Solo aplicar si se está deslizando hacia la izquierda
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && dX < 0) {
                val itemView = viewHolder.itemView
                val paint = Paint()
                paint.color = Color.RED

                // Dibuja el fondo rojo
                c.drawRect(
                    itemView.right.toFloat() + dX, // izquierda del fondo
                    itemView.top.toFloat(),
                    itemView.right.toFloat(),      // derecha del fondo
                    itemView.bottom.toFloat(),
                    paint
                )

                // Carga el icono
                val deleteIcon =
                    ContextCompat.getDrawable(recyclerView.context, R.drawable.ic_delete)
                val iconMargin = 32
                val iconSize = 64

                deleteIcon?.let {
                    val iconTop = itemView.top + (itemView.height - iconSize) / 2
                    val iconLeft = itemView.right - iconMargin - iconSize
                    val iconRight = itemView.right - iconMargin
                    val iconBottom = iconTop + iconSize

                    it.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                    it.draw(c)

                    // 3. Texto "Eliminar"
                    val text = "Eliminar"
                    val textPaint = Paint()
                    textPaint.color = Color.WHITE
                    textPaint.textSize = 40f
                    textPaint.isAntiAlias = true
                    textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)

                    // Calcular ancho del texto
                    val textWidth = textPaint.measureText(text)

                    // Dibujar texto a la izquierda del ícono
                    val textX = iconLeft - textWidth - 20f
                    val textY = itemView.top + itemView.height / 2f + 15f // Ajuste vertical

                    c.drawText(text, textX, textY, textPaint)
                }
            }

            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && dX > 0) {
                val itemView = viewHolder.itemView
                val paint = Paint()
                paint.color = Color.CYAN

                // Dibuja el fondo cyan
                c.drawRect(
                    itemView.left.toFloat(), // derecha del fondo
                    itemView.top.toFloat(),
                    itemView.left.toFloat() + dX,      // izquierda del fondo
                    itemView.bottom.toFloat(),
                    paint
                )

                // Carga el icono
                val deleteIcon =
                    ContextCompat.getDrawable(recyclerView.context, R.drawable.twotone_favorite_24)
                val iconMargin = 24
                val iconSize = 64

                deleteIcon?.let {
                    val iconTop = itemView.top + (itemView.height - iconSize) / 2
                    val iconLeft = itemView.left + iconMargin
                    val iconRight = itemView.left + iconSize
                    val iconBottom = iconTop + iconSize

                    it.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                    it.draw(c)

                    // 3. Texto "Favorito"
                    val text = "Favorito"
                    val textPaint = Paint()
                    textPaint.color = Color.WHITE
                    textPaint.textSize = 40f
                    textPaint.isAntiAlias = true
                    textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)

                    // Calcular ancho del texto
                    val textWidth = textPaint.measureText(text)

                    // Dibujar texto a la derecha del ícono
                    val textX = iconRight + textWidth + 20f
                    val textY = itemView.top + itemView.height / 2f + 15f // Ajuste vertical

                    c.drawText(text, textX, textY, textPaint)
                }
            }
        }
    }



}