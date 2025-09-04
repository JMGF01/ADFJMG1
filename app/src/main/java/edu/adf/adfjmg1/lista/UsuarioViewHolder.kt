package edu.adf.adfjmg1.lista

import android.os.CpuUsageInfo
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.graphics.toColor
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import edu.adf.adfjmg1.R
import edu.adf.adfjmg1.Usuario
import org.w3c.dom.Text

/**
 * Esta clase representa el hueco/fila que se recicla y cuyo contenido se actualiza
 * con la información del usuario que toque
 */
class UsuarioViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    //definimos un atributo para ligarlo a cada columna de la fila

    val tvNombreUsuario: TextView = itemView.findViewById<TextView>(R.id.nombreUsuario)
    val tvEdadUsuario: TextView = itemView.findViewById<TextView>(R.id.edadUsuario)
    val tvSexoUsuario: TextView = itemView.findViewById<TextView>(R.id.sexoUsuario)
    val lColorFav: LinearLayout = itemView.findViewById<LinearLayout>(R.id.colorUsuarioFav)

    /**
     * Cargamos la información del usuario en su contenedor
     * @param el usuario corriente/actual
     */
    fun rellenarFilaUsuario (usuario: Usuario)
    {
        this.tvNombreUsuario.text = usuario.nombre
        this.tvEdadUsuario.text = usuario.edad.toString()
        this.tvSexoUsuario.text = usuario.sexo.toString()
        //this.lColorFav.setBackgroundColor(usuario.colorFavorito) // este id es el id del recurso R.color y no lo coge bien. Espera un valor hexadecimal.
        this.lColorFav.setBackgroundColor(this.lColorFav.context.getColor(usuario.colorFavorito)) //este sí lo coge bien, porque del id del recurso, coge el color y lo traduce al hexadecimal correcto
    }
}