package edu.adf.adfjmg1

import android.media.Image
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ImagenResultadoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val resultado = intent.getStringExtra("resultado")  // "Aquí estoy leyendo del saco.
        Log.d("MIAPP_IMC_RESULTADO","RESULTADO = $resultado")
//        enableEdgeToEdge() // es para que se pueda usar la pantalla entera, incluyendo donde está el rejoj, la batería, etc
        setContentView(R.layout.activity_imagen_resultado)
/*        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
*/
        cambiarTextoEImagen(resultado!!)

    }

    fun cambiarTextoEImagen(resultado:String):Unit
    {
        val imageView:ImageView = findViewById<ImageView>(R.id.fotoResultado)

        var imagenImc:Int = when (resultado) {
            "DESNUTRIDO" -> R.drawable.imc_desnutrido
            "DELGADO" -> R.drawable.imc_delgado
            "IDEAL" -> R.drawable.imc_ideal
            "SOBREPESO" -> R.drawable.imc_sobrepeso
            "OBESO" ->  R.drawable.imc_obeso
            else -> R.drawable.imc_desnutrido
        }
        imageView.setImageResource(imagenImc)

        val tvLeyenda:TextView = findViewById<TextView>(R.id.leyenda)
        tvLeyenda.text = resultado
    }


}