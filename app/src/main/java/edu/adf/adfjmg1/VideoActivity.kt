package edu.adf.adfjmg1

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.adf.adfjmg1.databinding.ActivityBusquedaBinding
import edu.adf.adfjmg1.databinding.ActivityVideoBinding

class VideoActivity : AppCompatActivity() {

    lateinit var binding: ActivityVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide() // oculto la appbar
        window.decorView.systemUiVisibility= View.INVISIBLE  //quitar la barra de estado (hora, cobertura)

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            window.insetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
//        } else {
//            @Suppress("DEPRECATION")
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
//        }

        var rutaUriVideo = "android.resource://$packageName/${R.raw.video_inicio}"

        binding.videoView.setVideoURI(rutaUriVideo.toUri())
        binding.videoView.start()
        // cuando acabe el vídeo, cerramos esta actividad
        binding.videoView.setOnCompletionListener { mediaPlayer -> finish() }

    }

    fun saltarPresentacion(view: View) {
        finish()
    }

    fun noVolverAMostrar(view: View) {
        val fichero = getSharedPreferences(Constantes.FICHERO_PREFERENCIAS_INICIO, MODE_PRIVATE)
        val editor = fichero.edit()
        editor.putBoolean("SALTAR VIDEO", true)
        editor.apply()
    }


}