package com.company

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.util.*
import kotlin.random.Random

class Cocinero{

    var nombre: String = ""
    var id: Int = 0;
    var fechaLicencia : Date = Date()
    var salario: Double = 0.0
    var isChef: Boolean = false

    var preparaciones:MutableList<Comida> = mutableListOf<Comida>()

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

    fun addComida(idComida:Int){
        val preparacion = Comida.readOne(idComida)
        if (preparacion != null) {
            this.preparaciones.add(preparacion)
        }
        Cocinero.update(this.id, this.nombre, this.salario, this.isChef, this.fechaLicencia, this.preparaciones)

    }

    fun quitComida(idComida: Int) {
        this.preparaciones = this.preparaciones.filter { p -> p.id != idComida }.toMutableList()
        Cocinero.update(this.id, this.nombre, this.salario, this.isChef, this.fechaLicencia, this.preparaciones)
    }
    override fun toString(): String {
        return "-----------COCINERO-------------\n" +
                "NAME: ${this.nombre} \n" +
                "ID: ${this.id} \n" +
                "LICENCIA: ${this.fechaLicencia} \n" +
                "SALARIO: ${this.salario} \n" +
                "CHEF: ${this.isChef} \n" +
                "PREPARACIONES: [ ${this.preparaciones?.map { comida ->
                    comida.id.toString() + " - " + comida.nombre + " \n"
                }} ] "
    }

    companion object {
        var listaCocineros = mutableListOf<Cocinero>()
        val dbDir = "$directorioBase/DB/cocineroDB.json"

        init {
            readAll()
        }

        fun create(
            nombre: String,
            salario: Double,
            isChef: Boolean
        ): Cocinero? {
            val validoHasta = Calendar.getInstance()
            validoHasta.add(Calendar.YEAR, 1)
            var id = Random.nextInt(1, 1001)
            var newCocinero = Cocinero(nombre, id,validoHasta.time,salario,isChef)
            listaCocineros.add(newCocinero)
            try {
                val gson = Gson()
                val jsonCocineros = gson.toJson(listaCocineros)
                File(dbDir).writeText(jsonCocineros)

                println("New Cocinero Added Succefully!! :) ")
                println(newCocinero.toString())
                return newCocinero
            }catch(ex: Exception){
                println("Error on Writing File! " + ex.message)
            }
            return null
        }

        fun readAll(): MutableList<Cocinero> {

            val cocinerosJson = File(dbDir).readText()
            val type = object : TypeToken<List<Cocinero>>() {}.type
            listaCocineros = Gson().fromJson(cocinerosJson, type)
            for (cocinero in listaCocineros) {
                println(cocinero)
            }
            return listaCocineros
        }

        fun readOne(id:Int): Cocinero? {
            val cocinero = listaCocineros.find { it.id == id }
            return cocinero
        }

        fun update(
            id:Int,
            nombre:String?,
            salario: Double?,
            isChef: Boolean?,
            fechaLicencia: Date?,
            preparaciones: MutableList<Comida>?
        ):Cocinero? {
            val cocineroUpdate = readOne(id)
            if(cocineroUpdate==null){
                println("Cocinero con ID ${id} no encontrado")
                return null
            }
            val gson = Gson()

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
                preparaciones?.let { this.preparaciones = it }
            }
            val jsonActualizado = gson.toJson(listaCocineros)
            File(dbDir).writeText(jsonActualizado)

            return cocineroUpdate
        }

        fun delete(id:Int): Cocinero? {
            val cocineroDelete = readOne(id)
            if(cocineroDelete==null){
                println("Cocinero con ID ${id} no encontrado")
                return null
            }
            val gson = Gson()

            val indiceCocinero = listaCocineros.indexOfFirst { it.id == id }
            listaCocineros.removeAt(indiceCocinero)

            val jsonActualizado = gson.toJson(listaCocineros)

            File(dbDir).writeText(jsonActualizado)
            return cocineroDelete
        }

    }
}
