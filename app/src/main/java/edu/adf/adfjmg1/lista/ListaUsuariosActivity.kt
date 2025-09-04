package edu.adf.adfjmg1.lista

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.adf.adfjmg1.R
import edu.adf.adfjmg1.Usuario
import edu.adf.adfjmg1.databinding.ActivityListaUsuariosBinding



class ListaUsuariosActivity : AppCompatActivity() {

    var listaUsuarios =  listOf<Usuario>(Usuario(nombre = "Vale", edad = 41, sexo = 'M', esMayorEdad = true, colorFavorito = R.color.miRojo),
        Usuario(nombre = "Juanma", edad = 45, sexo = 'M', esMayorEdad = true, colorFavorito = R.color.miAzul),
        Usuario(nombre = "Cristina", edad = 56, sexo = 'M', esMayorEdad = true, colorFavorito = R.color.miAmarillo),
        Usuario(nombre = "Patri", edad = 46, sexo = 'M', esMayorEdad = true, colorFavorito = R.color.miNaranja),
        Usuario(nombre = "JoseAntonio", edad = 53, sexo = 'M', esMayorEdad = true, colorFavorito = R.color.miRosa))

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
        this.listaUsuarios = this.listaUsuarios.sortedBy { it.nombre }
        //this.listaUsuarios = this.listaUsuarios.sortedByDescending { it.nombre }//de mayor a menor

        this.binding.recViewUsuarios.adapter = UsuariosAdapter(this.listaUsuarios)
    }

    fun ondenarPorEdad(view: View)
    {
        this.listaUsuarios = this.listaUsuarios.sortedWith{ usuario0, usuario1 -> usuario0.edad - usuario1.edad}
        this.binding.recViewUsuarios.adapter = UsuariosAdapter(this.listaUsuarios)
    }

}