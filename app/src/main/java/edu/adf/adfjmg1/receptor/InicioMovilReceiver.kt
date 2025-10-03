package edu.adf.adfjmg1.receptor

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import edu.adf.adfjmg1.Constantes
import edu.adf.adfjmg1.MainMenuActivity
import edu.adf.adfjmg1.alarma.GestorAlarma
import edu.adf.adfjmg1.ejercicio3.ClasificacionActivity
import edu.adf.adfjmg1.notificaciones.Notificaciones
import edu.adf.adfjmg1.servicios.NumAleatorioService


class InicioMovilReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        Log.d(Constantes.ETIQUETA_LOG, "En InicioMovil receiver")
//        context.startActivity(Intent(context, MainMenuActivity::class.java))
        try {
            // Notificaciones.lanzarNotificacion(context)
            // GestorAlarma.programarAlarma(context)

            val ficherop = context.getSharedPreferences("ajustes", Context.MODE_PRIVATE)

            if (ficherop.getBoolean("ALARMA", false))
            {
                Log.d(Constantes.ETIQUETA_LOG, "Alarma programada, lanzo servicio")
                val intentService = Intent(context, NumAleatorioService::class.java)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(intentService)
                } else {
                    context.startService(intentService)
                }
                Log.d(Constantes.ETIQUETA_LOG, "Servicio lanzado...")
            } else {
                Log.d(Constantes.ETIQUETA_LOG, "No lanzo servicio de chequeo...")
            }
        } catch (e: Exception) {
            Log.d(Constantes.ETIQUETA_LOG, "Error al lanzar notificaciones -> ", e)
        }

        // TODO Comprobar si puedo lanzar la actividad desde el receiver
//        val intentMainActivity = Intent(context, MainMenuActivity::class.java)
//        intentMainActivity.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        context.startActivity(intentMainActivity)//TODO REVISAR LOG LANZAMIENTO
    }

}