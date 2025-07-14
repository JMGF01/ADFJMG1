package edu.adf.adfjmg1

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Calculadora : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calculadora)
        imagenBotonSumar() // Si se usa de esta manera la imagen no sale completa
        }

    fun calcularSuma(view: View)
    {
        var sumando1:Int = findViewById<EditText>(R.id.etSumando1).text.toString().toIntOrNull() ?: 0
        var sumando2:Int = findViewById<EditText>(R.id.etSumando2).text.toString().toIntOrNull() ?: 0

        val suma:Int = sumando1 + sumando2
        findViewById<TextView>(R.id.cajaResultado).text = suma.toString()
    }

    /**
     *  Carga la imagen de sumar en el botón de la calculadora
     */
    fun imagenBotonSumar()
    {
        findViewById<ImageButton>(R.id.botonSuma).setImageResource(R.drawable.boton_sumar)

    }



}


