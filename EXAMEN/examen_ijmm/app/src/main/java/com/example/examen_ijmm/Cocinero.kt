package com.example.examen_ijmm

import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.random.Random

class Cocinero{

    var nombre: String = ""
    var id: Int = 0;
    var fechaLicencia : Date = Date()
    var salario: Double = 0.0
    var isChef: Boolean = false

    constructor(
        nombre: String,
        id: Int,
        fechaLicencia : Date,
        salario: Double,
        isChef: Boolean
    ){
        this.nombre = nombre
        this.id = id
        this.fechaLicencia = fechaLicencia
        this.salario = salario
        this.isChef = isChef
    }

    override fun toString(): String {
        return "${this.nombre} \n"
    }

    companion object {
        var listaCocineros = mutableListOf<Cocinero>()

        fun create(
            nombre: String,
            salario: Double,
            isChef: Boolean
        ): Cocinero? {
            val validoHasta = Calendar.getInstance()
            validoHasta.add(Calendar.YEAR, 1)
            val id = Random.nextInt(1, 1001)
            val newCocinero = Cocinero(nombre, id, validoHasta.time, salario, isChef)
            return newCocinero
        }
        fun readAll(): MutableList<Cocinero> {
            return DB.tableCocinero?.readAllCocinerosSQL()!!
        }

        fun readOne(id:Int): Cocinero? {
            val cocinero = DB.tableCocinero?.readCocineroSQL(id)
            return cocinero
        }

        fun update(
            id:Int,
            nombre:String?,
            salario: Double?,
            isChef: Boolean?,
            fechaLicencia: Date?
        ):Cocinero? {
            val cocineroUpdate = readOne(id)
            if(cocineroUpdate==null){
                println("Cocinero con ID ${id} no encontrado")
                return null
            }
            cocineroUpdate.apply {
                if (nombre != null && nombre!="") {
                    this.nombre = nombre
                }
                if (fechaLicencia != null) {
                    this.fechaLicencia = fechaLicencia
                }
                if (salario != null) {
                    this.salario = salario
                }
                if (isChef != null) {
                    this.isChef = isChef
                }
            }
            return cocineroUpdate
        }

        fun delete(id:Int): Cocinero? {
            val cocineroDelete = readOne(id)
            if(cocineroDelete==null){
                println("Cocinero con ID ${id} no encontrado")
                return null
            }
            return cocineroDelete
        }

    }
}