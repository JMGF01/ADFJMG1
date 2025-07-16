package edu.adf.adfjmg1

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SumaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_suma)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun sumar(view: View)
    {
        var sumando1:Int = findViewById<EditText>(R.id.sumando1).text.toString().toInt()
        var sumando2:Int = findViewById<EditText>(R.id.sumando2).text.toString().toInt()
        var suma = sumando1 + sumando2
        findViewById<TextView>(R.id.resultadoSuma).text = suma.toString()
    }

}