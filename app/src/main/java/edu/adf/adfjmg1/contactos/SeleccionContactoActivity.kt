package edu.adf.adfjmg1.contactos

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import edu.adf.adfjmg1.Constantes
import edu.adf.adfjmg1.R

class SeleccionContactoActivity : AppCompatActivity() {

    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
        //Esta función se ejecuta a la vuelta del listado de contactos
        // it.resultCode // al ser una interfaz funcional, se podría usar directamente un iterator (it)
        resultado ->
        Log.d(Constantes.ETIQUETA_LOG, "A la vuelta de Contactos ...")
        if (resultado.resultCode == RESULT_OK)
        {
            Log.d(Constantes.ETIQUETA_LOG, "La selección del contacto fue BIEN")
            Log.d(Constantes.ETIQUETA_LOG, "uri contactos = ${resultado.data}")
            mostrarDatosContacto(resultado.data!!)
        } else {
            Log.d(Constantes.ETIQUETA_LOG, "La selección del contacto fue MAL")
        }

    }

    private fun mostrarDatosContacto(intent: Intent) {
        val cursor = contentResolver.query(intent.data!!, null, null, null, null)
        cursor!!.moveToFirst() // me pongo en la primera fila
        // y ahora accedemos a las columnas nombre y número (el nombre de las columnas lo averiguamos en la documentación del contentprovider de la app, en este caso, de Contactos)
        val columnaNombre = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
        val columnaNumero = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
        val nombre = cursor.getString(columnaNombre)
        val numero = cursor.getString(columnaNumero)
        Log.d(Constantes.ETIQUETA_LOG, "NOMBRE = $nombre y NÚMERO = $numero")
    }

    /* with(cursor) {
     val columnaNombreW = getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
     val columnaNumeroW = getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
     val nombreW = getString(columnaNombreW)
     val numeroW = getString(columnaNumeroW)
     Log.d(Constantes.ETIQUETA_LOG, "NOMBRE = $nombreW y NÚMERO = $numeroW")
     close()
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccion_contacto)

        selectContact()

    }

    private fun selectContact() {

        Log.d(Constantes.ETIQUETA_LOG, "Lanzando la app de contactos")
        val intent = Intent(Intent.ACTION_PICK,ContactsContract.CommonDataKinds.Phone.CONTENT_URI)

        if (intent.resolveActivity(packageManager)!=null) {
            Log.d(Constantes.ETIQUETA_LOG, "SÍ HAY app de contactos")
            startForResult.launch(intent)
        } else {
            Log.d(Constantes.ETIQUETA_LOG, "NO HAY app de contactos")
        }
    }
}