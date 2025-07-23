package edu.adf.adfjmg1

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class SubColorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_color)
        // oculta el action bar
        supportActionBar?.hide()
//        finish()
    }

    fun colorSeleccionado(view: View) {
        val fondo = view.background
        val colorfondo = fondo as ColorDrawable//casting
        val color = colorfondo.color
        Log.d("MIAPP_SUBCOLOR", "COLOR SELECCIONADO = $color")

        //guardar el color seleccioando como la resultado de la actividad
        //y finalizar la actividad
        val intent_resultado = Intent() //este intent, representa el valor devuelto por la actividad  memoria temporal
        intent_resultado.putExtra("COLOR_ELEGIDO", color)

        setResult(RESULT_OK, intent_resultado)
        finish()
    }
}