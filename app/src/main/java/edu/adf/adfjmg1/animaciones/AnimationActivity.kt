package edu.adf.adfjmg1.animaciones

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import edu.adf.adfjmg1.R

class AnimationActivity : AppCompatActivity() {

    lateinit var imagen1:ImageView
    lateinit var imagen2:ImageView
    lateinit var layoutPadre:LinearLayout
    lateinit var animacion:Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)

        this.imagen1 = findViewById<ImageView>(R.id.imagen1anim)
        this.imagen2 = findViewById<ImageView>(R.id.imagen2anim)
        this.layoutPadre = findViewById<LinearLayout>(R.id.layout_padre)

        this.imagen1.setImageResource(R.drawable.memory_perro)
        this.imagen2.setImageResource(R.drawable.memory_tigre)

        this.animacion = AnimationUtils.loadAnimation(this, R.anim.animacion)
        this.animacion.reset()
        this.layoutPadre.startAnimation(this.animacion)
        // TODO completar los listener de animación
    }
}