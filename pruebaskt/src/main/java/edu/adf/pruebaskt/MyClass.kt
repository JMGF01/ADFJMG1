package edu.adf.pruebaskt

import kotlin.system.measureNanoTime

fun main() {
/*    val morningNotification = 51
    val eveningNotification = 135

    printNotificationSummary(morningNotification)
    printNotificationSummary(eveningNotification)

    var resultadoCadena = traducirResultadoImcVersionIF(24.0f)
    println(resultadoCadena)
    resultadoCadena = traducirResultadoImcVersionIF(15.0f)
    println(resultadoCadena)
    println("${traducirResultadoImcVersionIF(15.99f)} ${traducirResultadoImcVersionIF(17.99f)} " +
            "${traducirResultadoImcVersionIF(24.99f)} ${traducirResultadoImcVersionIF(30.99f)} " +
            "${traducirResultadoImcVersionIF(31.01f)}")

    println("Introduce un IMC: ")
    var imcTeclado= readln()
    println(traducirResultadoImcVersionIF(imcTeclado.toFloat()))

    resultadoCadena = traducirResultadoImcVersionWhen(24.0f)
    println(resultadoCadena)
    resultadoCadena = traducirResultadoImcVersionWhen(15.0f)
    println(resultadoCadena)

    println("${traducirResultadoImcVersionWhen(15.99f)} ${traducirResultadoImcVersionWhen(17.99f)} " +
            "${traducirResultadoImcVersionWhen(24.99f)} ${traducirResultadoImcVersionWhen(30.99f)} " +
            "${traducirResultadoImcVersionWhen(431.01f)}")

    resultadoCadena = traducirResultadoImcVersionWhen2(24.0f)
    println(resultadoCadena)
    resultadoCadena = traducirResultadoImcVersionWhen2(15.0f)
    println(resultadoCadena)

    println("${traducirResultadoImcVersionWhen(15.99f)} ${traducirResultadoImcVersionWhen(17.99f)} " +
            "${traducirResultadoImcVersionWhen(24.99f)} ${traducirResultadoImcVersionWhen(30.99f)} " +
            "${traducirResultadoImcVersionWhen(431.01f)}")

 */

    val child = 5
    val adult = 28
    val senior = 87

    val isMonday = true


    // Test ejercicio 1
    println("*** TEST EJERCICIO 1 ***")
    println(esAdulto(14))
    println(esAdulto(18))
    println()

    // Test ejercicio 2
    println("*** TEST EJERCICIO 2 ***")
    println(resultadoNotas(0))
    println(resultadoNotas(5))
    println(resultadoNotas(6))
    println(resultadoNotas(8))
    println(resultadoNotas(10))
    println()

    // Test ejercicio 3
    println("*** TEST EJERCICIO 3 ***")
    println(numeroMayor1(3,45,8))
    println(numeroMayor1(26,13,8))
    println(numeroMayor1(1,57,124))
    println()

    //Test rendimiento
    println("*** PRUEBA DE RENDIMIENTO ***")
    val tiemponumeroMayor1:Long = measureNanoTime { numeroMayor1(1,57,124) }
    val tiemponumeroMayor2:Long = measureNanoTime { numeroMayor2(1,57,124) }
    println("Tiempo de ejecución de numeroMayor1: $tiemponumeroMayor1 ns")
    println("Tiempo de ejecución de numeroMayor2: $tiemponumeroMayor2 ns")
    println()

    // Test ejercicio 4
/*  println("*** TEST EJERCICIO 4 ***")
    println("The movie ticket price for a person aged $child is \$${ticketPrice(child, isMonday)}.")
    println("The movie ticket price for a person aged $adult is \$${ticketPrice(adult, isMonday)}.")
    println("The movie ticket price for a person aged $senior is \$${ticketPrice(senior, isMonday)}.")
    println()
*/
    println("*** TicketPrice ***")
    println("The movie ticket price for a person aged $child is \$${ticketPrice(child, isMonday)}.")
    println("The movie ticket price for a person aged $adult is \$${ticketPrice(adult, isMonday)}.")
    println("The movie ticket price for a person aged $senior is \$${ticketPrice(senior, isMonday)}.")
    println()

}


fun printNotificationSummary(numberOfMessages: Int) {
    if (numberOfMessages < 100)
    {
        println("You have $numberOfMessages notifications")
    }
    else
    {
        println("Your phone is blowing up! You have 99+ notifications.")
    }
}

fun traducirResultadoImc(resultadoImc: Float):String
{
    var imcResultado:String = ""

    return imcResultado
}

fun traducirResultadoImcVersionIF(resultado:Float):String
{
    var imcResultado:String = ""

    //TODO completar el cuerpo de la función o con IF o con WHEN
    if (resultado<16) {
        imcResultado = "DESNUTRIDO"
    } else if (resultado>=16 && resultado<18)
    {
        imcResultado = "DELGADO"
    } else if (resultado>=18 && resultado <25)
    {
        imcResultado = "IDEAL"
    } else if (resultado>=25 && resultado<31)
    {
        imcResultado = "SOBREPESO"
    } else if (resultado>=31)
    {
        imcResultado = "OBESO"
    }

    return imcResultado
}

fun traducirResultadoImcVersionWhen(resultado:Float):String
{
    var imcResultado:String = ""

    imcResultado = when {
        resultado < 16 -> "DESNUTRIDO"
        resultado >= 16 && resultado < 18 -> "DELGADO"
        resultado >= 18 && resultado < 25 -> "IDEAL"
        resultado >= 25 && resultado < 31 -> "SOBREPESO"
        else -> "OBESO"
    }

    return imcResultado
}

fun traducirResultadoImcVersionWhen2 (resultado:Float):String
{
    var imcResultado:String = ""

    val resultadoInt = resultado.toInt()

    imcResultado = when (resultadoInt) {
        in 1 until 15 -> "DESNUTRIDO"
        in 16 until 18 -> "DELGAD0"
        in 18 until 25 -> "IDEAL"
        in 25 until 31 -> "SOBREPESO"
        else -> "OBESO"
    }

    return imcResultado
}

//EJERICICIOS BÁSICOS
/*
1) HACED UNA FUNCIÓN QUE RECIBA UNA EDAD Y DIGA SI ES MAYOR DE EDAD O NO.
2) HACED UNA FUNCIÓN, QUE RECIBA UNA NOTA NUMÉRICA Y DIGA LA NOMINAL CORRESPONDIENTE,
BASÁNDOSE EN ESTE RANGO

0-4 SUSPENSO
5- APROBADO
6 - BIEN
7, 8 - NOTABLE
9, 10 - SOBRESALIENTE

3) HACED UNA FUNCIÓN QUE RECIBA 3 NÚMEROS Y DIGA CUÁL ES EL MAYOR
4) ENUNCIADO DE GOOGLE : PRECIO DE LAS ENTRADAS DE CINE
https://developer.android.com/codelabs/basic-android-kotlin-compose-kotlin-fundamentals-practice-problems?hl=es-419#2
*/

/**
 * EJERCICIO 1 -
 * RECIBE UNA EDAD Y DICE SI ES MAYOR DE EDAD (+18) O NO
 * @param age la edad a evaluar
 * @return string indicando si es mayor o menor de edad
 */
fun esAdulto(age:Int):String
{
    var vmayorEdad:String = "Eres menor de edad"

    if (age >= 18)
    {
        vmayorEdad = "Eres mayor de edad"
    }

    return vmayorEdad
}

/**
 * EJERCICIO 2 -
 * RECIBE UNA NOTA NUMÉRICA Y DICE LA NOMINAL CORRESPONDIENTE
 * BASÁNDOSE EN ESTE RANGO:
 *   - 0-4 SUSPENSO
 *   - 5- APROBADO
 *   - 6 - BIEN
 *   - 7, 8 - NOTABLE
 *   - 9, 10 - SOBRESALIENTE
 * @param nota la nota a evaluar
 * @return la evaluación de la nota
 */
fun resultadoNotas(nota:Int):String
{
    var evaluacion:String = ""

    evaluacion = when
    {
        nota <= 4 -> "SUSPENSO"
        nota >= 5 && nota < 6 -> "APROBADO"
        nota >= 6 && nota < 7 -> "BIEN"
        nota >= 7 && nota <= 8 -> "NOTABLE"
        else -> "SOBRESALIENTE"
    }

    return evaluacion
}

/**
 * EJERCICIO 3 -
 * RECIBE 3 NÚMEROS Y DICE CUÁL ES EL MAYOR
 * @param num1 primer número
 * @param num2 segundo número
 * @param num3 tercer número
 * @return devuelve el número mayor
 */
fun numeroMayor1(num1:Int, num2:Int, num3:Int):Int
{
    var numMayor:Int = num3

    if (num1 > num2) {
        if (num1 > num3){
            numMayor = num1
        }
    } else if (num2 > num3){
            numMayor = num2
        }

    return numMayor
}

fun numeroMayor2(num1:Int, num2: Int, num3: Int):Int
{
    return maxOf(num1, num2, num3)
}

/**
 * EJERCICIO 4 -
 * Función que recibe una edad y si es lunes,y devuelve un valor de entrada de cine
 * @param age edad
 * @param isMonday booleano que indica si es lunes
 * @return el precio de la entrada
 */
fun ticketPriceIf(age: Int, isMonday: Boolean): Int {

    var precioEntrada:Int = -1

    if (age < 12) {
        precioEntrada = 15
    } else if (age > 13 && age < 60) {
        if (isMonday)
        {
            precioEntrada = 25
        } else
            precioEntrada = 30
    } else if (age > 61) {
            precioEntrada = 20
    }

    return precioEntrada
}

/**
 * EJERCICIO 4 -
 * Función que recibe una edad y si es lunes,y devuelve un valor de entrada de cine
 * @param age edad
 * @param isMonday booleano que indica si es lunes
 * @return el precio de la entrada
 */
fun ticketPrice(age: Int, isMonday: Boolean): Int {

    var precioEntrada:Int = -1

     when (age) {
        in 0..12 -> precioEntrada = 15
        in 13..60 -> {
            if (isMonday)
            {
                precioEntrada = 25
            } else
                precioEntrada = 30
        }
        in 61..100 -> precioEntrada = 20
    }

    return precioEntrada
}



























class MyClass {
}