package edu.adf.adfjmg1.basedatos.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import edu.adf.adfjmg1.Constantes
import edu.adf.adfjmg1.basedatos.UltimaOperacionBD
import edu.adf.adfjmg1.basedatos.entity.Coche
import edu.adf.adfjmg1.basedatos.entity.Empleo
import edu.adf.adfjmg1.basedatos.entity.Persona
import edu.adf.adfjmg1.basedatos.entity.PersonaConDetalles
import edu.adf.adfjmg1.basedatos.repository.Repositorio
import edu.adf.profe.basedatos.db.AppDatabase
import kotlinx.coroutines.launch


class PersonaViewModel(application: Application): AndroidViewModel(application) // se pasa el contexto (en este caso pasamos application, pero podríamos pasar el contexto) porque es necesario para trabajar con BBDD.
{
    private val repository: Repositorio
//    val personas: LiveData<List<Persona>>
    val personasDetalles: LiveData<List<PersonaConDetalles>>
    lateinit var ultimaOperacionBD: UltimaOperacionBD
    var posicionAfectada:Int = -1

    init {
        val personaDao = AppDatabase.getDatabase(application).personaDao()
        val empleoDao = AppDatabase.getDatabase(application).empleoDao()
        val cocheDao = AppDatabase.getDatabase(application).cocheDao()
        repository = Repositorio(personaDao, empleoDao, cocheDao)
//        personas = repository.todasLasPersonas
        personasDetalles = repository.todasLasPersonas
        ultimaOperacionBD = UltimaOperacionBD.NINGUNA
    }

    /*fun insertar(persona: Persona) = viewModelScope.launch {
        repository.insertar(persona)
    }*/

    fun insertar(persona: Persona, pos:Int) {
        viewModelScope.launch {
            repository.insertar(persona)
            ultimaOperacionBD = UltimaOperacionBD.INSERTAR
            posicionAfectada = pos
        }
    }

    fun insertarPersonaYEmpleo(persona: Persona, pos: Int, empleo: Empleo) {
        viewModelScope.launch {
            repository.insertarPersonaYEmpleo(persona, empleo)
            ultimaOperacionBD = UltimaOperacionBD.INSERTAR
            posicionAfectada = pos
        }
    }

    fun borrar(persona: Persona, pos:Int) {
        viewModelScope.launch {
            repository.borrar(persona)
            ultimaOperacionBD = UltimaOperacionBD.BORRAR
            posicionAfectada = pos
        }
    }

    fun contarPersonas() {
        viewModelScope.launch {
            val nper = repository.contarPersonas()
            Log.d(Constantes.ETIQUETA_LOG, "numpersonas  $nper")
        }
    }

    suspend fun obtenerCochesPersona(personaId: Int):Pair<Int, List<Coche>>
    {
        var listaCoches = repository.leerCochesPersona(personaId)
        return personaId to listaCoches
    }


}