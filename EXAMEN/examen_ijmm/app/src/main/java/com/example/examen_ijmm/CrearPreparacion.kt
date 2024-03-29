package com.example.examen_ijmm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
class CrearPreparacion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_preparacion)
        val cocineroUpdateID = intent.getIntExtra("COMIDA",0)
        val cocineroUpdate = Cocinero.readOne(cocineroUpdateID)
        val btnCrearPreparacion = findViewById<Button>(R.id.btn_crear)
        btnCrearPreparacion
            .setOnClickListener {
                val nombre = findViewById<EditText>(R.id.input_nombre).text.toString()
                val precioText = findViewById<EditText>(R.id.input_precio).text.toString()

                if (nombre.isBlank()) {
                    mostrarSnackbar("POR FAVOR INGRESA UN NOMBRE VÁLIDO")
                } else {
                    val precio = precioText.toDoubleOrNull() ?: 0.0

                    if (precio <= 0.0) {
                        mostrarSnackbar("POR FAVOR INGRESA UN NÚMERO MAYOR A 0.0")
                    } else {
                        val isgourmet = findViewById<RadioButton>(R.id.check_si)

                        val newComida = Comida.create(nombre, precio, isgourmet.isChecked,"",cocineroUpdate!!.idString)!!
                        val db = Firebase.firestore
                        val referencia = db.collection("comidas")
                        val nuevaComida = hashMapOf(
                            "nombre" to newComida!!.nombre,
                            "precio" to newComida!!.precio,
                            "isGourmet" to newComida!!.isGourmet,
                            "fechaCaducidad" to newComida!!.fechaCaducidad.toString(),
                            "id" to newComida!!.id,
                            "cocinero" to cocineroUpdate!!.idString
                        )
                        referencia.add(nuevaComida)
                            .addOnSuccessListener{
                                mostrarSnackbar("Comida Creada en FBase :)")
                            }
                            .addOnFailureListener{}
                        finish()
                    }
                }

            }

    }

    fun mostrarSnackbar(texto:String){
        Snackbar.make(
            findViewById(R.id.title_addPreparacion),
            texto,
            Snackbar.LENGTH_LONG
        )
            .show()
    }
}