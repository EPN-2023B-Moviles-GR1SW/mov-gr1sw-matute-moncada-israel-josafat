package com.example.examen_ijmm

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioButton
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import kotlin.reflect.typeOf

class VerPreparacionesCocinero : AppCompatActivity() {
    private lateinit var arreglo: MutableList<Comida>
    private lateinit var cocineroUpdate: Cocinero

    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    val listView = findViewById<ListView>(R.id.preparaciones_list_view)
                    val adaptador = ArrayAdapter(
                        this,
                        android.R.layout.simple_list_item_1,
                        arreglo
                    )
                    listView.adapter = adaptador
                    adaptador.notifyDataSetChanged()
                    mostrarSnackbar("PREPARACIÓN ACTUALIZADA CORRECTAMENTE!")

                }
            }
        }
    var posItemSelected = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_preparaciones_cocinero)


        val cocineroUpdateID = intent.getIntExtra("COCINERO_EDITAR",0)
        cocineroUpdate = Cocinero.readOne(cocineroUpdateID)!!
        val nombre = cocineroUpdate!!.nombre

        val nameInput = findViewById<TextView>(R.id.cocinero_name)
        nameInput.setText(nombre)
        arreglo = cocineroUpdate.preparaciones

        val listView = findViewById<ListView>(R.id.preparaciones_list_view)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        registerForContextMenu(listView)

        val btnCrearPreparacion= findViewById<Button>(R.id.btn_crear_preparacion)
        btnCrearPreparacion
            .setOnClickListener {
                irActividad(CrearPreparacion::class.java, cocineroUpdate.id)

            }

        registerForContextMenu(listView)

    }

    override fun onResume() {
        super.onResume()
        actualizarListView()

    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_preparacion,menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posItemSelected = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mi_editar ->{
                abrirActividadConParametros(EditarPreparacion::class.java,arreglo[posItemSelected].id.toString().toInt(),"PREPARACION_EDITAR")
                return true
            }
            R.id.mi_eliminar ->{
                cocineroUpdate.quitComida(arreglo[posItemSelected].id.toString().toInt())
                Comida.delete(arreglo[posItemSelected].id.toString().toInt())
                arreglo.removeAt(posItemSelected)
                actualizarListView()
                mostrarSnackbar("Preparación eliminada correctamente")
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun actualizarListView() {
        val listView = findViewById<ListView>(R.id.preparaciones_list_view)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
    }


    fun mostrarSnackbar(texto:String){
        Snackbar.make(
            findViewById(R.id.preparaciones_cocineros_listview),
            texto,
            Snackbar.LENGTH_LONG
        )
            .show()
    }

    fun irActividad (
        clase: Class<*>,
        index: Int,

    ){
        val intent = Intent(this, clase)
        val cocineroSelectedID = Cocinero.readOne(index)!!.id
        intent.putExtra("COMIDA",cocineroSelectedID)
        startActivity(intent)

    }

    fun abrirActividadConParametros(
        clase: Class<*>,
        id: Int,
        name: String
    ){
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra(name,id)
        callbackContenidoIntentExplicito.launch(intentExplicito)

    }

}