package edu.adf.adfjmg1.tabs

import android.os.Bundle
import android.util.Log
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.tabs.TabLayoutMediator
import edu.adf.adfjmg1.Constantes
import edu.adf.adfjmg1.R
import edu.adf.adfjmg1.databinding.ActivityTabsBinding

class TabsActivity : AppCompatActivity() {

    lateinit var binding: ActivityTabsBinding
    lateinit var adapterTabs: AdapterTabs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityTabsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.adapterTabs = AdapterTabs(this)
        this.binding.vpt.adapter = this.adapterTabs
        //ASOCIO AL TABLAYOUT EL VIEWPAGER
        TabLayoutMediator(this.binding.tablayout,this.binding.vpt){
            tl, n -> tl.text = "VISTA ${n+1}"
        }.attach()

        TabLayoutMediator(this.binding.tablayout,this.binding.vpt, fun (tl, n){
            tl.text = "VISTA ${n+1}"
        }).attach()

        onBackPressedDispatcher.addCallback{
            Log.d(Constantes.ETIQUETA_LOG, "Ha tocado el botón hacia atrás 1")
            haciaAtras()
        }

//        onBackPressedDispatcher.addCallback(this){
//            Log.d(Constantes.ETIQUETA_LOG, "Ha tocado el botón hacia atrás 2")
//        }

    }


    fun haciaAtras ()
    {
        if (this.binding.vpt.currentItem == 0) { // si estoy en el tab 1
            finish()
        } else { // si no
            this.binding.vpt.currentItem == this.binding.vpt.currentItem-1 // me voy al item anterior
        }
    }

    //FORMA ANTIGUA DE ESCUCHAR EL BOTÓN HACIA ATRÁS
    // A PARTIR DEL API 33, DEPRECADO

//    override fun onBackPressed() {
//        Log.d(Constantes.ETIQUETA_LOG, "Ha dado al botón de ir hacia atrás")
//        if (this.binding.vpt.currentItem == 0) { // si estoy en el tab 1
//            super.onBackPressed() // salgo
//        } else { // si no
//            this.binding.vpt.currentItem == this.binding.vpt.currentItem-1 // me voy al item anterior
//        }
//
//    }
}