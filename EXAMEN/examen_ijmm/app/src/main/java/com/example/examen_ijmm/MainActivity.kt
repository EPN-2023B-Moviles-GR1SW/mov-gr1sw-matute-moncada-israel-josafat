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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
class MainActivity : AppCompatActivity() {

    var arreglo = Cocinero.listaCocineros
    var posItemSelected = -1
    var adaptador: ArrayAdapter<Cocinero>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DB.tableCocinero = CocineroSQLHelper(this)
        DB.tableComida = ComidaSQLHelper(this)

        val listView = findViewById<ListView>(R.id.cocineros_list_view)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )
        listView.adapter = adaptador
        adaptador!!.notifyDataSetChanged()

        //arreglo = DB.tableCocinero!!.readAllCocinerosSQL()
        readAllCocinerosFB(adaptador!!)

        val btnCrearCocinero = findViewById<Button>(R.id.btn_crear_preparacion)
        btnCrearCocinero.setOnClickListener{
            irActividad(CrearCocinero::class.java)
            adaptador!!.notifyDataSetChanged()
        }

        registerForContextMenu(listView)

    }

    override fun onResume() {
        super.onResume()
        readAllCocinerosFB(adaptador!!)
    }

    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    val data = result.data
                    val listView = findViewById<ListView>(R.id.cocineros_list_view)
                    adaptador = ArrayAdapter(
                        this,
                        android.R.layout.simple_list_item_1,
                        arreglo
                    )
                    listView.adapter = adaptador
                    adaptador!!.notifyDataSetChanged()
                }
            }
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
                    arreglo[posItemSelected].id,
                    "COCINERO_EDITAR"
                )
                return true
            }
            R.id.mi_eliminar ->{
                val cocineroSelectedID = arreglo[posItemSelected].id
                val cocineroUpdate = Cocinero.readOne(cocineroSelectedID)
                deleteCocineroFB(cocineroUpdate!!.idString)
                mostrarSnackbar("COCINERO ELIMINADO EXITOSAMENTE! ")
                readAllCocinerosFB(adaptador!!)
                return true
            }
            R.id.mi_ver ->{
                abrirActividadConParametros(
                    VerPreparacionesCocinero::class.java,
                    arreglo[posItemSelected].id,
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
        intentExplicito.putExtra(name,index)
        callbackContenidoIntentExplicito.launch(intentExplicito)
    }

    fun readAllCocinerosFB(
        adaptador: ArrayAdapter<Cocinero>
    ){
        val db = Firebase.firestore
        val referencia = db.collection("cocineros")
        adaptador.notifyDataSetChanged()
        referencia
            .get()
            .addOnSuccessListener {
                arreglo.clear()

                for (c in it){
                    c.id
                    val cocinero = Cocinero.create(
                        c.data.get("nombre") as String?:"",
                        c.data.get("salario") as Double?:0.0,
                        c.data.get("isChef") as Boolean?:false,
                        c.id
                    )
                    arreglo.add(cocinero!!)
                }
                adaptador.notifyDataSetChanged()
            }
            .addOnFailureListener {
            }
    }


    fun deleteCocineroFB(firebaseID: String){
        val db = Firebase.firestore

        val comidasCocineroRef = db.collection("comidas")

        try {
            comidasCocineroRef.whereEqualTo("cocinero",firebaseID).get()
                .addOnSuccessListener { querySnapshot ->
                    for (documento in querySnapshot) {
                        comidasCocineroRef.document(documento.id).delete()
                            .addOnSuccessListener{
                            }
                            .addOnFailureListener{}
                    }
                }
        }catch (e: Exception){
            Toast.makeText(this, "${e.message}", Toast.LENGTH_LONG)
                .show()
        }


        val referencia = db.collection("cocineros")
        referencia.document(firebaseID).delete()
            .addOnSuccessListener{
            }
            .addOnFailureListener{}
    }



}
