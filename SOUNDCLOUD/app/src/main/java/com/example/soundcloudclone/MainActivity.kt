package com.example.soundcloudclone

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import com.google.android.material.snackbar.Snackbar


class MainActivity : ComponentActivity() {
    @SuppressLint("WrongViewCast")
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

        val np1 = findViewById<LinearLayout>(R.id.list1)
        np1.setOnClickListener{
            mostrarSnackbar("PHONK")
        }

        val np2 = findViewById<LinearLayout>(R.id.list2)
        np2.setOnClickListener{
            mostrarSnackbar("STREET PHONK")
        }

        val np3 = findViewById<LinearLayout>(R.id.list3)
        np3.setOnClickListener{
            mostrarSnackbar("TRIBAL TRAP")
        }

        val np4 = findViewById<LinearLayout>(R.id.list4)
        np4.setOnClickListener{
            mostrarSnackbar("KAITO")
        }

        val np5 = findViewById<LinearLayout>(R.id.list5)
        np5.setOnClickListener{
            mostrarSnackbar("ZAPP BEATS")
        }

        val np6 = findViewById<LinearLayout>(R.id.list6)
        np6.setOnClickListener{
            mostrarSnackbar("JZVK9")
        }

        val masmusica1 = findViewById<LinearLayout>(R.id.mas_musica_1)
        masmusica1.setOnClickListener{
            mostrarSnackbar("MVDX & YDDN - VAMOS! MIX")
        }

        val masmusica2 = findViewById<LinearLayout>(R.id.mas_musica_2)
        masmusica2.setOnClickListener{
            mostrarSnackbar("NOT A PROBLEM! - ZAPP BEATS MIX")
        }

        val masmusica3 = findViewById<LinearLayout>(R.id.mas_musica_3)
        masmusica3.setOnClickListener{
            mostrarSnackbar("kells - IMAMONSTER MIX")
        }

        val masmusica4 = findViewById<LinearLayout>(R.id.mas_musica_4)
        masmusica4.setOnClickListener{
            mostrarSnackbar("KORDHELL - KILLERS FROM THE NORTHSIDE MIX")
        }

        val rec_escu1 = findViewById<LinearLayout>(R.id.listen_1)
        rec_escu1.setOnClickListener{
            mostrarSnackbar("Pistas que me gustan")
        }

        val rec_escu2 = findViewById<LinearLayout>(R.id.listen_2)
        rec_escu2.setOnClickListener{
            mostrarSnackbar("Trap mix")
        }


        val rec_escu3 = findViewById<LinearLayout>(R.id.listen_3)
        rec_escu3.setOnClickListener{
            mostrarSnackbar("Kaito station")
        }

        val rec_escu4 = findViewById<LinearLayout>(R.id.listen_4)
        rec_escu4.setOnClickListener{
            mostrarSnackbar("Underground mix")
        }

        val rec_escu5 = findViewById<LinearLayout>(R.id.listen_5)
        rec_escu5.setOnClickListener{
            mostrarSnackbar("Mix gym")
        }

        val rec_escu6 = findViewById<LinearLayout>(R.id.listen_6)
        rec_escu6.setOnClickListener{
            mostrarSnackbar("Street Phonk Releases")
        }

        val track = findViewById<Button>(R.id.play_track_btn)
        track.setOnClickListener{
            irActividad(TrackRV::class.java)
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
