package com.example.soundcloudclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

class RadioRV : AppCompatActivity() {

    val radioInfo = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_radio_rv)
        radioInfo.add("Daily Street Phonk Mix,Street Phonk Records,7852,158,drawable/rv1")
        radioInfo.add("Daily Megamix,For You,3213,196,drawable/rv2")
        radioInfo.add("Sigilkore Krushfunk Mix,Soundcloud,1780,33,drawable/rv3")
        radioInfo.add("Tribal Trap Station,Tribal Trap,2531,78,drawable/rv4")
        radioInfo.add("Daily Mix 2,For you,1236,27,drawable/rv5")
        inicializarRecyclerView()
    }

    fun inicializarRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.radio_rv)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
        val adaptador = RadioRVAdapter(
            this,
            radioInfo,
            recyclerView
        )
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        adaptador.notifyDataSetChanged()
    }
}