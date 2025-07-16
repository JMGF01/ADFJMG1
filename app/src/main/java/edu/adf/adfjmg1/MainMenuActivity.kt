package edu.adf.adfjmg1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

/**
 *  ESTA ES LA ACTIVIDAD DE INICIO
 *  DESDE AQUÍ, LANZAMOS EL RESTO DE ACTIVIDADES
 *  EN UN FUTURO, PONDREMOS UN MENÚ HAMBURGUESA / LATERAL
 *  DE MOMENTO, LO HACEMOS CON INTENTS
 */
class MainMenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    var menuvisible: Boolean = false // Se usa para controlar el estado del menú de la aplicación.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main_menu)

        this.drawerLayout =  findViewById<DrawerLayout>(R.id.drawer)
        this.navigationView = findViewById<NavigationView>(R.id.navigationView)

        // en esta actividad (this) escuchamos la selección sobre el menú Navigation
        this.navigationView.setNavigationItemSelectedListener(this)

        this.supportActionBar?.setDisplayHomeAsUpEnabled(true) // dibuja el ícono de menú
        this.supportActionBar?.setHomeAsUpIndicator(R.drawable.outline_menu_24) // le digo que me dibuje la hamburguesa

//        // VAMOS A LANZAR LA ACTIVIDAD IMC
//        val intent = Intent(this, ImcActivity::class.java)
//        startActivity(intent)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            android.R.id.home -> {// Esta clase R no es la de mi proyecto, si no la de Android.
                Log.d("MiImcActivity", "Botón Hamburguesa tocado")
                if (this.menuvisible) {
                    this.drawerLayout.closeDrawers()
//                    this.menuvisible = false
                } else {
                    this.drawerLayout.openDrawer(GravityCompat.START)
//                    this.menuvisible = true
                }
                this.menuvisible = !this.menuvisible
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d("MiImcActivity", "Opción ${item.itemId} seleccionada")
        this.drawerLayout.closeDrawers()
        this.menuvisible = false
        // TODO: Completad el menú lateral y su funciomaniento

        var intent:Intent = when(item.itemId) {
            R.id.menuVersiones -> Intent(this, VersionActivity::class.java)
            R.id.menuAdivinaNumero -> Intent(this, AdivinaNumeroActivity::class.java)
            R.id.menuCalculadora -> Intent(this, Calculadora::class.java)
            R.id.menuCuadros -> Intent(this, CuadrosActivity::class.java)
            R.id.menuSuma -> Intent(this, SumaActivity::class.java)
            else -> Intent(this, ImcActivity::class.java)
        }
        startActivity(intent) // Voy a otra pantalla

//        // Versión mas avanzada usando genéricos
//        var objeto:Class<out Activity> = when(item.order) {
//            2 -> VersionActivity::class.java
//            3 -> AdivinaNumeroActivity::class.java
//            4 -> Calculadora::class.java
//            5 -> CuadrosActivity::class.java
//            else -> ImcActivity::class.java
//        }
//        val miIntent: Intent = Intent(this, objeto)
//        startActivity(miIntent)

        return true
    }


}

