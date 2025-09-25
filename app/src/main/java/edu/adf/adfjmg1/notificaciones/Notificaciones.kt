package edu.adf.adfjmg1.notificaciones

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.os.Build
import androidx.core.app.NotificationCompat
import edu.adf.adfjmg1.MainMenuActivity
import edu.adf.adfjmg1.R
import android.net.Uri
import android.util.Log
import androidx.annotation.RequiresApi
import edu.adf.adfjmg1.Constantes

object Notificaciones {

    val NOTIFICATION_CHANNEL_ID = "UNO"
    val NOTIFICATION_CHANNEL_NAME = "CANAL_ADF"

//    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(Build.VERSION_CODES.O)
    private fun crearCanalNotificacion (context: Context): NotificationChannel?
    {
        var notificationChannel : NotificationChannel? = null

        Log.d(Constantes.ETIQUETA_LOG, "Dentro de lanzarNotificacion() x")

        notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT )
        notificationChannel.enableLights(true)
        notificationChannel.enableVibration(true)
        //vibración patron suena 500 ms, no vibra otros 500 ms
        notificationChannel.vibrationPattern = longArrayOf(
            500,
            500,
            500,
            500,
            500,
            500,
        )
        notificationChannel.lightColor = context.applicationContext.getColor(R.color.miRojo)
        //sonido de  la notificación si el api es inferior a la 8, hay que setear el sonido aparte
        //si es igual o superior, la notificación "hereda" el sonido del canal asociado
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build()

        notificationChannel.setSound(
            Uri.parse("android.resource://" + context.packageName + "/" + R.raw.snd_noti),
            audioAttributes
        )

        notificationChannel.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC

        return notificationChannel
    }

    // TODO crear el canal y lanzar desde el receiver inicio movil la notificación
    fun lanzarNotificacion (context: Context)
    {
        Log.d(Constantes.ETIQUETA_LOG, "Dentro de lanzarNotificacion()")
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            var notificationChannel = crearCanalNotificacion(context)
            notificationManager.createNotificationChannel(notificationChannel!!)

            var nb = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.outline_notifications_24)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.emoticono_risa))
                .setContentTitle("BUENOS DÍAS")
                .setSubText("aviso")
                .setContentText("Vamos a ver fotos de perros")
                .setAutoCancel(true)//es para que cuando toque la noti, desaparezca
                .setDefaults(Notification.DEFAULT_ALL)

            val intentDestino = Intent(context, MainMenuActivity::class.java)
            // pendingIntent -- Intent "securizado" -- permite lanzar el intent, como si estuviera dentro de mi app
            val pendingIntent = PendingIntent.getActivity(context, 100, intentDestino, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)
            nb.setContentIntent(pendingIntent) //asocio el intent a la notificación.
            // si estoy en api anterior, debo setear el sonido fuera porque no hay canal
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            {
                nb.setSound(Uri.parse("android.resource://" + context.packageName + "/" + R.raw.snd_noti))
            }

            Log.d(Constantes.ETIQUETA_LOG, "Dentro de lanzarNotificacion()2")
            val notification = nb.build()

            Log.d(Constantes.ETIQUETA_LOG, "Dentro de lanzarNotificacion() 3")
            notificationManager.notify(500, notification)
        }
    }
}