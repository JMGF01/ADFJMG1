package edu.adf.adfjmg1.servicios

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import edu.adf.adfjmg1.Constantes
import edu.adf.adfjmg1.notificaciones.Notificaciones
import edu.adf.adfjmg1.receptor.FinServicioReceiver

class NumAleatorioService : Service() {

    var numeroAleatorio = 0

    //se ejecuta al iniciar el servicio
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //return super.onStartCommand(intent, flags, startId)
        Log.d(Constantes.ETIQUETA_LOG, "onStartCommand")
        //programamos la escucha del final del serivicio
        val localBroadcastManager = LocalBroadcastManager.getInstance(this)
        val finServicioReceiver = FinServicioReceiver()
        val intentFilter = IntentFilter("SERV_ALEATORIO_FINAL")
        //"nuestro receptor está pendiente de esa señal intent filter"
        localBroadcastManager.registerReceiver(finServicioReceiver, intentFilter)

        val notificacionSegundoPlano = Notificaciones.crearNotificacionSegundoPlano(this)
        startForeground(65, notificacionSegundoPlano)

        try{
            Log.d(Constantes.ETIQUETA_LOG, "simulando API")
            //simulamos que consumimos un API
            Thread.sleep(5000)
            numeroAleatorio = (Math.random()*100+1).toInt()

        }catch (e:Exception)
        {
            Log.e("MIAPP", e.message, e)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Log.d(Constantes.ETIQUETA_LOG, "stopForeground")
                stopForeground(STOP_FOREGROUND_REMOVE)
        }//elimina el servicio del primer plano (foregorund) y la notificación
        Log.d(Constantes.ETIQUETA_LOG, "stopSelf")
        stopSelf()//detenemos el servicio lo paramos del tod o (si no, seguría en segundo plano)

        return START_NOT_STICKY//se ejecuta el servicio no se reinicia
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    //se ejecuta cuando se para el servicio.
    override fun onDestroy() {
        Log.d(Constantes.ETIQUETA_LOG, "onDestroy")
        val intent_fin = Intent("SERV_ALEATORIO_FINAL")
        intent_fin.putExtra("NUM_ALEATORIO", this.numeroAleatorio)
        val localBroadcastManager = LocalBroadcastManager.getInstance(this)
        //lanzamos la señal, como diciendo que ha acabado nuestro servicio
        localBroadcastManager.sendBroadcast(intent_fin)

        super.onDestroy()
    }
}