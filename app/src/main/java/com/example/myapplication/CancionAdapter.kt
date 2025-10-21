package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import okhttp3.FormBody
import okhttp3.Request
import java.util.Locale


class CancionAdapter(
    private val canciones: MutableList<Cancion>,
    private val context: Context,
    private val albumId: String
) : RecyclerView.Adapter<CancionAdapter.CancionViewHolder>() {

    private val CLIENT_ID = "8e99a10c7de94bbea433e50b5d4e9ad5"
    private val CLIENT_SECRET = "f05d71479a514ff083d2dd14d57a48b1" // CLIENTID y CLIENTSECRET son credenciales necesarias para implementar la api de spotify
    private val LIMIT_TRACKS = 10


    private val moshi = Moshi.Builder()
        .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.spotify.com/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    private val apiService = retrofit.create(SpotifyApiService::class.java)
    private val client = OkHttpClient()

    @JsonClass(generateAdapter = true)
    data class AlbumResponse(val items: List<Track> = emptyList())

    @JsonClass(generateAdapter = true)
    data class Track(
        val name: String?,
        val artists: List<Artist> = emptyList(),
        @Json(name = "duration_ms") val durationMs: Long = 0L,
        val album: Album? = null
    )

    @JsonClass(generateAdapter = true)
    data class Album(
        val images: List<Image> = emptyList())

    @JsonClass(generateAdapter = true)
    data class Image(val url: String?)

    @JsonClass(generateAdapter = true)
    data class Artist(val name: String?)

    interface SpotifyApiService {
        @GET
        fun getAlbumTracks(
            @Header("Authorization") auth: String,
            @Query("limit") limit: Int = 10
        ): Call<AlbumResponse>
    }

    // --------------------------------------------------------------------------------------------

    init { // obtener token de spotify y llamado a api
        Thread {
            try {
                val cred = "$CLIENT_ID:$CLIENT_SECRET"
                val basic = "Basic " + Base64.encodeToString(cred.toByteArray(), Base64.NO_WRAP)
                val body = FormBody.Builder().add("grant_type", "client_credentials").build()
                val req = Request.Builder()
                    .url("https://accounts.spotify.com/api/token")
                    .addHeader("Authorization", basic)
                    .post(body)
                    .build()
                val resp = client.newCall(req).execute()
                val json = org.json.JSONObject(resp.body?.string().orEmpty())
                val token = json.optString("access_token", "")

                val call = apiService.getAlbumTracks("Bearer $token", LIMIT_TRACKS)
                val response: Response<AlbumResponse> = call.execute()

                val bodyAlbum = response.body()

                val nuevas = mutableListOf<Cancion>()
                val items = bodyAlbum?.items ?: emptyList()

                for (i in items.indices) {
                    val track = items[i]
                    val titulo = track.name ?: "Sin título"
                    val artista = track.artists.firstOrNull()?.name ?: "Desconocido"
                    val durMs = track.durationMs
                    val min = durMs / 60000
                    val seg = (durMs % 60000) / 1000
                    val imagenUrl = track.album?.images?.firstOrNull()?.url ?:""
                    val durStr = String.format(Locale.getDefault(), "%d:%02d", min, seg)

                    nuevas.add(Cancion(titulo = titulo, top = i + 1, artista = artista, duracion = durStr, imagen = imagenUrl))
                }

                (context as? Activity)?.runOnUiThread {
                    canciones.clear()
                    canciones.addAll(nuevas)
                    notifyDataSetChanged()
                    Toast.makeText(context, "Top ${nuevas.size} cargado correctamente", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                (context as? Activity)?.runOnUiThread {
                    Toast.makeText(context, "Error: ${e.message}",Toast.LENGTH_SHORT).show(
                    )
                }
            }
        }.start()
    }

    // --------------------------------------------------------------------------------------------


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
            i.putExtra("imagen",item.imagen ?: "")
            ctx.startActivity(i)
        }
    }

    class CancionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtTitulo: TextView = view.findViewById(R.id.tv_cancion)
        val txtArtista: TextView = view.findViewById(R.id.tv_artista)
        val txtTop: TextView = view.findViewById(R.id.tv_top)
    }
}