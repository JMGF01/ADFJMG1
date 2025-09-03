package edu.adf.adfjmg1

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class BienvenidaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_bienvenida)

        val tvb = findViewById<TextView>(R.id.textoBienvenida)

        var usuario:Usuario?

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        {
            usuario = intent.getParcelableExtra("usuario", Usuario::class.java)
        } else {
            usuario = intent.getParcelableExtra("usuario")
        }

        tvb.text = "BIENVENID@ \n ${usuario?.nombre} ${usuario?.edad}"
        tvb.setTextColor(usuario!!.colorFavorito)
    }
}