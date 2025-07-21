package edu.adf.adfjmg1

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.adf.adfjmg1.databinding.ActivityBusquedaBinding
import edu.adf.adfjmg1.databinding.ActivityEnviarMensajeBinding

class EnviarMensajeActivity : AppCompatActivity() {

    lateinit var binding: ActivityEnviarMensajeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnviarMensajeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun enviarMensaje(view: View)
    {
        val mensaje:String = binding.etMensajeEnviar.text.toString()
        val intentEnviarTexto = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, mensaje)
        }
        startActivity(Intent.createChooser(intentEnviarTexto, "Enviar mensaje con ..."))
    }
}