package com.example.soundcloudclone

import android.R.color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class TrackRVAdapter(
    private val contexto: TrackRV,
    private val lista: ArrayList<String>,
    private val recyclerView: RecyclerView
):RecyclerView.Adapter<
        TrackRVAdapter.MyViewHolder
        >() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(
        view
    ){
        val nombreTextView: TextView
        val artistTextView: TextView
        val durationTextView: TextView
        val likes: Button
        val comments: Button
        val img: ImageView

        init{
            nombreTextView = view.findViewById(R.id.track_tv)
            artistTextView = view.findViewById(R.id.artist_tv)
            durationTextView = view.findViewById(R.id.duration_tv)
            likes = view.findViewById(R.id.like_btn)
            comments = view.findViewById(R.id.cmnt_btn)
            img = view.findViewById<ImageView>(R.id.track_image)

            likes.setOnClickListener{
                val currentLikes = likes.text.toString()
                val likesCount = currentLikes.toIntOrNull() ?: 0
                val newLikesCount = likesCount + 1
                likes.text = newLikesCount.toString()
            }

            comments.setOnClickListener{
                val currentCmnts = comments.text.toString()
                val cmntsCount = currentCmnts.toIntOrNull() ?: 0
                val newCmntsCount = cmntsCount + 1
                comments.text = newCmntsCount.toString()
            }


        }

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.trackrv,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val trackActual = this.lista[position]
        val datos = trackActual.split(",")

        val nombreCancion = datos[0]
        val artista = datos[1]
        val duracion = datos[2]
        val likes = datos[3]
        val comments = datos[4]
        val imagen = datos[5]

        holder.nombreTextView.text = nombreCancion
        holder.artistTextView.text = artista
        holder.durationTextView.text = duracion
        holder.likes.text = likes
        holder.comments.text = comments
        val nombreImagen = imagen.substringAfterLast("/")
        val context = holder.itemView.context
        val id: Int = context.resources.getIdentifier(nombreImagen, "drawable", context.packageName)
        holder.img.setImageResource(id)
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }


}

