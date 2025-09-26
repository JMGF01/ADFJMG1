package edu.adf.adfjmg1

import android.annotation.TargetApi
import android.content.ComponentName
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import edu.adf.adfjmg1.alarma.GestorAlarma
import edu.adf.adfjmg1.ejercicio1.Ejercicio1Activity
import edu.adf.adfjmg1.ejercicio2.PrensaDeportivaActivity
import edu.adf.adfjmg1.ejercicio3.TheMemoryActivity
import edu.adf.adfjmg1.canciones.BusquedaCancionesActivity
import edu.adf.adfjmg1.contactos.SeleccionContactoActivity
import edu.adf.adfjmg1.contactos.SeleccionContactoPermisosActivity
import edu.adf.adfjmg1.descargarcanciones.DescargarCancionActivity
import edu.adf.adfjmg1.foto.FotoActivity
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
 * //TODO INFLAR X
 * //TODO SERIALIZABLE VS PARCELABLE X
 * //TODO hacer que el Usuario pueda seleccionar una FOTo y que se visualice en el IMAGEVIEW X
 * //TODO RECYCLERVIEW - listas  LISTVIEW no X
 * //TODO HTTP API RETROFIT - PREVIO CORUTINAS KT - COLECCIONES -KT - git hub X
 * //TODO FRAGMENTS - VIEWPAGER - TABS X
 * //TODO NOTIFICACIONES - PENDING INTENT
 * //TODO FIREBASE
 * //TODO PERMISOS PELIGROSOS X
 * //TODO CÁMARA FOTOS / VIDEO
 * //TODO GPS Y MAPAS // bLUETHOHT¿¿ // NFC dni??
 * //TODO SERVICIOS DEL SISTEMA (DOWNLOAD MANAGER, ALARM MANAGER)
 * //TODO SERVICIOS PROPIOS??
 * //TODO RECIEVERS
 * //TODO PROVIDERS X
 * //TODO SQLITE - ROOM
 * //TODO LIVE DATA?
 * //TODO apuntes JETPCK COMPOSE Y MONETIZACIÓN, DISEÑO Y SEGURIDAD
 */
class MainMenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    var menuvisible: Boolean = false // Se usa para controlar el estado del menú de la aplicación.
    var launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        Log.d(Constantes.ETIQUETA_LOG, "Volviendo de Ajustes Autonicio")
        val ficherop = getSharedPreferences("ajustes", MODE_PRIVATE)
        ficherop.edit().putBoolean("INICIO_AUTO", true).commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main_menu)

//        this.drawerLayout =  findViewById<DrawerLayout>(R.id.drawer)
//        this.navigationView = findViewById<NavigationView>(R.id.navigationView)

        mostrarAPPSinstaladas()
        gestionarPermisosNotis ()
        lanzarAlarma()

        val ficherop = getSharedPreferences("ajustes", MODE_PRIVATE)
        val inicio_auto = ficherop.getBoolean("INICIO_AUTO", false)
        if (!inicio_auto) {
            solicitarInicioAutomatico()
        }

        this.drawerLayout =  findViewById<DrawerLayout>(R.id.drawer)
        this.navigationView = findViewById<NavigationView>(R.id.navigationView)

        // en esta actividad (this) escuchamos la selección sobre el menú Navigation
        //this.navigationView.setNavigationItemSelectedListener(this)
        //this.navigationView.setNavigationItemSelectedListener{false} así sería suficiente

        val ficheroInicio = getSharedPreferences(Constantes.FICHERO_PREFERENCIAS_INICIO, MODE_PRIVATE)
        val saltarVideo = ficheroInicio.getBoolean("SALTAR VIDEO", false)
        if (!saltarVideo) {
            val intentvideo = Intent(this, VideoActivity::class.java)
            startActivity(intentvideo)
        }




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
                R.id.menuContacto -> Intent(this, SeleccionContactoActivity::class.java)
                R.id.menuContactoPermiso -> Intent(this, SeleccionContactoPermisosActivity::class.java)
                R.id.menuFoto -> Intent(this, FotoActivity::class.java)
                R.id.menuDescarga -> Intent(this, DescargarCancionActivity::class.java)
                else -> Intent(this, ImcActivity::class.java)
            }
            startActivity(intent) // Voy a otra pantalla
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

    private fun solicitarInicioAutomatico() {
        val manufacturer = Build.MANUFACTURER
        try {
            val intent = Intent()
            if ("xiaomi".equals(manufacturer, ignoreCase = true)) {
                intent.setComponent(
                    ComponentName(
                        "com.miui.securitycenter",
                        "com.miui.permcenter.autostart.AutoStartManagementActivity"
                    )
                )
            }

            launcher.launch(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun mostrarAPPSinstaladas()
    {
        val packageManager = packageManager
        val apps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

        Log.d(Constantes.ETIQUETA_LOG, "Número apps: ${apps.size}")
        for (app in apps.sortedBy { it.packageName }) {
            Log.d(Constantes.ETIQUETA_LOG, "Package: ${app.packageName}, Label: ${packageManager.getApplicationLabel(app)}")
        }

    }

    fun gestionarPermisosNotis()
    {
        val areNotificationsEnabled = NotificationManagerCompat.from(this).areNotificationsEnabled()

        if (!areNotificationsEnabled) {
            // Mostrar un diálogo al usuario explicando por qué necesita habilitar las notificaciones
            mostrarDialogoActivarNotis()
        }
        else {
            Log.d(Constantes.ETIQUETA_LOG, "Notis desactivadas")
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun mostrarDialogoActivarNotis() {
        var dialogo = AlertDialog.Builder(this)
            .setTitle("AVISO NOTIFICACIONES") //i18n
            //.setTitle("AVISO")
            .setMessage("Para que la app funcione, debe ir a ajustes y activar las notificaciones")
            //.setMessage("¿Desea Salir?")
            .setIcon(R.drawable.outline_notifications_24)
            .setPositiveButton("IR"){ dialogo, opcion ->
                Log.d(Constantes.ETIQUETA_LOG, "Opción positiva salir =  $opcion")
                val intent = Intent().apply {
                    action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                    putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                }
                startActivity(intent)

            }
            .setNegativeButton("CANCELAR"){ dialogo: DialogInterface, opcion: Int ->
                Log.d(Constantes.ETIQUETA_LOG, "Opción negativa  =  $opcion")
                dialogo.dismiss()
            }


        dialogo.show()//lo muestro
    }

    fun lanzarAlarma() {
        GestorAlarma.programarAlarma(this)
    }

}

