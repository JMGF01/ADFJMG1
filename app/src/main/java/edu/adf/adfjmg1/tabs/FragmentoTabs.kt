package edu.adf.adfjmg1.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.adf.adfjmg1.databinding.FragmentoTabBinding

class FragmentoTabs:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var fragmentoTab = FragmentoTabBinding.inflate(inflater, container, false)

        val num = arguments?.getInt("VALOR") ?: 0
        fragmentoTab.numVista.text = "VISTA $num"

        return fragmentoTab.root
    }
}