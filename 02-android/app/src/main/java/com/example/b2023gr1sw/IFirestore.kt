package com.example.b2023gr1sw

import ICities
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle

import android.widget.ArrayAdapter

import android.widget.Button

import android.widget.ListView

import com.google.android.gms.tasks.Task

import com.google.firebase.firestore.Query

import com.google.firebase.firestore.QueryDocumentSnapshot

import com.google.firebase.firestore.QuerySnapshot

import com.google.firebase.firestore.ktx.firestore

import com.google.firebase.ktx.Firebase

import java.util.*

import kotlin.collections.ArrayList

class IFirestore : AppCompatActivity() {

    var query: Query? = null
    val arreglo: ArrayList<ICities> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ifirestore)

        val listView = findViewById<(ListView)>(R.id.lv_firestore)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val btnDatosPrueba = findViewById<Button>(R.id.btn_fs_datos_prueba)
        btnDatosPrueba.setOnClickListener{
            crearDatosPrueba()
        }

        val btnOrderBy = findViewById<Button>(R.id.btn_fs_order_by)
        btnOrderBy.setOnClickListener{
            consultarOrderBy(adaptador)
        }

        val btnObtenerDocumento = findViewById<Button>(R.id.btn_fs_odoc)
        btnObtenerDocumento.setOnClickListener{
            consultarDocumento(adaptador)
        }

        val btnIndiceCompuesto = findViewById<Button>(
            R.id.btn_fs_ind_comp
        )
        btnIndiceCompuesto.setOnClickListener{
            consultarIndiceCompuesto(adaptador)
        }

        val btnCrear = findViewById<Button>(
            R.id.btn_fs_crear
        )
        btnCrear.setOnClickListener{
            crearEjemplo()
        }

        val btnFirebaseEliminar = findViewById<Button>(
            R.id.btn_fs_eliminar
        )
        btnFirebaseEliminar.setOnClickListener{
            eliminarRegistro()
        }

        val btnFirebaseEmpezarPaginar = findViewById<Button>(
            R.id.btn_fs_epaginar
        )
        btnFirebaseEmpezarPaginar.setOnClickListener{
            query=null;
            consultarCiudades(adaptador)
        }

        val botonFirebasePaginar = findViewById<Button>(
            R.id.btn_fs_paginar
        )
        botonFirebasePaginar.setOnClickListener{
            consultarCiudades(
                adaptador
            )
        }
    }

    fun eliminarRegistro(){
        val db = Firebase.firestore
        val referenciaEjemploEstudiante = db.collection("ejemplo")
        referenciaEjemploEstudiante
            .document("1234567")
            .delete()
            .addOnSuccessListener{}
            .addOnFailureListener{}

    }

    fun consultarCiudades(
        adaptador: ArrayAdapter<ICities>
    ){
        val db = Firebase.firestore
        val citiesRef = db.collection("cities")
            .orderBy("population")
            .limit(1)
        var tarea: Task<QuerySnapshot>? = null
        if (query == null){
            tarea = citiesRef.get()
            limpiarArreglo()
           adaptador.notifyDataSetChanged()
        }else{
            tarea= query!!.get()
        }
        if(tarea!=null){
            tarea
                .addOnSuccessListener { documentSnapshots ->
                    guardarQuery(documentSnapshots, citiesRef)
                    for (ciudad in documentSnapshots){
                        anadirArregloCiudad(ciudad)
                    }
                    adaptador.notifyDataSetChanged()
                }
                .addOnFailureListener{}
        }
    }

    fun guardarQuery(
        documentSnapshot: QuerySnapshot,
        refICities: Query
    ){
        if (documentSnapshot.size()>0){
            val ultimoDocumento = documentSnapshot
                .documents[documentSnapshot.size()-1]
            query = refICities.startAfter(ultimoDocumento)
        }
    }

    fun crearEjemplo(){
        val db = Firebase.firestore
        val referenciaEjemploEstudiante = db.collection("ejemplo")
        val datosEstudiante = hashMapOf(
            "nombre" to "Josafat",
            "graduado" to false,
            "promedio" to 14.00,
            "direccion" to hashMapOf(
                "direccion" to "Destruge",
                "numeroCalle" to 1234
            ),
            "materias" to listOf("moviles")
        )

        referenciaEjemploEstudiante
            .document("1234567")
            .set(datosEstudiante)
            .addOnSuccessListener{}
            .addOnFailureListener{}

        val identificador = Date().time
        referenciaEjemploEstudiante
            .document(identificador.toString())
            .set(datosEstudiante)
            .addOnSuccessListener{}
            .addOnFailureListener{}

        referenciaEjemploEstudiante
            .add(datosEstudiante)
            .addOnSuccessListener{}
            .addOnFailureListener{}
    }

    fun consultarIndiceCompuesto(
        adaptador: ArrayAdapter<ICities>
    ){
        val db = Firebase.firestore
        val citiesRefUnico = db.collection("cities")
        limpiarArreglo()
        adaptador.notifyDataSetChanged()
        citiesRefUnico
            .whereEqualTo("capital", false)
            .whereLessThan("population",4000000)
            .orderBy("population", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                for (ciudad in it){
                    anadirArregloCiudad(ciudad)
                }
                adaptador.notifyDataSetChanged()
            }
            .addOnFailureListener{}
    }
    fun consultarDocumento(
        adaptador: ArrayAdapter<ICities>
    ){
        val db = Firebase.firestore
        val citiesRefUnico = db.collection("cities")
        limpiarArreglo()
        adaptador.notifyDataSetChanged()
        citiesRefUnico
            .document("BJ")
            .get()
            .addOnSuccessListener{
                arreglo.add(
                    ICities(
                        it.data?.get("name") as String?,
                        it.data?.get("state") as String?,
                        it.data?.get("country") as String?,
                        it.data?.get("capital") as Boolean?,
                        it.data?.get("population") as Long?,
                        it.data?.get("regions") as ArrayList<String>
                    )
                )
                adaptador.notifyDataSetChanged()
            }.addOnFailureListener{

            }
    }

    fun limpiarArreglo(){
        this.arreglo.clear()
    }

    fun anadirArregloCiudad(
        ciudad: QueryDocumentSnapshot
    ){
        val nuevaCiudad = ICities(
            ciudad.data.get("name") as String?,
            ciudad.data.get("state") as String?,
            ciudad.data.get("country") as String?,
            ciudad.data.get("capital") as Boolean?,
            ciudad.data.get("population") as Long?,
            ciudad.data.get("regions") as ArrayList<String>
            )
        arreglo.add(nuevaCiudad)
    }

    fun consultarOrderBy(
        adaptador: ArrayAdapter<ICities>
    ) {

        val db = Firebase.firestore
        val citiesRefUnico = db.collection("cities")
        limpiarArreglo()
        adaptador.notifyDataSetChanged()
        citiesRefUnico.orderBy("population", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener {
                for (ciudad in it){
                    ciudad.id
                    anadirArregloCiudad(ciudad)
                }
                adaptador.notifyDataSetChanged()
            }
            .addOnFailureListener{

            }

    }

    fun crearDatosPrueba(){
        val db = Firebase.firestore

        val cities = db.collection("cities")
        val data1 = hashMapOf(
            "name" to "San Francisco",
            "state" to "CA",
            "country" to "USA",
            "capital" to false,
            "population" to 860000,
            "regions" to listOf("west_coast", "norcal"),
        )
        cities.document("SF").set(data1)
        val data2 = hashMapOf(
            "name" to "Los Angeles",
            "state" to "CA",
            "country" to "USA",
            "capital" to false,
            "population" to 3900000,
            "regions" to listOf("west_coast", "socal"),
        )
        cities.document("LA").set(data2)
        val data3 = hashMapOf(
            "name" to "Washington D.C.",
            "state" to null,
            "country" to "USA",
            "capital" to true,
            "population" to 680000,
            "regions" to listOf("east_coast"),
        )
        cities.document("DC").set(data3)
        val data4 = hashMapOf(
            "name" to "Tokyo",
            "state" to null,
            "country" to "Japan",
            "capital" to true,
            "population" to 9000000,
            "regions" to listOf("kanto", "honshu"),
        )
        cities.document("TOK").set(data4)
        val data5 = hashMapOf(
            "name" to "Beijing",
            "state" to null,
            "country" to "China",
            "capital" to true,
            "population" to 21500000,
            "regions" to listOf("jingjinji", "hebei"),
        )
        cities.document("BJ").set(data5)
    }
}