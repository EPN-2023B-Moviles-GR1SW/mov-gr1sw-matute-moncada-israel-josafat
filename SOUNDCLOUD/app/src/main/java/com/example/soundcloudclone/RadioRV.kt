package com.example.soundcloudclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class RadioRV : AppCompatActivity() {

    val radioInfo = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_radio_rv)
        radioInfo.add("KILLERS FROM THE NORTHSIDE,KORDHELL,3352,48,drawable/trv1")
        radioInfo.add("ON THE FLOOR,KVERZ,2510,56,drawable/trv2")
        radioInfo.add("DANÇA DA MONTAGEM AUTOMOTIVA,SXID X Scythermane X Lurk,1520,23,drawable/trv3")
        radioInfo.add("ANNIHILATE,Ariis & FXRR,1752,21,drawable/trv4")
        radioInfo.add("SAINTS AND SINNERS,Kells,985,15,drawable/trv5")
        inicializarRecyclerView()
    }

    fun inicializarRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.radio_rv)
        val adaptador = RadioRVAdapter(
            this,
            radioInfo,
            recyclerView
        )
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        adaptador.notifyDataSetChanged()
    }
}