package edu.adf.adfjmg1.receptor

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.view.OnReceiveContentListener
import edu.adf.adfjmg1.Constantes

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(Constantes.ETIQUETA_LOG, "En AlarmaReceiver")
        //TODO("Not yet implemented")
    }

}
