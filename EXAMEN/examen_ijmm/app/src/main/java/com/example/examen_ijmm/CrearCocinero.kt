package com.example.examen_ijmm

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import com.google.android.material.snackbar.Snackbar

class CrearCocinero : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cocinero)

        val btnCrearCocinero = findViewById<Button>(R.id.btn_crear)
        btnCrearCocinero
            .setOnClickListener {
                val nombre = findViewById<EditText>(R.id.input_nombre).text.toString()
                val salarioText = findViewById<EditText>(R.id.input_salario).text.toString()
                val isChef = findViewById<RadioButton>(R.id.check_si)

                if (nombre.isBlank()) {
                    mostrarSnackbar("POR FAVOR INGRESA UN NOMBRE VÁLIDO")
                } else {
                    val salario = salarioText.toDoubleOrNull() ?: 0.0

                    if (salario <= 0.0) {
                        mostrarSnackbar("POR FAVOR INGRESA UN NÚMERO MAYOR A 0.0")
                    } else {
                        val isChef = findViewById<RadioButton>(R.id.check_si)
                        var newCocinero = Cocinero.create(nombre, salario, isChef.isChecked)

                        DB.tableCocinero?.crearCocineroSQL(
                            newCocinero!!.nombre.toString(),
                            newCocinero!!.id.toInt(),
                            newCocinero!!.fechaLicencia.toString(),
                            newCocinero!!.salario.toDouble(),
                            newCocinero!!.isChef
                        )

                        irActividad(MainActivity::class.java)
                    }
                }

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
            findViewById(R.id.title_EditCocinero),
            texto,
            Snackbar.LENGTH_LONG
        )
            .show()
    }

}