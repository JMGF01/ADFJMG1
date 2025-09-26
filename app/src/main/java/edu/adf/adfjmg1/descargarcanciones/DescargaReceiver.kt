package edu.adf.adfjmg1.descargarcanciones

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.util.Log
import androidx.core.net.toUri
import edu.adf.adfjmg1.Constantes

class DescargaReceiver : BroadcastReceiver() {

    var idDescarga: Long = -1
    lateinit var activity: DescargarCancionActivity

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(Constantes.ETIQUETA_LOG, "Descarga Finalizada")

        // preparo mi consulta sobre el CONTENT PROVIDER DEL DOWNLOADMANAGER
        val consulta = DownloadManager.Query()
        consulta.setFilterById(idDescarga)

        // obtengo el downloadmanager
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        var cursor = downloadManager.query(consulta)

        cursor.use {
            if (cursor.moveToFirst())
            {
                val numColStatus = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                val numColUri = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)
                val status = cursor.getInt(numColStatus)
                val uri = cursor.getString(numColUri)
                Log.d(Constantes.ETIQUETA_LOG, "Status descarga = $status URI = $uri")
                activity.actualizarEstadoDescarga(status, uri)
            }
        }

        context.unregisterReceiver(this) //si no lo deregistramos, la siguiente vez que haga otra descarga estaría escuchando la nueva y la anterior.
    }




}