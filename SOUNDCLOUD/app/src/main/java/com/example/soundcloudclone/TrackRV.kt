package com.example.soundcloudclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class TrackRV : AppCompatActivity() {

    val tracksInfo = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_rv)
        tracksInfo.add("KILLERS FROM THE NORTHSIDE - KORDHELL - 3:54")
        tracksInfo.add("ON THE FLOOR - KVERZ - Duración: 1:30")
        tracksInfo.add("DANÇA DA MONTAGEM AUTOMOTIVA - SXID, Scythermane & Lurk - 1:37")
        tracksInfo.add("ANNIHILATE - Ariis & FXRR  - 2:28")
        tracksInfo.add("SAINTS AND SINNERS - Kells - 1:41")
        inicializarRecyclerView()

    }

    fun inicializarRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.tracks_rv)
        val adaptador = TrackRVAdapter(
            this,
            tracksInfo,
            recyclerView
        )
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        adaptador.notifyDataSetChanged()
    }
}