package edu.adf.adfjmg1.basedatos.repository

import edu.adf.adfjmg1.basedatos.dao.CocheDao
import edu.adf.adfjmg1.basedatos.dao.EmpleoDao
import edu.adf.adfjmg1.basedatos.dao.PersonaDao
import edu.adf.adfjmg1.basedatos.entity.Coche
import edu.adf.adfjmg1.basedatos.entity.Empleo
import edu.adf.adfjmg1.basedatos.entity.Persona

class Repositorio(private val personaDao: PersonaDao, private val empleoDao: EmpleoDao, private val cocheDao: CocheDao) {

    val todasLasPersonas = personaDao.obtenerTodas()

    suspend fun insertar(persona: Persona):Long
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

    suspend fun insertarPersonaYEmpleo(persona: Persona, empleo: Empleo)
    {
        personaDao.insertar(persona)
        empleo.personaId = persona.id
        empleoDao.insert(empleo)
    }

    suspend fun insertarCoche(coche: Coche)
    {
        cocheDao.insertCoche(coche)
    }

    suspend fun borrarCoche(coche: Coche)
    {
        cocheDao.deleteCoche(coche)
    }
}