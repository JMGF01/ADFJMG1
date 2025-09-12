package edu.adf.adfjmg1.perros

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import edu.adf.adfjmg1.Constantes

class AdapterPerrosFragment(fragmentActivity: FragmentActivity):  FragmentStateAdapter(fragmentActivity) {

    var fotosRazaPerros: FotosPerro? = null

    override fun createFragment(position: Int): Fragment
    {
        var perroFragment = PerroFragment()
        val url = this.fotosRazaPerros?.message?.get(position)
        Log.d(Constantes.ETIQUETA_LOG, "A pintar foto ${url}")

        var contador = "${position+1} / ${this.fotosRazaPerros!!.message.size.toString()}"

        var bundle = Bundle()
        bundle.putString("URL_FOTO", url) //meto la información que quiero pintar en ese fragment
        bundle.putString("CONTADOR", contador) //meto el contador con la posición

        perroFragment.arguments = bundle//lo guardo en arguments (Bundle)

        return perroFragment
    }

    override fun getItemCount(): Int {
        return this.fotosRazaPerros?.message?.size ?: 0
    }

}