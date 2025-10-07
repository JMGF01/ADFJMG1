package edu.adf.adfjmg1.basedatos

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import edu.adf.adfjmg1.Constantes
import edu.adf.adfjmg1.basedatos.adapter.AdapterPersonas
import edu.adf.adfjmg1.basedatos.entity.Persona
import edu.adf.adfjmg1.basedatos.viewmodel.PersonaViewModel
import edu.adf.adfjmg1.databinding.ActivityBaseDatosBinding

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

    val personas:MutableList<Persona> = mutableListOf() // creamos la lista de personas vacía
    lateinit var binding: ActivityBaseDatosBinding
    lateinit var adapterPersonas: AdapterPersonas

    //al insntaciar este atributo, se ejecuta la sección init de PersonaViewModel
    val personaViewModel: PersonaViewModel by viewModels() // aquí guardamos los datos de la pantalla..

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseDatosBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val adapterPersonas = AdapterPersonas(personas)
        binding.recview.adapter = adapterPersonas
        binding.recview.layoutManager = LinearLayoutManager(this)

        //ligamos las actualizaciones automáticas de la lista.
        personaViewModel.personas.observe(this, { //Cuando se detecta que hay un cambio, se llama a la función anónima.
            personas ->
            //Log.d(Constantes.ETIQUETA_LOG, "Personas = $personas")
            personas?.let{
                Log.d(Constantes.ETIQUETA_LOG, "Personas = ${personas.size} = $personas")
                adapterPersonas.listaPersonas = it
                adapterPersonas.notifyDataSetChanged()
            }
        })
    }

    fun insertarPersona(view: View) {
        personaViewModel.insertar(Persona(nombre="Andrés", edad = 25))
        personaViewModel.contarPersonas()
    }





}