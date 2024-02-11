package com.example.soundcloudclone

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class ImagenListAdapter(
    private val contexto: Context,
    private val listaImagenes: Array<Int>,
    private val listaTextos: Array<String>
) : ArrayAdapter<Int>(contexto, R.layout.list_item_with_image_and_text, listaImagenes) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater.inflate(R.layout.list_item_with_image_and_text, parent, false)

        val imageView = itemView.findViewById<ImageView>(R.id.logoArt_iv)
        val textView = itemView.findViewById<TextView>(R.id.nameArt_iv)

        imageView.setImageResource(listaImagenes[position])
        textView.text = listaTextos[position]

        return itemView
    }
}
