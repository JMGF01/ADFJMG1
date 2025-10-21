package edu.adf.adfjmg1.animaciones

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.adf.adfjmg1.R
import kotlin.math.hypot

class RippleYDesaparecerActivity : AppCompatActivity() {

    lateinit var imageView: ImageView
    lateinit var boton:Button
    lateinit var boton2:Button
    private var visible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ripple_ydesaparecer)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        this.imageView = findViewById<ImageView>(R.id.imagenAnimada)
        this.boton = findViewById<Button>(R.id.botonRipple)
        this.boton2 = findViewById<Button>(R.id.botonRipple2)

        this.imageView.visibility = View.INVISIBLE

        this.boton.setOnClickListener{
            if (visible) {
                ocultarImagen()
            } else {
                mostrarImagen()
            }
            visible = !visible
        }

        this.boton2.setOnClickListener {
            //transito a TransitionActivity
            val intentTransicion = Intent(this, TransitionActivity::class.java)
            startActivity(intentTransicion, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            //programáticamente evitamos la transición definida en el tema
            //startActivity(intentTransicion, null)
        }
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add("Efecto Ocultar")
        menu.add("Efecto Mostrar")

        return super.onCreateOptionsMenu(menu)
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.title == "Efecto Ocultar") {
//            ocultarImagen()
//        } else {
//            mostrarImagen()
//        }
//        return super.onOptionsItemSelected(item)
//    }

    fun ocultarImagen()
    {
        val cx = this.imageView.width/2
        val cy = this.imageView.height/2
        val initialRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()
        val anim = ViewAnimationUtils.createCircularReveal(this.imageView,cx,cy,initialRadius,0f)
        anim.duration = 500
        anim.addListener(object: AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator) {
//                super.onAnimationEnd(animation)
                imageView.visibility = View.INVISIBLE
            }
        })
        anim.start()
    }

    fun mostrarImagen() {
        val cx = this.imageView.width/2
        val cy = this.imageView.height/2
        val finalRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()
        val animacion = ViewAnimationUtils.createCircularReveal(this.imageView,cx,cy,0f,finalRadius)
        imageView.visibility = View.VISIBLE
        animacion.duration = 500
        animacion.start()
    }


}