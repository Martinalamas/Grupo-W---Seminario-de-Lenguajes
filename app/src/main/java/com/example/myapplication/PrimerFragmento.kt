package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class PrimerFragmento : Fragment() {
    var listener : PrimerFragmentoInterfaz? = null
    lateinit var btnMostrarContenido : Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.primer_fragmento, container, false)
        btnMostrarContenido = view.findViewById(R.id.btnFragment)
        btnMostrarContenido.setOnClickListener {
            listener?.mostrarContenido()
        }
        return view

    }

}