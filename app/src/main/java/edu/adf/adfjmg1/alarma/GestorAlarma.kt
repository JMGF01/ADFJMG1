package edu.adf.adfjmg1.alarma

import android.Manifest
import android.annotation.TargetApi
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresPermission
import edu.adf.adfjmg1.Constantes
import edu.adf.adfjmg1.receptor.AlarmReceiver
import java.text.SimpleDateFormat
import android.util.Log

object GestorAlarma {

    /**
     * Me declaro un objeto en vez de clase, cuando sólo necesito una instancia "es algo estático-JAVA"
      */

//    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    @TargetApi(Build.VERSION_CODES.S_V2)
    fun programarAlarma(context: Context)
    {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        //calcular el tiempo donde suena la alarma
        val tiempo = System.currentTimeMillis()+30000 // 30 segundos más

        //preparo el listener de la alarma - Receiver
        val intentAlarma = Intent(context, AlarmReceiver::class.java)
        val pendingIntentAlarm = PendingIntent.getBroadcast(context, 303, intentAlarma, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        // programo la alarma AlarmManager.RTC_WAKEUP --> tiempo en milisegundos del reloj del sistema y que salte con el sta bloqueado
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, tiempo, pendingIntentAlarm)
//        alarmManager.set(AlarmManager.RTC_WAKEUP, tiempo, pendingIntentAlarm)
        try {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, tiempo, pendingIntentAlarm)
//            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, tiempo, pendingIntentAlarm)
            //mostramos un mensaje informativo
            val dateformatter = SimpleDateFormat("E dd/MM/yyyy 'a las ' hh:mm:ss")
            val mensaje = dateformatter.format(tiempo)
            Log.d(Constantes.ETIQUETA_LOG, "ALARMA PROGRAMADA PARA $mensaje")
            Toast.makeText(context, "Alarma programada para $mensaje", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Log.d(Constantes.ETIQUETA_LOG, "EXCEPCIÓN AL PROGRAMAR ALARMA: $e")
        }
    }
}