package edu.adf.adfjmg1.receptor

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import edu.adf.adfjmg1.Constantes
import edu.adf.adfjmg1.alarma.GestorAlarma
import edu.adf.adfjmg1.notificaciones.Notificaciones

class FinServicioReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        Log.d(Constantes.ETIQUETA_LOG, "Servicio finalizado")
        //inspecciono el intent para ver el resultado del servicio (número aleatorio)
        val numAleatorio = intent.getIntExtra("NUM_ALEATORIO", -1)
        Log.d(Constantes.ETIQUETA_LOG, "Número aleatorio = $numAleatorio")
        // si el número aleatorio es >= 60 --> "hay mensajes" -- lanzamos una notificación
        // si no, --> no hago nada
        // en cualquier caso reprogramo la alarma
        if (numAleatorio >= 60)
        {
            Notificaciones.lanzarNotificacion(context)
            Log.d(Constantes.ETIQUETA_LOG, "El servicio nos da un número mayor a 60, hay mensajes")
        }
        // reprogramamos la alarma -- comento las dos líneas siguientes para que no se esté lanzando todo el tiempo.
//        GestorAlarma.programarAlarma(context)
//        Log.d(Constantes.ETIQUETA_LOG, "Reprogramo alarma")
        // desregistrar el receptor, para que la próxima vez que acabe el servicio, no salte el
        LocalBroadcastManager.getInstance(context).unregisterReceiver(this)
    }
}