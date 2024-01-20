package com.example.examen_ijmm

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class EditarPreparacion : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_preparacion)
        val comidaUpdateID = intent.getIntExtra("PREPARACION_EDITAR",0)
        val comidaToUpdate = Comida.readOne(comidaUpdateID)
        val nombre = comidaToUpdate!!.nombre
        val precio = comidaToUpdate!!.precio
        val isGourmet = comidaToUpdate!!.isGourmet
        val date = comidaToUpdate!!.fechaCaducidad

        val nameInput = findViewById<EditText>(R.id.input_nombre)
        nameInput.setText(nombre)


        val precioInput = findViewById<EditText>(R.id.input_precio)
        precioInput.setText(precio.toString())

                val isGourmetRadioBtn = findViewById<RadioButton>(R.id.check_si)
                val notIsGourmetRadioBtn = findViewById<RadioButton>(R.id.check_no)

                if (isGourmet){
                    isGourmetRadioBtn.isChecked = true
                } else {
                    notIsGourmetRadioBtn.isChecked = true
                }

          val formattedDate  = SimpleDateFormat("dd-MM-yyyy").format(date)

          val dateInput = findViewById<TextView>(R.id.editTextDate2)
          dateInput.text = formattedDate

          val btnEditar = findViewById<Button>(R.id.btn_editar)
          btnEditar.setOnClickListener {

              val nombre = findViewById<EditText>(R.id.input_nombre).text.toString()
              val precioText = findViewById<EditText>(R.id.input_precio).text.toString()

              if (nombre.isBlank()) {
                  mostrarSnackbar("POR FAVOR INGRESA UN NOMBRE VÁLIDO")
              } else {
                  val precio = precioText.toDoubleOrNull() ?: 0.0

                  if (precio <= 0.0) {
                      mostrarSnackbar("POR FAVOR INGRESA UN NÚMERO MAYOR A 0.0")
                  } else {
                      val isGourmet = findViewById<RadioButton>(R.id.check_si)
                      val dateInput = findViewById<TextView>(R.id.editTextDate2).text.toString()
                      try {
                          Comida.update(comidaUpdateID, nombre, precio, isGourmet.isChecked, SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(dateInput))
                          devolverRespuesta()
                      }catch (e: ParseException) {
                          mostrarSnackbar("FECHA INVÁLIDA")
                      }

                  }
              }
          }

    }
    fun mostrarSnackbar(texto:String){
        Snackbar.make(
            findViewById(R.id.title_EditCocinero),
            texto,
            Snackbar.LENGTH_LONG
        )
            .show()
    }

    fun devolverRespuesta(){
        val intentDevolverParametros = Intent()
        setResult(
            RESULT_OK,
            intentDevolverParametros
        )
        finish()
    }
}