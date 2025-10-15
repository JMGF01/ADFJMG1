package edu.adf.adfjmg1

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.net.toUri
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import edu.adf.adfjmg1.databinding.ActivityFormularioBinding
import java.io.File
import java.io.FileOutputStream

class FormularioActivity : AppCompatActivity() {

    // para lanzar una subactividad (una actividad que me da un resultado)
    lateinit var lanzadorColorFavorito: ActivityResultLauncher<Intent>
    lateinit var lanzadorImagenFormulario: ActivityResultLauncher<Intent>
    lateinit var binding: ActivityFormularioBinding
    var color: Int = 0
    lateinit var usuario:Usuario


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFormularioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // ocultada la barra (actionbar) desde el tema del Manifest específico para esta actividad.
        // TODO gestión automática del checkbox de edad
        // TODO validación / snackBar
        // TODO VideoView + SharedPrefs de saltar intro
        // TODO hacer que el Usuario pueda seleccionar una foto y que se visualice en el ImageView

//        lanzador = registerForActivityResult(
//            ActivityResultContracts.StartActivityForResult(), {
//                it.
//            })

        // dibujamos la barra del menú app bar
        supportActionBar?.show()

        //programamos el menú contextual sobre la imagen
        registerForContextMenu(binding.imagenFormulario)

        //si hay datos en el fichero
        val fichUsuario = getSharedPreferences("usuario", MODE_PRIVATE)
        if (fichUsuario.all.isNotEmpty())
        {
            Log.d("MIAPP_FORMULARIO","El fichero de preferencias 'usuario' EXISTE")
            this.usuario = cargarFichero(fichUsuario)
            //NUEVO Como existe un usuario, queremos mostrar en otra actividad un mensaje de bienvenida
            val intent = Intent(this, BienvenidaActivity::class.java)
            intent.putExtra("USUARIO", usuario)
            startActivity(intent)
        } else {
            Log.d("MIAPP_FORMULARIO","El fichero de preferencias 'usuario' ESTÁ VACÍO")
        }

        // cuando la caja de texto del nombre pierda el foco, se valide
        // y si el nombre no es correcto, me salga en rojo
        binding.editTextNombreFormulario.setOnFocusChangeListener (fun (view: View, tieneFoco: Boolean) {
            if (tieneFoco)
            {
                Log.d(Constantes.ETIQUETA_LOG_FORMULARIO, "la caja del nombre tiene el foco")
            } else {
                Log.d(Constantes.ETIQUETA_LOG_FORMULARIO, "la caja del nombre ha perdido el foco")
                // AUNQUE PARECE QUE FUNCIONA SIN ESO, VER SIGUIENTES LÍNEAS, el problema de las funciones anónimas es que se ejecutan en su propio contexto, por lo que "this" hace referencia al contexto de la función, no a la actividad de fuera, por eso para llamar funciones de fuera hay que hacer la siguiente línea
//                if (!this@FormularioActivity.esNombreValido(this@FormularioActivity.binding.editTextNombreFormulario.text.toString()))
//                    {
//                        this@FormularioActivity.binding.tilnombre.error = "Nombre incorrecto"
//                    } else {
//                        this@FormularioActivity.binding.tilnombre.isErrorEnabled = false
//                }
                if (!esNombreValido(binding.editTextNombreFormulario.text.toString()))
                {
                    binding.tilnombre.error = "Nombre incorrecto"
                } else {
                    binding.tilnombre.isErrorEnabled = false
                }
            }
        })

//        binding.editTextNombreFormulario.setOnFocusChangeListener { view: View, tieneFoco: Boolean ->
//            if (tieneFoco)
//            {
//                Log.d(Constantes.ETIQUETA_LOG_FORMULARIO, "la caja del nombre tiene el foco")
//            } else {
//                Log.d(Constantes.ETIQUETA_LOG_FORMULARIO, "la caja del nombre ha perdido el foco")
//
//                if (!esNombreValido(binding.editTextNombreFormulario.text.toString()))
//                {
//                   binding.tilnombre.error = "Nombre incorrecto"
//                } else {
//                    binding.tilnombre.isErrorEnabled = false
//                }
//            }
//        }

        lanzadorColorFavorito = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult() //lo lque hay entre {} es el segundo parámetro de ActivityResultContracts.StartActivityForResult(), que lo sacamos fuera para que quede mas claro. Lo que lanzo es una actividad
        ){
            // la función que recibe el resultado
            result ->
                if (result.resultCode == Activity.RESULT_OK)
                {
                    Log.d("MIAPP_FORMULARIO","La subactividad ha FINALIZADO BIEN  ${result.resultCode}")
                    var intent_resultado = result.data
                    color = intent_resultado?.getIntExtra("COLOR_ELEGIDO", 0) ?: 0
                    binding.colorFavorito.setBackgroundColor(color)
                } else {
                    Log.d("MIAPP_FORMULARIO","La subactividad ha FINALIZADO BIEN  ${result.resultCode}")
                }

        }

        lanzadorImagenFormulario = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                resultado: ActivityResult ->
            Log.d(Constantes.ETIQUETA_LOG, "Volviendo de la Galeria")
            if (resultado.resultCode == RESULT_OK) {
                Log.d(Constantes.ETIQUETA_LOG, "Volviendo de la Galeria ok")
                Log.d(Constantes.ETIQUETA_LOG, "RUTA FOTO =  ${resultado.data?.data}")
                binding.imagenFormulario.setImageURI(resultado.data?.data)
                binding.imagenFormulario.scaleType = ImageView.ScaleType.CENTER_CROP
                //en el propio ImageView, guardo la uri de la foto, para luego poder guardarla desde la Imagen setTag/getTag


                val urilocal = copiarImagenALocal(resultado.data?.data!!)
                Log.d(Constantes.ETIQUETA_LOG, "RUTA FOTO LOCAL =  ${urilocal}")
                binding.imagenFormulario.tag = urilocal

                //PEDIMOS PERMISOS PERMANTENES PARA ACCEDER A ESA FOTO DE LA GALERÍA DESPIUÉS (EN OTRO PROCESO, AL ARRANCAR EL PROGRAMA OTRA VEZ)
                //ESTA SOLUCIÓN FALLA! EL Content Provider de DOcuimentos no nos concende acceso permanente
                //val takeFlags = resultado.data?.flags!! and Intent.FLAG_GRANT_READ_URI_PERMISSION
                //contentResolver.takePersistableUriPermission(resultado.data?.data!!, takeFlags)


                //TODO probar la versión del detalle thumnail https://developer.android.com/guide/components/intents-common?hl=es-419#GetFile
                //TODO probar escalar la imagen

            } else {
                Log.d(Constantes.ETIQUETA_LOG, "Volviendo de la Galeria MAL")
            }

        }

        /*
        lanzadorImagenFormulario = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            fun (res: ActivityResult) {
            res
        })

        lanzadorImagenFormulario = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            this::aLaVueltaSeleccionFoto) //function referencia

        lanzadorImagenFormulario = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            // void	onActivityResult(result:ActivityResult)
            //it representa activityresult
        }*/

        //TODO mejora para seleccionar una foto de la galería y mostrarla en el imageview de la actividad
        //0) programar el listener onclick sobre imageview
        //1) Lanzar un intent implícito para seleccionar la foto
        //2) tengo que preparar el objeto para lanar el 1) y recibir su respuesta
        //3) a la vuelta, coger la foto y ponerla en el imageView
        //función lambda / flecha
//        this.binding.imagenFormulario.setOnClickListener { imagen ->
//            seleccionarFoto()
//        }
        this.binding.imagenCargarFoto.setOnClickListener{
            seleccionarFoto()
        }

        /*this.binding.imagenFormulario.setOnClickListener {
            seleccionarFoto()
        }
        //función anónima
        this.binding.imagenFormulario.setOnClickListener ( fun (v: View) {
            seleccionarFoto()
        }
        )

        this.binding.imagenFormulario.setOnClickListener (this::seleccionarFoto2)*/

    } // fin OnCreate

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu?.add(Menu.NONE, 1, 1, "Borrar")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        Log.d(Constantes.ETIQUETA_LOG, "MENU TOCADO ${item.title}")
        binding.imagenFormulario.setImageResource(R.drawable.ic_launcher_background)
        return super.onContextItemSelected(item)
    }

    fun seleccionarFoto ()
    {
        //val intentGaleria = Intent(Intent.ACTION_PICK) //intent implicito para ir a la galeria
        val intentGaleria = Intent(Intent.ACTION_GET_CONTENT) //intent implicito para ir a la galeria
        intentGaleria.type = "image/*"

        if (intentGaleria.resolveActivity(packageManager)!=null)
        {
            Log.d(Constantes.ETIQUETA_LOG, "SÍ HAY una APP de GALERIA")


            lanzadorImagenFormulario.launch(intentGaleria)
        } else {
            Log.d(Constantes.ETIQUETA_LOG, "NO HAY APP de GALERÍA")
        }


    }
    fun seleccionarFoto2 (v: View)
    {

    }


    fun aLaVueltaSeleccionFoto (resultado: ActivityResult): Unit
    {

    }


    fun aLaVueltaSeleccionFoto2 (resultado: Intent): Unit
    {

    }

    /**
     * carga los datos existentes en el fichero SharedPreferences "usuario"
     * en el formulario.
     * @param fichUsuario El fichero SharedPreferences
     */
    private fun cargarFichero(fichUsuario: SharedPreferences): Usuario
    {
        val nombre: String = fichUsuario.getString("nombre", "") ?: ""
        binding.editTextNombreFormulario.setText(nombre)
        val edad: Int = fichUsuario.getInt("edad", 0) ?: 0
        binding.editTextEdadFormulario.setText(fichUsuario.getInt("edad", 0).toString())

        val sexo: Char = fichUsuario.getString("sexo", "M")!!.get(0)
        when (sexo) {
            'M' -> binding.radioButtonHombre.isChecked = true
            'F' -> binding.radioButtonMujer.isChecked = true
        }

        val colorFavorito: Int = fichUsuario.getInt("color", 0)
        binding.colorFavorito.setBackgroundColor(colorFavorito)
        val mayorEdad: Boolean = fichUsuario.getBoolean("mayorEdad", false)
        binding.checkBox.isChecked = mayorEdad

        val uriFoto = fichUsuario.getString("uriFoto", "")
        if (uriFoto!="")
        {
            binding.imagenFormulario.setImageURI(uriFoto?.toUri())
            binding.imagenFormulario.scaleType = ImageView.ScaleType.CENTER_CROP
        }

//        val usuarioFichero: Usuario = Usuario(nombre, edad, sexo, mayorEdad, colorFavorito)
        val usuarioFichero = Usuario(nombre!!, edad, sexo, mayorEdad, color, uriFoto!!)

        val intent:Intent = Intent(this, BienvenidaActivity::class.java)
        intent.putExtra("usuario", usuarioFichero)
        startActivity(intent)

        return usuarioFichero
    }

    fun seleccionarColorFavorito(view: View)
    {
        // DEBEMOS LANZAR LA OTRA ACTIVIDAD SUBCOLOR ACTIVITY, PERO
        val intent = Intent(this, SubColorActivity::class.java)
        //startActivity(intent)
        //startActivityForResult(intent, 99) // es como se hacía antiguamente, está deprecado, ya no se hace así
        lanzadorColorFavorito.launch(intent) // aquí lanzo la subactividad
    }

    fun mostrarInfoFormulario(view: View) {
        //mostrar los datos del formulario
        Log.d("MIAPP_FORMULARIO", "NOMBRE = ${binding.editTextNombreFormulario.text.toString()} EDAD = ${binding.editTextEdadFormulario.text.toString()} HOMBRE = ${binding.radioButtonHombre.isChecked} MUJER = ${binding.radioButtonMujer.isChecked} MAYOR EDAD = ${binding.checkBox.isChecked}")

        val nombre: String = binding.editTextNombreFormulario.text.toString()
        val edad: Int = binding.editTextEdadFormulario.text.toString().toInt()
        val sexo: Char = if (binding.radioButtonHombre.isChecked)
        {
            'M'//'M' o 'F'
        } else if (binding.radioButtonMujer.isChecked) {
            'F'
        } else {
            'M'
        }

        val mayorEdad: Boolean = binding.checkBox.isChecked
        val uriFoto = if (binding.imagenFormulario.tag == null)
        {
            "" //no tiene foto
        } else
        {
            binding.imagenFormulario.tag as Uri //sí tiene foto
        }
        val usuario: Usuario = Usuario(nombre, edad, sexo, mayorEdad, color)
        Log.d("MIAPP_FORMULARIO", "USUARIO = $usuario")
        guardarUsuario(usuario)

        // mostramos el SNACKBAR -> Mensaje guardado.
        val snackbar = Snackbar.make(binding.main, "USUARIO GUARDADO", BaseTransientBottomBar.LENGTH_LONG) // recibe la vista donde ejecutarlo, el mensaje, y la duración (que hay que coger de la clase BaseTransientBottomBar)
        snackbar.setAction("DESHACER") {v:View ->
            Log.d(Constantes.ETIQUETA_LOG_FORMULARIO, "HA TOCADO DESHACER")
            borrarUsuario()
        }
        //snackbar.setTextColor(getColor(R.color.miRojo)) // el color de la acción
        snackbar.show()

    }


    /**
     *
    versión antigua
     */
    /*
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        caller: ComponentCaller
    ) {
        super.onActivityResult(requestCode, resultCode, data, caller)
        //obtenía el resultado
    }

     */

    fun guardarUsuario(usuario: Usuario)
    {
        // 1º accedo al fichero (se crea automáticamente si no existe)
        val ficheroUsuario = getSharedPreferences("usuario", MODE_PRIVATE)
        val editor = ficheroUsuario.edit() // obtengo un editor para poder escribir a través de él en el fichero.
        editor.putString("nombre", usuario.nombre)
        editor.putInt("edad", usuario.edad)
//        editor.putString("sexo", ""+usuario.sexo) //si sumo cadena vacía lo convierto a string, pero es un poco "gitano"
        editor.putString("sexo", usuario.sexo.toString())
        editor.putInt("color", usuario.colorFavorito)
        editor.putBoolean("mayorEdad", usuario.esMayorEdad)
        editor.putString("uriFoto", usuario.uriFoto)
        editor.apply() // o commit - guardo los cambios de verdad en el fichero - se confirma
        editor.commit()
    }

    /**
     * Borra el fichero de preferencias USUARIO
     */
    fun borrarUsuario()
    {
        val ficheroUsuario = getSharedPreferences(Constantes.FICHERO_PREFERENCIAS, MODE_PRIVATE)
        //ficheroUsuario.edit().clear().apply()
        ficheroUsuario.edit (true){ clear() } //a la función edit le puedes pasar un parámetro que indique si hace commit al terminar

        //versión alternativa "tradicional"
        //val editor =  fichero.edit()
        //editor.clear()
        //editor.commit()
        Log.d(Constantes.ETIQUETA_LOG_FORMULARIO, "FICHERO USUARIO BORRADO")
    }

    fun esNombreValido (nombre:String): Boolean
    {
//        var nombreValido: Boolean = false // declaro e inicializo la variable

//        nombreValido = if (nombre.length > 2)
//        {
//            true
//        } else {
//            false
//        }
//        nombreValido = (nombre.length > 2)

//        return nombreValido

        return nombre.length > 2
    }

    fun borrarUsuarioPrefs(view: View)
    {
        borrarUsuario()
    }

    fun copiarImagenALocal(uri: Uri): Uri {
        val archivoGaleria = contentResolver.openInputStream(uri)
        val nombreArchivo = "imagen_formulario_perfil.jpg"
        val archivoNuevoSalida = File(filesDir, nombreArchivo)
        val outputStream = FileOutputStream(archivoNuevoSalida)

        archivoGaleria?.copyTo(outputStream)

        archivoGaleria?.close()
        outputStream.close()

        return Uri.fromFile(archivoNuevoSalida) // este sí puedes guardar y reutilizar
    }

}