package edu.adf.adfjmg1.receptor

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.view.OnReceiveContentListener
import edu.adf.adfjmg1.Constantes
import edu.adf.adfjmg1.servicios.NumAleatorioService

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(Constantes.ETIQUETA_LOG, "En AlarmaReceiver")
        //TODO lanzaremos un servicio en background
        val intentService = Intent(context, NumAleatorioService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService)
        } else {
            context.startService(intentService)
        }
        Log.d(Constantes.ETIQUETA_LOG, "Servicio lanzado...")

    }

}
