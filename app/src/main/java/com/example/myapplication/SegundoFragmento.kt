package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.Toast
import androidx.fragment.app.Fragment

class SegundoFragmento: Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.activity_segundo_fragmento, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val ratingBar: RatingBar = view.findViewById(R.id.ratingBarFragmento)

        // Listener para los cambios del usuario
        ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->



            if (fromUser) {
                // mensaje temporal con la calificación
                Toast.makeText(requireContext(), "Calificación: $rating estrellas", Toast.LENGTH_SHORT).show()


                Log.d("SegundoFragmento", "El usuario seleccionó: $rating")
            }
        }


    }
}