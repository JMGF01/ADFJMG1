package edu.adf.adfjmg1

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.adf.adfjmg1.databinding.ActivityFormularioBinding
import java.util.zip.Inflater

class FormularioActivity : AppCompatActivity() {

    // para lanzar una subactividad (una actividad que me da un resultado)
    lateinit var lanzador: ActivityResultLauncher<Intent>
    lateinit var binding: ActivityFormularioBinding
    var color: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFormularioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // ocultada la barra (actionbar) desde el tema del Manifest
        // TODO gestión automática del checkbox de edad
        // TODO validación / snackBar
        // TODO VideoView + SharedPrefs de saltar intro
        // TODO hacer que el Usuario pueda seleccionar una foto y que se visualice en el ImageView

//        lanzador = registerForActivityResult(
//            ActivityResultContracts.StartActivityForResult(), {
//                it.
//            })

        val fichUsuario = getSharedPreferences("usuario", MODE_PRIVATE)
        if (fichUsuario.all.isNotEmpty())
        {
            Log.d("MIAPP_FORMULARIO","El fichero de preferencias 'usuario' EXISTE")
            cargarFichero(fichUsuario)
        } else {
            Log.d("MIAPP_FORMULARIO","El fichero de preferencias 'usuario' ESTÁ VACÍO")
        }

        lanzador = registerForActivityResult(
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
    }

    /**
     * carga los datos existentes en el fichero SharedPreferences "usuario"
     * en el formulario.
     * @param fichUsuario El fichero SharedPreferences
     */
    private fun cargarFichero(fichUsuario: SharedPreferences)
    {
        binding.editTextNombreFormulario.setText(fichUsuario.getString("nombre", ""))
        binding.editTextEdadFormulario.setText(fichUsuario.getInt("edad", 0).toString())

        when (fichUsuario.getString("sexo", "M")) {
            "M" -> binding.radioButtonHombre.isChecked = true
            "F" -> binding.radioButtonMujer.isChecked = true
        }

        binding.colorFavorito.setBackgroundColor(fichUsuario.getInt("color", 0))
        binding.checkBox.isChecked = fichUsuario.getBoolean("mayorEdad", false)
    }

    fun seleccionarColorFavorito(view: View)
    {
        // DEBEMOS LANZAR LA OTRA ACTIVIDAD SUBCOLOR ACTIVITY, PERO
        val intent = Intent(this, SubColorActivity::class.java)
        //startActivity(intent)
        //startActivityForResult(intent, 99) // es como se hacía antiguamente, está deprecado, ya no se hace así
        lanzador.launch(intent) // aquí lanzo la subactividad
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
        val usuario: Usuario = Usuario(nombre, edad, sexo, mayorEdad, color)
        Log.d("MIAPP_FORMULARIO", "USUARIO = $usuario")
        guardarUsuario(usuario)
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
        editor.apply() // o commit - guardo los cambios de verdad en el fichero - se confirma
    }

}