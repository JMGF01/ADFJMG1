package edu.adf.pruebaskt

fun main() {
    val morningNotification = 51
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

class MyClass {
}