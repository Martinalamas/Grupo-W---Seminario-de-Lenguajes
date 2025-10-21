package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CancionAdapter(
    private val canciones: MutableList<Cancion>,
    private val context: Context
) : RecyclerView.Adapter<CancionAdapter.CancionViewHolder>() {

    class CancionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtTitulo: TextView = view.findViewById(R.id.tv_cancion)
        val txtArtista: TextView = view.findViewById(R.id.tv_artista)
        val txtTop: TextView = view.findViewById(R.id.tv_top)

    }

    override fun getItemCount(): Int = canciones.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CancionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cancion, parent, false)
        return CancionViewHolder(view)
    }

    override fun onBindViewHolder(holder: CancionViewHolder, position: Int) {
        val item = canciones.get(position)
        holder.txtTitulo.text = item.titulo
        holder.txtArtista.text = item.artista
        holder.txtTop.text = item.top.toString()


        holder.itemView.setOnClickListener {
            val i = Intent(holder.itemView.context, DetalleCancionActivity::class.java)
            val ctx = holder.itemView.context
            i.putExtra("titulo",item.titulo)
            i.putExtra("artista",item.artista)
            i.putExtra("duracion",item.duracion)
            ctx.startActivity(i)
        }
    }
}