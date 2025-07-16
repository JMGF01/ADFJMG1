package edu.adf.adfjmg1

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class VersionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MIAPP","Entrando en onCreate()")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Log.d("MIAPP","Saliendo de onCreate()")
    }

    fun saludar(view: View) {
        //TODO: poner en la caja de texto: "Hola Mundo".
        Log.d("MIAPP","El usuario ha tocado el botón de saludar()")
    }
    fun obtenerVersion(view: View) {
        //TODO: poner en la caja de texto la versión Android del dispositivo.
        Log.d("MIAPP","El usuario ha tocado el botón de obtenerVersion()")
    }
}