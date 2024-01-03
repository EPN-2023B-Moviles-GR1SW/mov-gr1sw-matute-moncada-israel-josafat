package com.example.b2023gr1sw

import EBaseDeDatos
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

class ECrudEntrenador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ecrud_entrenador)

        val btnBuscarBDD = findViewById<Button>(R.id.btn_buscar_bdd)
        btnBuscarBDD.setOnClickListener{
            val id = findViewById<EditText>(R.id.input_id)
            val nombre = findViewById<EditText>(R.id.input_nombre)
            val descripcion = findViewById<EditText>(R.id.input_descripcion)

            val entrenador = EBaseDeDatos.tablaEntrenador!!
                .consultarEntrenadorPorID(id.text.toString().toInt())

            id.setText(entrenador.id.toString())
            nombre.setText(entrenador.nombre)
            descripcion.setText(entrenador.descripción)
        }

        val btnCrearBDD = findViewById<Button>(R.id.btn_crear_bdd)
        btnCrearBDD.setOnClickListener{
            val nombre = findViewById<EditText>(R.id.input_nombre)
            val descripcion = findViewById<EditText>(R.id.input_descripcion)
            val respuesta = EBaseDeDatos.tablaEntrenador!!.crearEntrenador(
                nombre.text.toString(),
                descripcion.text.toString()
            )
            if(respuesta) mostrarSnackbar("ENT. CREADO")
        }

        val btnActualizarBDD = findViewById<Button>(R.id.btn_actualizar_bdd)
        btnActualizarBDD.setOnClickListener{
            val id = findViewById<EditText>(R.id.input_id)
            val nombre = findViewById<EditText>(R.id.input_nombre)
            val descripcion = findViewById<EditText>(R.id.input_descripcion)
            val respuesta = EBaseDeDatos.tablaEntrenador!!.actualizarEntrenador(
                nombre.text.toString(),
                descripcion.text.toString(),
                id.text.toString().toInt()
            )
            if (respuesta) mostrarSnackbar("USUARIO ACTUALIZADO")
        }

        val btnEliminarBDD = findViewById<Button>(R.id.btn_eliminar_bdd)
        btnEliminarBDD.setOnClickListener{
            val id = findViewById<EditText>(R.id.input_id)
            val respuesta = EBaseDeDatos.tablaEntrenador!!.eliminarEntrenador(
                id.text.toString().toInt()
            )
            if (respuesta) mostrarSnackbar("USUARIO ELIMINADO")
        }
    }

    fun mostrarSnackbar(txt: String){
        Snackbar.make(
            findViewById(R.id.cl_sqlite),
            txt,
            Snackbar.LENGTH_LONG
        ).show()
    }
}