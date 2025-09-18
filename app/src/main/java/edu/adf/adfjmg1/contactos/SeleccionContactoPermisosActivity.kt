package edu.adf.adfjmg1.contactos

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.icu.text.CompactDecimalFormat.CompactStyle
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.adf.adfjmg1.Constantes
import edu.adf.adfjmg1.R

/**
 * A diferencia de la actividad SeleccionContacto, aquí sí leemos todos los contactos (no sólo uno)
 * por lo que necesitamos obtener el permiso de READ_CONTACTS, que además al ser peligroso
 * necesitamos pedirlo en ejecución
 */
class SeleccionContactoPermisosActivity : AppCompatActivity() {

    val infoVersion =  " (${Build.VERSION.RELEASE}) API ${Build.VERSION.SDK_INT} "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccion_contacto_permisos)
        // si tengo el permiso concedido
            // leemos los contactos
        // si no, lo pido
            // si es la primera vez, le explico
        /*
         val permisoConcedido = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
         if (permisoConcedido == PackageManager.PERMISSION_GRANTED)
         */

        // EN LA PRÁCTICA, PEDIMOS SIEMPRE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), 300)
        }
        else {
            Log.d(Constantes.ETIQUETA_LOG, "Versión antigua")
            leerContactos()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        Log.d(Constantes.ETIQUETA_LOG, "A la vuelta de pedir permisos de lectura")
        if (requestCode == 300)
        {
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Log.d(Constantes.ETIQUETA_LOG, "Permiso CONCEDIDO")
                //obtenerVersion()
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                    Toast.makeText(this, "SE HAN CONCEDIDO PERMISOS DE LECTURA AUTOMÁTICAMENTE", Toast.LENGTH_LONG).show()
                }
                leerContactos()
            } else
            {
                Log.d(Constantes.ETIQUETA_LOG, "Permiso DENEGADO")
                Toast.makeText(this, "SIN permisos para leer contactos", Toast.LENGTH_LONG).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun consultarTodosLosTelefonos(): Unit {
        Log.d(Constantes.ETIQUETA_LOG, "consultarTodosLosTelefonos")
        val telefonos: Cursor? = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        var numcolumna = 0
        var number: String? = ""

        while (telefonos?.moveToNext() == true) {
            numcolumna = telefonos.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            number = telefonos.getString(numcolumna)
            Log.d(Constantes.ETIQUETA_LOG, "Telefono $number")
        }
        telefonos?.close()//Cierro el cursor!

    }

    fun leerContactos()
    {
        consultarTodosLosTelefonos()
        mostrarContactos("")
    }



    fun mostrarContactos (prefijo : String)
    {
        val uri_contactos = ContactsContract.Contacts.CONTENT_URI//content://com.android.contacts/contacts
        /*val cursor_contactos = contentResolver.query(
            uri_contactos,
            null,
            ContactsContract.Contacts.DISPLAY_NAME +" LIKE ?",
            arrayOf(prefijo),
            null
        )*/

//        val cursor_contactos = contentResolver.query(
//            uri_contactos,
//            null,
//            null,
//            null,
//            null
//        )

        val cursor_contactos = if (prefijo.isEmpty())
        {
            contentResolver.query(
                uri_contactos,
                null,
                null,
                null,
                null
            )
        } else {
            contentResolver.query(
                uri_contactos,
                null,
                ContactsContract.Contacts.DISPLAY_NAME +" LIKE ?",
                arrayOf(prefijo),
                null
            )
        }

        cursor_contactos.use {
            if (it?.moveToFirst() == true)
            {
                do {
                    Log.d(Constantes.ETIQUETA_LOG, "NUM CONTACTOS = " + it.count)

                    val numColId = it.getColumnIndex(ContactsContract.Contacts._ID)
                    val numColNombre = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)

                    val id = it.getLong(numColId)
                    val nombre = it.getString(numColNombre)

                    Log.d(Constantes.ETIQUETA_LOG, "Nombre = $nombre ID = $id")
                    mostrarCuentaRaw(id)
                } while (it.moveToNext())
            }
        }

    }


    fun mostrarCuentaRaw (id:Long):Unit
    {
        var cursor_raw = contentResolver.query(
            ContactsContract.RawContacts.CONTENT_URI,
            null,
            ContactsContract.RawContacts.CONTACT_ID + " = " +id,
            null,
            null
        )
        if (cursor_raw?.moveToFirst()==true){
            do {
                val columnaIdRaw = cursor_raw.getColumnIndex(ContactsContract.RawContacts._ID)
                val id_raw = cursor_raw.getLong(columnaIdRaw)

                val tipoIdRaw = cursor_raw.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_TYPE)
                val tipo_raw = cursor_raw.getString (tipoIdRaw)

                val nombreCuentaIdRaw = cursor_raw.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_NAME)
                val nombreCuenta_raw = cursor_raw.getString(nombreCuentaIdRaw)

                Log.d(Constantes.ETIQUETA_LOG, "(RAW) NOMBRE CUENTA = $nombreCuenta_raw TIPO CUENTA = $tipo_raw ID = $id_raw")

                mostrarDetalle(id_raw)


            } while (cursor_raw.moveToNext())
        }
        cursor_raw?.close()
    }

    fun mostrarDetalle (id_raw :Long):Unit
    {
        val cursor_data = contentResolver.query(
            ContactsContract.Data.CONTENT_URI,
            null,
            ContactsContract.Data.RAW_CONTACT_ID +" = " + id_raw,
            null,
            null
        )

        cursor_data?.use {
            //it es el cursor

            if (it.moveToFirst()==true)
            {
                do {
                    val tipoMimeCol = it.getColumnIndex(ContactsContract.Data.MIMETYPE)
                    val tipoMime = it.getString(tipoMimeCol)
                    val dataCol = it.getColumnIndex(ContactsContract.Data.DATA1)
                    val data = it.getString(dataCol)

                    Log.d(Constantes.ETIQUETA_LOG, "   (DATA) MIME = $tipoMime DATA = $data")

                } while (it.moveToNext())
            }
        } //se cierra el cursor automáticamente si lo uso con use

    }

    fun obtenerVersion(): String {

        val sdkVersion = getAndroidCodename(Build.VERSION.SDK_INT) + infoVersion
        Log.d("MIAPP_VERSION","Version SDK: $sdkVersion")
        return sdkVersion
    }

    fun getAndroidCodename(sdkInt: Int): String {
        return when (sdkInt) {
            36 -> "Baklava"            // Android 16
            35 -> "VanillaIceCream"    // Android 15
            34 -> "Upside Down Cake"   // Android 14
            33 -> "Tiramisu"           // Android 13
            32 -> "Android 12L"        // Android 12L
            31 -> "Snow Cone"          // Android 12
            30 -> "Red Velvet Cake"    // Android 11
            29 -> "Quince Tart"        // Android 10
            28 -> "Pie"                // Android 9
            27 -> "Oreo"               // Android 8.1
            26 -> "Oreo"               // Android 8.0
            25 -> "Nougat"             // Android 7.1
            24 -> "Nougat"             // Android 7.0
            23 -> "Marshmallow"
            22 -> "Lollipop"
            21 -> "Lollipop"
            20 -> "KitKat Watch"
            19 -> "KitKat"
            18 -> "Jelly Bean"
            17 -> "Jelly Bean"
            16 -> "Jelly Bean"
            15 -> "Ice Cream Sandwich"
            14 -> "Ice Cream Sandwich"
            13 -> "Honeycomb"
            12 -> "Honeycomb"
            11 -> "Honeycomb"
            10 -> "Gingerbread"
            9 -> "Gingerbread"
            8 -> "FroYo"
            7 -> "Eclair"
            6 -> "Eclair"
            5 -> "Eclair"
            4 -> "Donut"
            3 -> "Cupcake"
            2 -> "Banana Bread"
            1 -> "Apple Pie"
            else -> "Unknown"
        }
    }
}