package com.example.examen_ijmm

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    val arreglo = Cocinero.listaCocineros
    var posItemSelected = -1

    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    //ACTUALIZAR NAME
                    val data = result.data
                    val listView = findViewById<ListView>(R.id.cocineros_list_view)
                    val adaptador = ArrayAdapter(
                        this,
                        android.R.layout.simple_list_item_1,
                        arreglo
                    )
                    listView.adapter = adaptador
                    adaptador.notifyDataSetChanged()
                    mostrarSnackbar("COCINERO ACTUALIZADO: ${data?.getStringExtra("COCINERO_EDITAR")} ")

                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.cocineros_list_view)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val btnCrearCocinero = findViewById<Button>(R.id.btn_crear_preparacion)
        btnCrearCocinero.setOnClickListener{
            irActividad(CrearCocinero::class.java)
            adaptador.notifyDataSetChanged()
        }

        registerForContextMenu(listView)

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu,menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posItemSelected = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mi_editar ->{
                abrirActividadConParametros(
                    EditarCocinero::class.java,
                    posItemSelected,
                    "COCINERO_EDITAR"
                )
                return true
            }
            R.id.mi_eliminar ->{
                val cocineroSelectedID = Cocinero.listaCocineros[posItemSelected].id
                Cocinero.delete(cocineroSelectedID)
                val listView = findViewById<ListView>(R.id.cocineros_list_view)
                val adaptador = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    arreglo
                )
                listView.adapter = adaptador
                adaptador.notifyDataSetChanged()
                mostrarSnackbar("COCINERO ELIMINADO EXITOSAMENTE! ")
                return true
            }
            R.id.mi_ver ->{
                abrirActividadConParametros(
                    VerPreparacionesCocinero::class.java,
                    posItemSelected,
                    "COCINERO_EDITAR"
                )
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun mostrarSnackbar(texto:String){
        Snackbar.make(
            findViewById(R.id.cocinero_name),
            texto,
            Snackbar.LENGTH_LONG
        )
            .show()
    }

    fun irActividad (
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    fun abrirActividadConParametros(
        clase: Class<*>,
        index: Int,
        name: String
    ){
        val intentExplicito = Intent(this, clase)
        val cocineroSelectedID = Cocinero.listaCocineros[index].id
        intentExplicito.putExtra(name,cocineroSelectedID)
        callbackContenidoIntentExplicito.launch(intentExplicito)
    }

}
