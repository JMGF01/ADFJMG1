package edu.adf.adfjmg1.basedatos.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import edu.adf.adfjmg1.Constantes
import edu.adf.adfjmg1.basedatos.entity.Persona
import edu.adf.adfjmg1.basedatos.repository.Repositorio
import edu.adf.profe.basedatos.db.AppDatabase
import kotlinx.coroutines.launch


class PersonaViewModel(application: Application): AndroidViewModel(application) // se pasa el contexto (en este caso pasamos application, pero podríamos pasar el contexto) porque es necesario para trabajar con BBDD.
{
    private val repository: Repositorio
    val personas: LiveData<List<Persona>>

    init {
        val dao = AppDatabase.getDatabase(application).personaDao()
        repository = Repositorio(dao)
        personas = repository.todasLasPersonas
    }

    /*fun insertar(persona: Persona) = viewModelScope.launch {
        repository.insertar(persona)
    }*/

    fun insertar(persona: Persona) {
        viewModelScope.launch {
            repository.insertar(persona)
        }
    }

    fun borrar(persona: Persona) {
        viewModelScope.launch {
            repository.borrar(persona)
        }
    }

    fun contarPersonas() {
        viewModelScope.launch {
            val nper = repository.contarPersonas()
            Log.d(Constantes.ETIQUETA_LOG, "numpersonas  $nper")
        }
    }

}