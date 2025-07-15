package edu.adf.adfjmg1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
                    this.menuvisible = false
                } else {
                    this.drawerLayout.openDrawer(GravityCompat.START)
                    this.menuvisible = true
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d("MiImcActivity", "Opción ${item.order} seleccionada")
        this.drawerLayout.closeDrawers()
        this.menuvisible = false

        return true
    }


}

