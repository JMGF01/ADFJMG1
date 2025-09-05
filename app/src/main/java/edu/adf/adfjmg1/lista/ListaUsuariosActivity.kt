package edu.adf.adfjmg1.lista

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.adf.adfjmg1.Constantes
import edu.adf.adfjmg1.R
import edu.adf.adfjmg1.Usuario
import edu.adf.adfjmg1.databinding.ActivityListaUsuariosBinding
import kotlin.system.measureNanoTime


class ListaUsuariosActivity : AppCompatActivity() {

    var listaUsuarios =  listOf<Usuario>(Usuario(nombre = "Vale", edad = 41, sexo = 'M', esMayorEdad = true, colorFavorito = R.color.miRojo),
        Usuario(nombre = "Juanma", edad = 45, sexo = 'M', esMayorEdad = true, colorFavorito = R.color.miAzul),
        Usuario(nombre = "Cristina", edad = 56, sexo = 'M', esMayorEdad = true, colorFavorito = R.color.miAmarillo),
        Usuario(nombre = "Patri", edad = 46, sexo = 'M', esMayorEdad = true, colorFavorito = R.color.miNaranja),
        Usuario(nombre = "JoseAntonio", edad = 53, sexo = 'M', esMayorEdad = true, colorFavorito = R.color.miRosa),
        Usuario(nombre = "Marcos", edad = 31, sexo = 'M', esMayorEdad = true, colorFavorito = R.color.miGrisClaro),
        Usuario(nombre = "Jorge", edad = 18, sexo = 'M', esMayorEdad = true, colorFavorito = R.color.ic_launcher_background),
        Usuario(nombre = "Jorge", edad = 33, sexo = 'M', esMayorEdad = true, colorFavorito = R.color.ic_launcher_background),

    )

    lateinit var binding: ActivityListaUsuariosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityListaUsuariosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.binding.recViewUsuarios.adapter = UsuariosAdapter(this.listaUsuarios)
        this.binding.recViewUsuarios.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
//        this.binding.recViewUsuarios.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, true)
//        this.binding.recViewUsuarios.layoutManager = GridLayoutManager(this, 2)
    }

    fun ordenarPorNombre(view: View)
    {
        //this.listaUsuarios.sortedBy { usuario -> usuario.nombre }
        var ns = measureNanoTime { this.listaUsuarios = this.listaUsuarios.sortedBy { it.nombre }}
        //this.listaUsuarios = this.listaUsuarios.sortedByDescending { it.nombre }//de mayor a menor
        Log.d(Constantes.ETIQUETA_LOG, "EN ORDENAR POR NOMBRE TARDA $ns NANOSEGUNDOS")

        this.binding.recViewUsuarios.adapter = UsuariosAdapter(this.listaUsuarios)
    }

    fun ondenarPorEdad(view: View)
    {
//        this.listaUsuarios = this.listaUsuarios.sortedWith{ usuario0, usuario1 -> usuario0.edad - usuario1.edad}
        var ns1 = measureNanoTime { this.listaUsuarios = this.listaUsuarios.sortedWith { usuario0, usuario1 -> usuario0.edad - usuario1.edad } }
        var ns2 = measureNanoTime {  this.listaUsuarios = this.listaUsuarios.sortedByDescending { it.edad } }

        Log.d(Constantes.ETIQUETA_LOG, "EN ORDENAR CON WITH TARDA ${ns1} nanosegundos y con BY ${ns2}")


        this.binding.recViewUsuarios.adapter = UsuariosAdapter(this.listaUsuarios)
    }

    fun ordenarPorNombreYEdad(view: View)
    {
        var ns = measureNanoTime {
        this.listaUsuarios =  this.listaUsuarios.sortedWith(
            compareBy<Usuario> { it.nombre }.thenBy { it.edad } //compara por nombre, y si son iguales, compara por la edad.
//          compareBy<Usuario> { it.nombre }.thenByDescending { it.edad } //compara por nombre, y si son iguales, compara por la edad de forma descendente.
        )}
        Log.d(Constantes.ETIQUETA_LOG, "EN ORDENAR POR NOMBRE y edad TARDA ${ns} nanosegundos")
        this.binding.recViewUsuarios.adapter = UsuariosAdapter(this.listaUsuarios)
    }


}