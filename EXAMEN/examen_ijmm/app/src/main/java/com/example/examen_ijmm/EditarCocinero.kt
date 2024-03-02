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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
class EditarCocinero : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_cocinero)
        val cocineroUpdateID = intent.getIntExtra("COCINERO_EDITAR",0)
      val cocineroUpdate = Cocinero.readOne(cocineroUpdateID)
      val nombre = cocineroUpdate!!.nombre
      val salario = cocineroUpdate!!.salario
      val isChef = cocineroUpdate!!.isChef
      val date = cocineroUpdate!!.fechaLicencia

      val nameInput = findViewById<EditText>(R.id.input_nombre)
      nameInput.setText(nombre)

      val salarioInput = findViewById<EditText>(R.id.input_salario)
      salarioInput.setText(salario.toString())

      val isChefRadioBtn = findViewById<RadioButton>(R.id.check_si)
      val notIsChefRadioBtn = findViewById<RadioButton>(R.id.check_no)

      if (isChef){
          isChefRadioBtn.isChecked = true
      } else {
          notIsChefRadioBtn.isChecked = true
      }

      val formattedDate  = SimpleDateFormat("dd-MM-yyyy").format(date)

      val dateInput = findViewById<TextView>(R.id.editTextDate)
      dateInput.text = formattedDate

        val btnEditar = findViewById<Button>(R.id.btn_editar)
        btnEditar.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.input_nombre).text.toString()
            val salarioText = findViewById<EditText>(R.id.input_salario).text.toString()

            if (nombre.isBlank()) {
                mostrarSnackbar("POR FAVOR INGRESA UN NOMBRE VÁLIDO")
            } else {
                val salario = salarioText.toDoubleOrNull() ?: 0.0

                if (salario <= 0.0) {
                    mostrarSnackbar("POR FAVOR INGRESA UN NÚMERO MAYOR A 0.0")
                } else {
                    val isChef = findViewById<RadioButton>(R.id.check_si)
                    val dateText = findViewById<TextView>(R.id.editTextDate).text.toString()

                    try {
                        val date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(dateText)
                        var updatedCocineroDTO = Cocinero.update(cocineroUpdateID, nombre, salario, isChef.isChecked, date)
                        updateCocineroFB(updatedCocineroDTO!!,cocineroUpdate.idString )
                        devolverRespuesta(nombre)
                    } catch (e: ParseException) {
                        mostrarSnackbar("e:${e}" )
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

    fun devolverRespuesta(nombre:String){
        val intentDevolverParametros = Intent()
        intentDevolverParametros.putExtra("COCINERO_EDITAR",nombre)
        setResult(
            RESULT_OK,
            intentDevolverParametros
        )
        finish()
    }

    fun updateCocineroFB(
        dto: Cocinero,
        firebaseID: String
    ){
        val db = Firebase.firestore
        val ref = db.collection("cocineros").document(firebaseID)
        val updatedCocinero = mapOf(
            "nombre" to dto.nombre,
            "salario" to dto.salario,
            "isChef" to dto.isChef,
            "fechaLicencia" to dto.fechaLicencia.toString(),
        )

        ref.update(updatedCocinero)
            .addOnSuccessListener{}
            .addOnFailureListener{}
    }

}