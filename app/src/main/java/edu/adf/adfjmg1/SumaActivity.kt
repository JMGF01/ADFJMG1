package edu.adf.adfjmg1

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.adf.adfjmg1.databinding.ActivitySumaBinding

class SumaActivity : AppCompatActivity() {

    /**
     *  PASOS PARA USAR LA VINCULACIÓN DE VISTAS / BINDING (MÁS FÁCIL QUE EL FINDVIEWBYID
     *
     *  1) MODIFICO EL FICHERO GRADLE DEL MÓDULO (module app build.gradle.kts)
     *
     *  buildFeatures {
     *         viewBinding = true
     *     }
     *
     *  Después de esto, es necesario hacer SYNC del fichero de Gradle
     *  (File -> Sync gradle files)
     *
     *  2) En la actividad, me declaro como atributo de la clase, un objeto de la clase "binding"
     *  El nombre de esta clase, viene determinado por el nombre del archivo de layout XML
     *  de tal manera que si mi archivo se llama archivo_layout.xml, la clase binding será
     *  ArchivoLayoutBinding (se añade el sufijo binding)
     *
     *      lateinit var binding: ActivitySumaBinding
     *
     *  3) Para inicializar correctamente el objeto binding anterior, dentro de oncreate hacemos
     *
     *  binding = ActivitySumaBinding.inflate(layoutInflater)
     *  val view = binding.root
     *  setContentView(view)
     *
     *
     */
    lateinit var binding: ActivitySumaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* versión nueva con Binding */
        binding = ActivitySumaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // binding tendrá atributos identificados por el id de las vistas
        //que me dan acceso directo a esas vistas (sin tener que hacer findViewById)
        /* fin versión nueva */

        /* versión "vieja" findViewById */
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_suma)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        /* fin versión "vieja" findViewById*/
    }

    fun sumar(view: View)
    {
        /* con findViewById */
        //var sumando1:Int = findViewById<EditText>(R.id.sumando1).text.toString().toInt()
        // var sumando2:Int = findViewById<EditText>(R.id.sumando2).text.toString().toInt()
        /* con binding */
        var sumando1:Int = binding.sumando1.text.toString().toInt()
        var sumando2:Int = binding.sumando2.text.toString().toInt()
        var suma = sumando1 + sumando2
        //findViewById<TextView>(R.id.resultadoSuma).text = suma.toString()
        binding.resultadoSuma.text = suma.toString()
    }

}