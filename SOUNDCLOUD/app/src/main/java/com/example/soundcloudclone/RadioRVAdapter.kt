package com.example.soundcloudclone

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RadioRVAdapter(
    private val contexto: RadioRV,
    private val lista: ArrayList<String>,
    private val recyclerView: RecyclerView
): RecyclerView.Adapter<
        RadioRVAdapter.MyViewHolder
        >() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(
        view
    ){
        val nombreTextView: TextView
        val artistTextView: TextView
        val likes: Button
        val comments: Button
        val img: ImageView
        val constraintLayout: androidx.constraintlayout.widget.ConstraintLayout

        init{
            nombreTextView = view.findViewById(R.id.trackRd_tv)
            artistTextView = view.findViewById(R.id.artistRd_tv)
            likes = view.findViewById(R.id.likeRd_btn)
            comments = view.findViewById(R.id.cmntRd_btn)
            img = view.findViewById<ImageView>(R.id.trackRd_image)
            constraintLayout = view.findViewById(R.id.parentRvConstraintLayout)

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
                R.layout.radiorv,
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

        val nombreRadio = datos[0]
        val artista = datos[1]
        val likes = datos[2]
        val comments = datos[3]
        val imagen = datos[4]

        holder.nombreTextView.text = nombreRadio
        holder.artistTextView.text = artista
        holder.likes.text = likes
        holder.comments.text = comments
        val nombreImagen = imagen.substringAfterLast("/")
        val context = holder.itemView.context
        val id: Int = context.resources.getIdentifier(nombreImagen, "drawable", context.packageName)
        holder.img.setImageResource(id)

        val bitmap = BitmapFactory.decodeResource(context.resources, id)
        val averageColor = getAverageColor(bitmap)

        holder.constraintLayout.setBackgroundColor(averageColor)
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }

    private fun getAverageColor(bitmap: Bitmap): Int {
        var redBucket = 0
        var greenBucket = 0
        var blueBucket = 0
        var pixelCount = 0

        for (y in 0 until bitmap.height) {
            for (x in 0 until bitmap.width) {
                val c = bitmap.getPixel(x, y)

                pixelCount++
                redBucket += android.graphics.Color.red(c)
                greenBucket += android.graphics.Color.green(c)
                blueBucket += android.graphics.Color.blue(c)
            }
        }

        val red = redBucket / pixelCount
        val green = greenBucket / pixelCount
        val blue = blueBucket / pixelCount

        return android.graphics.Color.rgb(red, green, blue)
    }

}