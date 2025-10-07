package edu.adf.adfjmg1.basedatos.repository

import edu.adf.adfjmg1.basedatos.dao.PersonaDao
import edu.adf.adfjmg1.basedatos.entity.Persona

class Repositorio(private val personaDao: PersonaDao) {

    val todasLasPersonas = personaDao.obtenerTodas()

    suspend fun insertar(persona: Persona)
    {
        personaDao.insertar(persona)
    }

    suspend fun borrar(persona: Persona)
    {
        personaDao.borrar(persona)
    }

    suspend fun contarPersonas():Int
    {
        return personaDao.countPersonas()
    }

}