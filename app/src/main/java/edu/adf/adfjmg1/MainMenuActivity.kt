package edu.adf.adfjmg1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import edu.adf.adfjmg1.ejercicio1.Ejercicio1Activity
import edu.adf.adfjmg1.ejercicio2.PrensaDeportivaActivity
import edu.adf.adfjmg1.ejercicio3.TheMemoryActivity
import edu.adf.adfjmg1.canciones.BusquedaCancionesActivity
import edu.adf.adfjmg1.lista.ListaUsuariosActivity
import edu.adf.adfjmg1.perros.PerrosActivity
import edu.adf.adfjmg1.productos.ListaProductosActivity
import edu.adf.adfjmg1.tabs.TabsActivity

/**
 *  ESTA ES LA ACTIVIDAD DE INICIO
 *  DESDE AQUÍ, LANZAMOS EL RESTO DE ACTIVIDADES
 *  EN UN FUTURO, PONDREMOS UN MENÚ HAMBURGUESA / LATERAL
 *  DE MOMENTO, LO HACEMOS CON INTENTS
 *
 * //TODO TERMINAR PRENSA APP X
 * //TODO INFLAR
 * //TODO SERIALIZABLE VS PARCELABLE
 * //TODO hacer que el Usuario pueda seleccionar una FOTo y que se visualice en el IMAGEVIEW
 * //TODO RECYCLERVIEW - listas  LISTVIEW no
 * //TODO HTTP API RETROFIT - PREVIO CORUTINAS KT - COLECCIONES -KT - git hub
 * //TODO FRAGMENTS - VIEWPAGER - TABS
 * //TODO FIREBASE
 * //TODO PERMISOS PELIGROSOS
 * //TODO CÁMARA FOTOS / VIDEO
 * //TODO GPS Y MAPAS // bLUETHOHT¿¿ // NFC dni??
 * //TODO SERVICIOS DEL SISTEMA (DOWNLOAD MANAGER, ALARM MANAGER)
 * //TODO SERVICIOS PROPIOS??
 * //TODO RECIEVERS
 * //TODO PROVIDERS
 * //TODO SQLITE - ROOM
 * //TODO LIVE DATA?
 * //TODO apuntes JETPCK COMPOSE Y MONETIZACIÓN, DISEÑO Y SEGURIDAD
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

        val ficheroInicio = getSharedPreferences(Constantes.FICHERO_PREFERENCIAS_INICIO, MODE_PRIVATE)
        val saltarVideo = ficheroInicio.getBoolean("SALTAR VIDEO", false)
        if (!saltarVideo) {
            val intentvideo = Intent(this, VideoActivity::class.java)
            startActivity(intentvideo)
        }

        // en esta actividad (this) escuchamos la selección sobre el menú Navigation
        //this.navigationView.setNavigationItemSelectedListener(this)
        //this.navigationView.setNavigationItemSelectedListener{false} así sería suficiente

        //TODO tarea opcional: Haced esta función setNavigationItemSelectedListener en versión función anónima

        // CON FUNCIÓN LAMBDA
//        this.navigationView.setNavigationItemSelectedListener{
//            Log.d("MiImcActivity", "Opción ${it.itemId} seleccionada")
//            this.drawerLayout.closeDrawers()
//            this.menuvisible = false
//
//            var intent:Intent = when(it.itemId) {
//                R.id.menuAdivinaNumero -> Intent(this, AdivinaNumeroActivity::class.java)
//                R.id.menuCalculadora -> Intent(this, Calculadora::class.java)
//                R.id.menuCuadros -> Intent(this, CuadrosActivity::class.java)
//                R.id.menuImc -> Intent(this, ImcActivity::class.java)
//                R.id.menuSuma -> Intent(this, SumaActivity::class.java)
//                else -> Intent(this, VersionActivity::class.java)
//            }
//            startActivity(intent)
//            true //en una lambda, no hace falta poner return (de hecho daría error)
//        }

        // CON FUNCIÓN ANÓNIMA
        this.navigationView.setNavigationItemSelectedListener (fun (item: MenuItem): Boolean {
            Log.d("MIAPP_MAINMENU", "Opción ${item.itemId} seleccionada")
            this.drawerLayout.closeDrawers()
            this.menuvisible = false

            var intent:Intent = when(item.itemId) {
                R.id.menuVersiones -> Intent(this, VersionActivity::class.java)
                R.id.menuAdivinaNumero -> Intent(this, AdivinaNumeroActivity::class.java)
                R.id.menuCalculadora -> Intent(this, Calculadora::class.java)
                R.id.menuCuadros -> Intent(this, CuadrosActivity::class.java)
                R.id.menuSuma -> Intent(this, SumaActivity::class.java)
                R.id.menuBusqueda -> Intent(this, BusquedaActivity::class.java)
                R.id.menuEnviarMensaje -> Intent(this, EnviarMensajeActivity::class.java)
                R.id.menuSpinner -> Intent(this, SpinnerActivity::class.java)
                R.id.menuFormulario -> Intent(this, FormularioActivity::class.java)
//                R.id.menuWeb ->
//                    {
//                    val intent = Intent(Intent.ACTION_VIEW, "https://adf-formacion.es/".toUri()) // Intent implícito
//                    Intent.createChooser(intent, "Elige APP para ver ADF WEB")
//                    Intent(this, WebViewActivity::class.java) //intent explícito
//                }
                R.id.menuWeb -> Intent(this, WebViewActivity::class.java)
                R.id.menuEjercicio1 -> Intent(this, Ejercicio1Activity::class.java)
                R.id.menuEjercicio2 -> Intent(this, PrensaDeportivaActivity::class.java)
                R.id.menuEjercicio3 -> Intent(this, TheMemoryActivity::class.java)
                R.id.menuInflar -> Intent(this, InflarActivity::class.java)
                R.id.menuRecycler -> Intent(this, ListaUsuariosActivity::class.java)
                R.id.menuAPI_ListaProductos -> Intent(this, ListaProductosActivity::class.java)
                R.id.menuAPI_ListaPerros -> Intent(this, PerrosActivity::class.java)
                R.id.menuAPI_TabsLayout -> Intent(this, TabsActivity::class.java)
                R.id.menuAPI_Canciones -> Intent(this, BusquedaCancionesActivity::class.java)
                else -> Intent(this, ImcActivity::class.java)
            }
            startActivity(intent) // Voy a otra pantalla6
            return true
        })


//        this.navigationView.setNavigationItemSelectedListener{
//            Log.d("MiImcActivity", "Opción ${it.itemId} seleccionada")
//            this.drawerLayout.closeDrawers()
//            this.menuvisible = false
//
//            var intent:Intent = when(it.itemId) {
//                R.id.menuAdivinaNumero -> Intent(this, AdivinaNumeroActivity::class.java)
//                R.id.menuCalculadora -> Intent(this, Calculadora::class.java)
//                R.id.menuCuadros -> Intent(this, CuadrosActivity::class.java)
//                R.id.menuImc -> Intent(this, ImcActivity::class.java)
//                R.id.menuSuma -> Intent(this, SumaActivity::class.java)
//                else -> Intent(this, VersionActivity::class.java)
//            }
//            startActivity(intent)
//            true //en una lambda, no hace falta poner return (de hecho daría error)
//        }

        this.supportActionBar?.setDisplayHomeAsUpEnabled(true) // dibuja el ícono de menú
        this.supportActionBar?.setHomeAsUpIndicator(R.drawable.outline_menu_24) // le digo que me dibuje la hamburguesa

//        // VAMOS A LANZAR LA ACTIVIDAD IMC
//        val intent = Intent(this, ImcActivity::class.java)
//        startActivity(intent)

    }

    fun intentCompartir()
    {
        val intentEnviarTexto = Intent(Intent.ACTION_SEND) // ENVIAR
        intentEnviarTexto.type = "text/plain" //TIPO MIME - significa de qué tipo es la información
        intentEnviarTexto.putExtra(Intent.EXTRA_TEXT, "Hola desde Android :)")
        startActivity(Intent.createChooser(intentEnviarTexto, "Enviar mensaje con ..."))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            android.R.id.home -> {// Esta clase R no es la de mi proyecto, si no la de Android.
                Log.d("MIAPP_MAINMENU", "Botón Hamburguesa tocado")
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
        Log.d("MIAPP_MAINMENU", "Opción ${item.itemId} seleccionada")
        this.drawerLayout.closeDrawers()
        this.menuvisible = false
        // TODO: Completad el menú lateral y su funciomaniento

        var intent:Intent = when(item.itemId) {
            R.id.menuVersiones -> Intent(this, VersionActivity::class.java)
            R.id.menuAdivinaNumero -> Intent(this, AdivinaNumeroActivity::class.java)
            R.id.menuCalculadora -> Intent(this, Calculadora::class.java)
            R.id.menuCuadros -> Intent(this, CuadrosActivity::class.java)
            R.id.menuSuma -> Intent(this, SumaActivity::class.java)
            R.id.menuWeb -> Intent(this, WebViewActivity::class.java)
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

