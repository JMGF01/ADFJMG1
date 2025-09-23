package edu.adf.adfjmg1.foto

import android.Manifest
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.content.pm.LauncherActivityInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
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
    val launcherIntentFoto = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
            resultado ->
        if (resultado.resultCode== RESULT_OK)
        {
            Log.d(Constantes.ETIQUETA_LOG, "La foto fue bien")
        } else {
            Log.d(Constantes.ETIQUETA_LOG, "La foto fue mal")
        }

    }

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

//    private fun lanzarCamara() {
//        //TODO("Not yet implemented")
//        // 1 CREAMOS UN FICHERO DESTINO
//        var res = crearFicheroDestino()
//        if (res != null)
//        {
//            this.uriFoto = res.toUri()
//        }
//
//        Log.d(Constantes.ETIQUETA_LOG, "URI FOTO = $uriFoto")
//        // 2 LANZAMOS EL INTENT
//    }

    private fun lanzarCamara() {
        //TODO("Not yet implemented")
        //1 CREAMOS UN FICHERO DESTINO
        val uri = crearFicheroDestino()
        uri?.let { //si uri es != null
            this.uriFoto = it
            Log.d(Constantes.ETIQUETA_LOG, "URI FOTO = ${this.uriFoto}")
            val intentFoto = Intent()
            intentFoto.setAction(MediaStore.ACTION_IMAGE_CAPTURE)
            intentFoto.putExtra(MediaStore.EXTRA_OUTPUT, this.uriFoto)
            launcherIntentFoto.launch(intentFoto)
        } ?: run {
            Toast.makeText(this, "NO FUE POSIBLE CREAR EL FICHERO DESTINO", Toast.LENGTH_LONG).show()
        }
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

//    private fun crearFicheroDestino(): String {
//        var uriFoto: String = "null"
//        val fechaActual = Date()
//        val momentoActual = SimpleDateFormat("yyyyMMdd_HHmmss").format(fechaActual)
//        val nombreFichero = "FOTO_ADF_$momentoActual"
////        var rutaFoto = "${filesDir.path}/$nombreFichero" // ruta privada de nuestra aplicación   ruta completa fichero: /data/user/0/edu.adf.adfjmg1/files/FOTO_ADF_20250922_122920
////        var rutaFoto = getExternalFilesDir(null)?.path + nombreFichero // ruta público/privada - si le paso null coge el directorio raíz ruta completa fichero: /storage/emulated/0/Android/data/edu.adf.adfjmg1/filesFOTO_ADF_20250922_124507
//        var rutaFoto = "${Environment.getExternalStorageDirectory()?.path}/$nombreFichero" // trata de escribir en la siguiente ruta, pero da error por denegación de permisos: ruta pública/pública ruta completa fichero: /storage/emulated/0/FOTO_ADF_20250922_134009
////        var rutaFoto = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)?.path}/$nombreFichero" // trata de escribir en la siguiente ruta, pero da error por denegación de permisos: ruta pública/pública ruta completa fichero: ruta completa fichero: /storage/emulated/0/Download/FOTO_ADF_20250922_134525
//
//
//        Log.d(Constantes.ETIQUETA_LOG, "ruta completa fichero: $rutaFoto")
//
//        val ficheroFoto = File(rutaFoto)
//
//        try {
//            uriFoto = ficheroFoto.toUri().toString() // si es null, la clase String devulve "null"
//            ficheroFoto.createNewFile() // este método tira una excepción, pero para KOTLIN todas las excepciones son de tipo RUNTIME o UNCHECKED - NO ME OBLIGA A GESTIONARLAS CON TRY/CATCH -
//        } catch (e:Exception) {
//            Log.d(Constantes.ETIQUETA_LOG, "Error al crear el fichero destino de la foto: $e")
//        }
////        ficheroFoto.createNewFile() // este método tira una excepción, pero para KOTLIN todas las excepciones son de tipo RUNTIME o UNCHECKED - NO ME OBLIGA A GESTIONARLAS CON TRY/CATCH -
//        // TODO CREAR RUTA PÚBLICA
////        return ficheroFoto.toUri().toString() // si es null, la clase String devulve "null"
//        return uriFoto
//    }

    private fun crearFicheroDestino():Uri? {
        var rutaUriFoto:Uri? = null
        val fechaActual = Date()
        val momentoActual = SimpleDateFormat("yyyyMMdd_HHmmss").format(fechaActual)
        val nombreFichero = "FOTO_ADF_$momentoActual.jpg"
//        var rutaFoto =  "${Environment.getExternalStorageDirectory()?.path}/$nombreFichero" //ruta pública NO SE PUEDE - Security Exception
//        var rutaFoto =  "${Environment.getExternalStoragePublicDirectory (Environment.DIRECTORY_DOWNLOADS)?.path}/$nombreFichero" //ruta pública de DESCARGAS NO SE PUEDE - Security Exception
//        var rutaFoto =  "${Environment.getExternalStoragePublicDirectory (Environment.DIRECTORY_DCIM)?.path}/$nombreFichero" //ruta pública content://edu.adf.adfjmg1/fotopublicadcim/FOTO_ADF_20250923_130126.jpg
//        var rutaFoto =  "${getExternalFilesDir(null)?.path}/$nombreFichero" //ruta pública/privada /storage/emulated/0/Android/data/edu.adf.profe/files/FOTO_ADF_20250922_124524 (EXPLORADOR) /storage/sdcard0/Android/data/edu.adf.profe/files/FOTO_ADF_20250922_124524
        var rutaFoto = "${filesDir.path}/$nombreFichero" //ruta privada ruta completa fichero =  /data/user/0/edu.adf.profe/files/FOTO_ADF_20250922_122916 (EXPLORADOR) /data/data/edu.adf.profe/files/FOTO_ADF_20250922_122916
        Log.d(Constantes.ETIQUETA_LOG, "ruta privada completa fichero =  $rutaFoto ")

        val ficheroFoto = File(rutaFoto)
        try{
            ficheroFoto.createNewFile()//este método tira una excepción, pero para KOTLIN todas las excepciones son de tipo RUNTIME o UnCHECKED - NO ME OBLIGA A GESTIONARLAS CON TRY/CATCH -
            rutaUriFoto = ficheroFoto.toUri()
            Log.d(Constantes.ETIQUETA_LOG, "Fichero destino creado OK")
            rutaUriFoto = FileProvider.getUriForFile(this, "edu.adf.adfjmg1", ficheroFoto)
            Log.d(Constantes.ETIQUETA_LOG, "ruta pública $rutaUriFoto")
        } catch (e:Exception)
        {
            Log.e(Constantes.ETIQUETA_LOG, "ERROR al crear el fichero destino de la foto", e)
        }

        //TODO CREAR RUTA PÚBLICA
        return rutaUriFoto
    }


}