package edu.adf.adfjmg1

import android.Manifest
import android.animation.ObjectAnimator
import android.annotation.TargetApi
import android.content.ComponentName
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.messaging.FirebaseMessaging
import edu.adf.adfjmg1.alarma.AjusteAlarmaActivity
import edu.adf.adfjmg1.alarma.GestorAlarma
import edu.adf.adfjmg1.authfirebase.MenuAuthActivity
import edu.adf.adfjmg1.basedatos.BaseDatosActivity
import edu.adf.adfjmg1.biometrico.BioActivity
import edu.adf.adfjmg1.canciones.BusquedaCancionesActivity
import edu.adf.adfjmg1.contactos.SeleccionContactoActivity
import edu.adf.adfjmg1.contactos.SeleccionContactoPermisosActivity
import edu.adf.adfjmg1.descargarcanciones.DescargarCancionActivity
import edu.adf.adfjmg1.ejercicio1.Ejercicio1Activity
import edu.adf.adfjmg1.ejercicio2.PrensaDeportivaActivity
import edu.adf.adfjmg1.ejercicio3.TheMemoryActivity
import edu.adf.adfjmg1.fechayhora.SeleccionFechaYHoraActivity
import edu.adf.adfjmg1.foto.FotoActivity
import edu.adf.adfjmg1.googleauth.GoogleAuthActivity
import edu.adf.adfjmg1.lista.ListaUsuariosActivity
import edu.adf.adfjmg1.mapa.MapsActivity
import edu.adf.adfjmg1.perros.PerrosActivity
import edu.adf.adfjmg1.productos.ListaProductosActivity
import edu.adf.adfjmg1.realtimedatabase.InsertarClientesFirebaseActivity
import edu.adf.adfjmg1.servicios.PlayActivity
import edu.adf.adfjmg1.tabs.TabsActivity
import edu.adf.adfjmg1.util.LogUtil
import edu.adf.adfjmg1.worker.MiTareaProgramada
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

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
 * //TODO FIREBASE (auth y bd) --> falta revisar el delete por campo no clave y el update
 * //TODO FIREBASE messagin --> notificaciones por servidor
 * //TODO FIREBASE login con Cuenta de GOOGLE
 * //TODO PERMISOS PELIGROSOS X
 * //TODO CÁMARA FOTOS / VIDEO
 * //TODO Autenticación Biométrica/PIN x
 * //TODO GPS Y MAPAS // bLUETHOHT¿¿ // NFC dni??
 * //TODO SERVICIOS DEL SISTEMA (DOWNLOAD MANAGER, ALARM MANAGER)
 * //TODO SERVICIOS PROPIOS started service / foreground service / intent service / binded
 * //TODO RECIEVERS
 * //TODO PROVIDERS X
 * //TODO SQLITE - ROOM --> añadir más entidades a nuestro esquema
 * //TODO SQLITE - ROOM -->  RELACIÓN N:m? listener único para recycler? mostrarCoches en otra actividad/fragment? / STATEFLOW? / inserte coches?
 * //TODO LIVE DATA?
 * //TODO apuntes JETPCK COMPOSE Y MONETIZACIÓN, DISEÑO Y SEGURIDAD
 * //TODO personalizar Fuentes tipos de letra
 * //TODO DESPROGRAMAR ALARMA
 * //TODO CalendarPicker y TimePicker
 * //TODO firma y PUBLICAR APPS
 * //TODO themebuilder material / colores / diseñar el tema
 * //TODO proyecto API MAPA no de google con consulta al API de clima en varios módulos
 * //TODO FLOW  vs LiveData
 * //TODO bLUETHOHT¿¿ // NFC dni??
 * //TODO VISTAS JETPACK COMPOSE
 * //TODO splash screen - pantalla de inicio
 * //TODO transiones / animaciones
 * //TODO APp shortcuts
 */

class MainMenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    var menuVisible: Boolean = false // Se usa para controlar el estado del menú de la aplicación.
    var launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        Log.d(Constantes.ETIQUETA_LOG, "Volviendo de Ajustes Autonicio")
        val ficherop = getSharedPreferences("ajustes", MODE_PRIVATE)
//        ficherop.edit().putBoolean("INICIO_AUTO", true).commit()
        ficherop.edit(true){
            putBoolean("INICIO_AUTO", true)
            putBoolean("ALARMA", false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme) // Actualizamos el tema al tema normal (eliminamos el usado para la splash screen en versiones anteriores)
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main_menu)

//        this.drawerLayout =  findViewById<DrawerLayout>(R.id.drawer)
//        this.navigationView = findViewById<NavigationView>(R.id.navigationView)

        //VER COMENTARIOS estas dos funciones sobre Splash Screen
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        {
            Log.d(Constantes.ETIQUETA_LOG, "Estoy en V12 o superior")
            retardoModerno()
        } else {
            Log.d(Constantes.ETIQUETA_LOG, "Estoy en V12 o superior")
            retardoAntiguo()
            animacionSalidaSplash()
        }

        val ficherop = getSharedPreferences("ajustes", MODE_PRIVATE)
        val inicio_auto = ficherop.getBoolean("INICIO_AUTO", false)
        if (!inicio_auto) {
            //PRIMERA VEZ
            solicitarInicioAutomatico()
            askNotificationPermission() // por notificaciones firebase
        }

        this.drawerLayout =  findViewById<DrawerLayout>(R.id.drawer)
        this.navigationView = findViewById<NavigationView>(R.id.navigationView)

        // en esta actividad (this) escuchamos la selección sobre el menú Navigation
        //this.navigationView.setNavigationItemSelectedListener(this)
        //this.navigationView.setNavigationItemSelectedListener{false} así sería suficiente

        // intentCompartir()
        val ficheroInicio = getSharedPreferences(Constantes.FICHERO_PREFERENCIAS_INICIO, MODE_PRIVATE)
        val saltarVideo = ficheroInicio.getBoolean("SALTAR VIDEO", false)
        if (!saltarVideo) //==false
        {
            val intentvideo = Intent(this, VideoActivity::class.java)
            startActivity(intentvideo)
        }
//        mostrarAPPSinstaladas()
        gestionarPermisosNotis ()
//        lanzarAlarma() // Ahora la lanzamos cuando se ejecute el servicio, una vez se reinicie el móvil.
        lanzarWorkManager()


        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(Constantes.ETIQUETA_LOG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            } else {
                // Get new FCM registration token
                val token = task.result

                // Log and toast
                val msg = "TOKEN CREADO PARA NOTIFICACIONES = $token"// getString(R.string.msg_token_fmt, token)
                Log.d(Constantes.ETIQUETA_LOG, "${LogUtil.getLogInfo()}  $msg")
                // Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                //Log.d(Constantes.ETIQUETA_LOG, "Token registro FBCM $token")
            }


        })


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

            var intent:Intent = when(item.itemId) {
//                R.id.menuVersiones -> Intent(this, VersionActivity::class.java)
                R.id.menuAdivinaNumero -> Intent(this, AdivinaNumeroActivity::class.java)
                R.id.menuImc -> Intent(this, ImcActivity::class.java)
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
                R.id.menuBioActivity -> Intent(this, BioActivity::class.java)
                R.id.menuMapa -> Intent(this, MapsActivity::class.java)
                R.id.menuPlay -> Intent(this, PlayActivity::class.java)
                R.id.menuAlarma -> Intent(this, AjusteAlarmaActivity::class.java)
                R.id.menuFechaHora -> Intent(this, SeleccionFechaYHoraActivity::class.java)
                R.id.menuBaseDatos -> Intent(this, BaseDatosActivity::class.java)
                R.id.menuLogin -> Intent(this, MenuAuthActivity::class.java)
                R.id.menuBBDD -> Intent(this, InsertarClientesFirebaseActivity::class.java)
                R.id.menuGoogleAUTH -> Intent(this, GoogleAuthActivity::class.java)
//                else -> Intent(this, ImcActivity::class.java)
                else -> Intent(this, VersionActivity::class.java)
            }
            startActivity(intent) // Voy a otra pantalla

            this.drawerLayout.closeDrawers()
            this.menuVisible = false

            return true //en una lambda, no hace poner return
        })

// con función lambda
//        this.navigationView.setNavigationItemSelectedListener{
//            Log.d("MiImcActivity", "Opción ${it.itemId} seleccionada")
//
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

        //dibujamos con fuente iconográfica
        val fuente = Typeface.createFromAsset(assets, "fuentepatas.ttf")
        val mensaje = findViewById<TextView>(R.id.logopatas)
        mensaje.typeface = fuente
    }

//    fun intentCompartir()
//    {
//        val intentEnviarTexto = Intent(Intent.ACTION_SEND) // ENVIAR
//        intentEnviarTexto.type = "text/plain" //TIPO MIME - significa de qué tipo es la información
//        intentEnviarTexto.putExtra(Intent.EXTRA_TEXT, "Hola desde Android :)")
//        startActivity(Intent.createChooser(intentEnviarTexto, "Enviar mensaje con ..."))
//    }

    //este métod o se invoca al tocar la hamburguesa
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            android.R.id.home -> {// Esta clase R no es la de mi proyecto, si no la de Android.
                Log.d("MIAPP_MAINMENU", "Botón Hamburguesa tocado")
                if (this.menuVisible) {
                    this.drawerLayout.closeDrawers()
//                    this.menuvisible = false
                } else {
                    this.drawerLayout.openDrawer(GravityCompat.START)
//                    this.menuvisible = true
                }
                this.menuVisible = !this.menuVisible
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d("MIAPP_MAINMENU", "Opción ${item.itemId} seleccionada")

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

        this.drawerLayout.closeDrawers()
        this.menuVisible = false
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
//        for (app in apps.sortedBy { it.packageName }) {
//            Log.d(Constantes.ETIQUETA_LOG, "Package: ${app.packageName}, Label: ${packageManager.getApplicationLabel(app)}")
//        }
        //ordeno por paquete
        val appOrdenadas = apps.sortedBy { it.packageName }
        appOrdenadas.forEach {
            Log.d("AppInfo", "Package: ${it.packageName}, Label: ${packageManager.getApplicationLabel(it)}")
            // Log.d("AppInfo", "$it")
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

    fun lanzarWorkManager ()
    {
        //definimos restricciones
        val constraints = Constraints.Builder()
            //.setRequiredNetworkType(NetworkType.UNMETERED) // solo Wi-Fi
            //.setRequiresBatteryNotLow(true)                // no ejecutar con batería baja
            //.setRequiresCharging(true)                     // solo cuando esté cargando
            .build()

        //pasamos datos de entrada
        val inputData = workDataOf("USER_ID" to "12345")

        //creamos el trabajo periódico (la petición) con los datos y restricciones anteriores
        val periodicWorkRequest = PeriodicWorkRequestBuilder<MiTareaProgramada>(
            15, TimeUnit.MINUTES // Periodicidad mínima: 15 minutos
        )
            .setConstraints(constraints)
            .setInputData(inputData)
            .build()

        //encolamos la petición
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "MiTareaProgramada",                       // Nombre único
                ExistingPeriodicWorkPolicy.KEEP,        // No reemplazar si ya existe
                periodicWorkRequest
            )

        val tiempo = System.currentTimeMillis()+(60*1000*15)//(30*1000)//15 minutos
        val dateformatter = SimpleDateFormat("E dd/MM/yyyy ' a las ' hh:mm:ss")
        val mensaje = dateformatter.format(tiempo)
        Log.d(Constantes.ETIQUETA_LOG, "ALARMA PROGRAMADA PARA $mensaje")
        Toast.makeText(this, "Alarma programada para $mensaje", Toast.LENGTH_LONG).show()

        /**
         * parece QUE NO se ejecutan las taresas programadas tras el reinicio del móvil
         * se puedo probar esto:
         *
         * tener mi clase application, con este conetenido, registrada en el manifest
         *
         * class MyApp : Application(), Configuration.Provider {
         *     override fun getWorkManagerConfiguration(): Configuration {
         *         return Configuration.Builder()
         *             .setMinimumLoggingLevel(Log.DEBUG)
         *             .build()
         *     }
         * }
         *
         * <application
         *     android:name=".MyApp"
         *     ...>
         * </application>
         *
         */

    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
            Log.d(Constantes.ETIQUETA_LOG, "Permisos notificaciones concedidos")
        } else {
            // TODO: Inform user that that your app will not show notifications.
            Log.d(Constantes.ETIQUETA_LOG, "Permisos notificaciones NO concedidos")
        }
    }

//    private fun askNotificationPermission() {
//        // This is only necessary for API level >= 33 (TIRAMISU)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
//                PackageManager.PERMISSION_GRANTED
//            ) {
//                // FCM SDK (and your app) can post notifications.
//            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
//                // TODO: display an educational UI explaining to the user the features that will be enabled
//                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
//                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
//                //       If the user selects "No thanks," allow the user to continue without notifications.
//            } else {
//                // Directly ask for the permission
//                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
//            }
//        }
//    }


    /**
     * Método para preguntar por notificaciones para firebase a partir de la versión 13 Tiramisú
     */
    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    /**
    Por defecto, cuando ya se ha dibujado la Actividad Principal, la Splash Screen
    desaparece. Sin emabargo, al programar esta función Predraw no se pinta ningún
    fotograma, hasta que esta función no devuelta true. Por ejemplo
    en este caso, estamos causando un retardo de 6 segundos y hasta que no acabe
    la actividad no empieza a pintarse y mientras, se ve sólo la Splash Screen
     */
    fun retardoAntiguo ()
    {
        // Set up an OnPreDrawListener to the root view.
        //OJO android.R.id.content apunta al FrameLayout que contiene toda la interfaz de tu Activity.
        //Ese content existe siempre, todos nuestros layouts montan en este Frame y sigue estando en JetPack Compose
        val content = findViewById<View>(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // Check whether the initial data is ready.
                    Thread.sleep(6000)
                    return if (true) {
                        // The content is ready. Start drawing.
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        // The content isn't ready. Suspend.
                        false
                    }
                }
            })
    }

    fun retardoModerno ()
    {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            Thread.sleep(5000)
            true }
    }

    /**
     * La salidad de la SplashScreen, puede ser animada. De modo, que podemos
     * definir un listener al finalizar su tiempo y cargar una animación
     * como ésta
     */
    fun animacionSalidaSplash ()
    {
        //sólo para versiones anteriores
        //también podría obtener la instancia con val splashScreen = installSplashScreen()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                // Create your custom animation.
                val slideUp = ObjectAnimator.ofFloat(
                    splashScreenView,
                    View.TRANSLATION_X,
                    0f,
                    -splashScreenView.width.toFloat()
                )
                slideUp.interpolator = AnticipateInterpolator()
                slideUp.duration = 20000L

                // Call SplashScreenView.remove at the end of your custom animation.
                slideUp.doOnEnd { splashScreenView.remove() }

                // Run your animation.
                slideUp.start()
            }
        }
        /*
        * splashScreen.setOnExitAnimationListener { splashScreenView ->
    splashScreenView.iconView.animate()
        .alpha(0f)
        .setDuration(300L)
        .withEndAction {
            splashScreenView.remove()
        }
}*/
    }

}

