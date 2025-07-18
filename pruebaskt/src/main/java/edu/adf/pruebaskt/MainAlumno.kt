package edu.adf.pruebaskt

import kotlin.system.measureNanoTime

fun main() {

    // declaro un alumno
    val alumno:Alumno = Alumno("Vale",41,5)
    val alumno1:Alumno = Alumno("Fran",45,8)
    val alumno2:Alumno = Alumno("Miguel",19,8)


    println("EDAD Fran ${alumno1.edad}")
    val listaAlumnos:List<Alumno> = listOf(alumno, alumno1, alumno2)

    //vamos a recorrer la lista de Alumnos // bucle

    for (al in listaAlumnos) // al es un alumno cada vez
    {
        println("NOMBRE ${al.nombre} EDAD ${al.edad} NOTA ${al.nota}")
    }

    println("LA MEDIA DE EDAD ES: ${mediaEdadAlumnos(listaAlumnos)}" )
    println("LA MEDIA DE EDAD ES: ${mediaEdadAlumnos2(listaAlumnos)}" )
    println("LA MEDIA DE EDAD ES: ${mediaEdadAlumnos2(listaAlumnos)}" )

    val tiempo_mediaEdadAlumnos:Long = measureNanoTime { mediaEdadAlumnos(listaAlumnos) }
    val tiempo_mediaEdadAlumnos2:Long = measureNanoTime { mediaEdadAlumnos2(listaAlumnos) }
    val tiempo_mediaEdadAlumnos3:Long = measureNanoTime { mediaEdadAlumnos2(listaAlumnos) }
    println("El tiempo de ejecución de mediaEdadAlumnos es: $tiempo_mediaEdadAlumnos")
    println("El tiempo de ejecución de mediaEdadAlumnos2 es: $tiempo_mediaEdadAlumnos2")
    println("El tiempo de ejecución de mediaEdadAlumnos3 es: $tiempo_mediaEdadAlumnos3")

    for (a in listaAlumnos) {
        edadAlumno(a)
    }

    println("RECORRIDO CON FOREACH CON FUNCIÓN ANÓNIMA")
    listaAlumnos.forEach( fun (a: Alumno) {
        println("LA EDAD ES: ${a.edad}")
    })

    println("RECORRIDO CON FOREACH CON FUNCIÓN LAMBDA")
    listaAlumnos.forEach({ //el parámetro es una variable predefinida que se llama it (iterador)
        println("LA EDAD ES: ${it.edad}")
    })

}

// TODO Haced una función que reciba una lista de alumnos y devuelva la media de las edades

/**
 * recibe una lista de tipo Alumno y devuelve la media de las edades
 * @param listAlumnos la lista de alumnos
 * @return la media de edad
 */
fun mediaEdadAlumnos(listAlumnos: List<Alumno>): Float
{
    var sumaEdades:Int = 0

    for (alumnoEdad in listAlumnos)
    {
        sumaEdades = sumaEdades + alumnoEdad.edad
    }

    return (sumaEdades.toFloat() / listAlumnos.size)
}

/**
 * recibe una lista de tipo Alumno y devuelve la media de las edades, utilizando la función sumOf
 * @param listAlumnos la lista de alumnos
 * @return la media de edad
 */
fun mediaEdadAlumnos2(listAlumnos: List<Alumno>): Float
{
    var sumaEdades = listAlumnos.sumOf { alumno -> alumno.edad }
    return (sumaEdades.toFloat() / listAlumnos.size)
}

/**
 * recibe una lista de tipo Alumno y devuelve la media de las edades, utilizando la función map
 * @param listAlumnos la lista de alumnos
 * @return la media de edad
 */
fun mediaEdadAlumnos3(listAlumnos: List<Alumno>): Float
{
    return (listAlumnos.map { alumno -> alumno.edad }.average()).toFloat()
}


// TODO Haced una función que reciba un Alumno e imprima su edad. No devuelve nada.

fun edadAlumno(alumno:Alumno)
{
    println("LA EDAD ES: ${alumno.edad}")
}


