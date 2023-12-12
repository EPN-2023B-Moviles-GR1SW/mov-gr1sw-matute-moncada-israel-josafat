package com.example.b2023gr1sw

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnCicloVida = findViewById<Button>(R.id.btn_ciclo_vida)
        btnCicloVida
            .setOnClickListener {
                irActividad(ACicloVida::class.java)
            }

        val btnListView = findViewById<Button>(R.id.btn_ir_list_view)
            .setOnClickListener{
                irActividad(BListView::class.java)
            }
    }

    fun irActividad (
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}
