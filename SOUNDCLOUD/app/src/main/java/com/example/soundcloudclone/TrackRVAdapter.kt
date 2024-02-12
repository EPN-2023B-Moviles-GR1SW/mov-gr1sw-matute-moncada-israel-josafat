package com.example.soundcloudclone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

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

        init{
            nombreTextView = view.findViewById(R.id.track_tv)
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
        holder.nombreTextView.text = trackActual
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }
}