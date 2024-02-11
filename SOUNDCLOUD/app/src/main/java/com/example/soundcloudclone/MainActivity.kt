package com.example.soundcloudclone

import android.content.Intent

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        irActividad(Greeting::class.java)

        var btnUpgrade = findViewById<Button>(R.id.upgrade_btn)
        btnUpgrade.setOnClickListener{
            mostrarSnackbar("UPGRADE")
        }

        var btnCast = findViewById<Button>(R.id.cast_btn)
        btnCast.setOnClickListener{
            mostrarSnackbar("CAST")
        }

        var btnUpload = findViewById<Button>(R.id.upload_btn)
        btnUpload.setOnClickListener{
            mostrarSnackbar("UPLOAD")
        }

        var btnMsg = findViewById<Button>(R.id.msg_btn)
        btnMsg.setOnClickListener{
            mostrarSnackbar("MESSAGE")
        }

        val btnNotif = findViewById<Button>(R.id.notif_btn)
        btnNotif.setOnClickListener{
            mostrarSnackbar("NOTIFICATION")
        }

    }

    fun irActividad (
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    fun mostrarSnackbar(texto:String){
        Snackbar.make(
            findViewById(R.id.inicio_tv),
            texto,
            Snackbar.LENGTH_LONG
        ).show()
    }
}
