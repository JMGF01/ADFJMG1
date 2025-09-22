package edu.adf.adfjmg1.foto

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.adf.adfjmg1.Constantes
import edu.adf.adfjmg1.R
import edu.adf.adfjmg1.databinding.ActivityFotoBinding
import java.io.File
import java.net.URI
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date

class FotoActivity : AppCompatActivity() {

    lateinit var binding: ActivityFotoBinding
    lateinit var uriFoto:Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityFotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun tomarFoto(view: View)
    {
        pedirPermisos()
    }

    private fun pedirPermisos() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA), 500)
        }
        else {
            Log.d(Constantes.ETIQUETA_LOG, "Versión antigua, permiso no soportado")
            lanzarCamara()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            Log.d(Constantes.ETIQUETA_LOG, "PERMISO CÁMARA CONCEDIDO")
            lanzarCamara()
        } else {
            Log.d(Constantes.ETIQUETA_LOG, "PERMISO CÁMARA NO CONCEDIDO")
            Toast.makeText(this, "SIN PERMISOS PARA HACER FOTOS", Toast.LENGTH_LONG).show()
        }
    }

    private fun lanzarCamara() {
        //TODO("Not yet implemented")
        // 1 CREAMOS UN FICHERO DESTINO
        this.uriFoto = crearFicheroDestino()
        Log.d(Constantes.ETIQUETA_LOG, "URI FOTO = $uriFoto")
        // 2 LANZAMOS EL INTENT
    }

    /**
     * Scoped Storage (Almacenamiento con Ámbito) — Desde Android 11 (versión "R")
     * "A partir de Android R (Android 11), no podrás acceder al contenido de la carpeta interna compartida (/Android/data/, /Android/obb/) desde este gestor de archivos u otras apps.
     *
     * Con Scoped Storage, ninguna app puede acceder libremente al almacenamiento de otras apps o al directorio compartido /Android/data/, incluso aunque antes fuera posible
     *
     * Esto dice la teoría. Con navegadores de serie de algunos dispositivos sí se puede acceder
     *
     * Programáticamente se puede seguir accediendo y escribiendo sin permisos en el almacenamiento interno compartido
     * "
     */

    private fun crearFicheroDestino(): Uri {
        val fechaActual = Date()
        val momentoActual = SimpleDateFormat("yyyyMMdd_HHmmss").format(fechaActual)
        val nombreFichero = "FOTO_ADF_$momentoActual"
//        var rutaFoto = "${filesDir.path}/$nombreFichero" // ruta privada de nuestra aplicación   ruta completa fichero: /data/user/0/edu.adf.adfjmg1/files/FOTO_ADF_20250922_122920
//        var rutaFoto = getExternalFilesDir(null)?.path + nombreFichero // ruta público/privada - si le paso null coge el directorio raíz ruta completa fichero: /storage/emulated/0/Android/data/edu.adf.adfjmg1/filesFOTO_ADF_20250922_124507
//        var rutaFoto = "${Environment.getExternalStorageDirectory()?.path}/$nombreFichero" // trata de escribir en la siguiente ruta, pero da error por denegación de permisos: ruta pública/pública ruta completa fichero: /storage/emulated/0/FOTO_ADF_20250922_134009
        var rutaFoto = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)?.path}/$nombreFichero" // trata de escribir en la siguiente ruta, pero da error por denegación de permisos: ruta pública/pública ruta completa fichero: ruta completa fichero: /storage/emulated/0/Download/FOTO_ADF_20250922_134525


        Log.d(Constantes.ETIQUETA_LOG, "ruta completa fichero: $rutaFoto")

        val ficheroFoto = File(rutaFoto)
        ficheroFoto.createNewFile() // este método tira una excepción, pero para KOTLIN todas las excepciones son de tipo RUNTIME o UNCHECKED - NO ME OBLIGA A GESTIONARLAS CON TRY/CATCH -
        // TODO CREAR RUTA PÚBLICA
        return ficheroFoto.toUri()
    }


}