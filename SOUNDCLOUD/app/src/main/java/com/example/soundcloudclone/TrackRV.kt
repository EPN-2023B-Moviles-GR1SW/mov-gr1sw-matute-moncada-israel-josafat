package com.example.soundcloudclone

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

class TrackRV : AppCompatActivity() {

    val tracksInfo = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_rv)

        tracksInfo.add("KILLERS FROM THE NORTHSIDE,KORDHELL,3:54,3352,48,drawable/trv1")
        tracksInfo.add("ON THE FLOOR,KVERZ,1:30,2510,56,drawable/trv2")
        tracksInfo.add("DANÇA DA MONTAGEM AUTOMOTIVA,SXID X Scythermane X Lurk,1:37,1520,23,drawable/trv3")
        tracksInfo.add("ANNIHILATE,Ariis & FXRR,2:28,1752,21,drawable/trv4")
        tracksInfo.add("SAINTS AND SINNERS,Kells,1:41,985,15,drawable/trv5")
        inicializarRecyclerView()

    }

    fun inicializarRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.tracks_rv)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
        val adaptador = TrackRVAdapter(
            this,
            tracksInfo,
            recyclerView
        )
        recyclerView.adapter = adaptador

        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        adaptador.notifyDataSetChanged()
    }

}