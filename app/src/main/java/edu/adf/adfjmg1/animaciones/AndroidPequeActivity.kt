package edu.adf.adfjmg1.animaciones

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.adf.adfjmg1.R

class AndroidPequeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_android_peque)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imagenP = findViewById<ImageView>(R.id.miImagenP)
        imagenP.setOnClickListener{
            //transitamos a la actividad grande
            val intent = Intent(this, AndroidGrandeActivity::class.java)
            val options = ActivityOptions.makeSceneTransitionAnimation(this, imagenP, "roboto")
            startActivity(intent, options.toBundle())
        }
    }

}