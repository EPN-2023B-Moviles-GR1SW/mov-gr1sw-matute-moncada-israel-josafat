package com.company

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.util.*
import kotlin.random.Random

class Comida {
    var nombre: String = ""
    var id: Int = 0;
    var fechaCaducidad : Date = Date()
    var precio: Double = 0.0
    var isGourmet: Boolean = false

    constructor(
        nombre: String,
        id: Int,
        fechaCaducidad : Date,
        precio: Double,
        isGourmet: Boolean
    ){
        this.nombre = nombre
        this.id = id
        this.fechaCaducidad = fechaCaducidad
        this.precio = precio
        this.isGourmet = isGourmet
    }

    override fun toString(): String {
        return " ------------FOOD------------\n" +
                "NAME: ${this.nombre} \n" +
                "ID: ${this.id} \n" +
                "CONSUMIR HASTA: ${this.fechaCaducidad} \n" +
                "PRICE: ${this.precio} \n" +
                "GOURMET: ${this.isGourmet} \n"
    }

    companion object {
        private var listaComida = mutableListOf<Comida>()
        val dbDir = "$directorioBase/DB/comidaDB.json"

        init {
            readAll()
        }

        fun create(
            nombre: String,
            precio: Double,
            isGourmet: Boolean
        ): Comida? {
            val consumirHasta = Calendar.getInstance()
            consumirHasta.add(Calendar.MONTH, 1)
            var id = Random.nextInt(1, 1001)

            var newComida = Comida(nombre, id,consumirHasta.time,precio,isGourmet)
            listaComida.add(newComida)
            try {
                val gson = Gson()
                val jsonComidas = gson.toJson(listaComida)
                File(dbDir).writeText(jsonComidas)

                println("New Cocinero Added Succefully!! :) ")
                println(newComida.toString())
                return newComida
            }catch(ex: Exception){
                println("Error on Writing File! " + ex.message)
            }
            return null
        }

        fun readAll(): MutableList<Comida> {

            val jsonComidas = File(dbDir).readText()
            val type = object : TypeToken<List<Comida>>() {}.type
            listaComida = Gson().fromJson(jsonComidas, type)
            for (comida in listaComida) {
                println(comida)
            }
            return listaComida
        }

        fun readOne(id:Int): Comida? {
            val comida = listaComida.find { it.id == id }
            return comida
        }

        fun update(
            id:Int,
            nombre:String?,
            precio: Double?,
            isGourmet: Boolean?,
            fechaCaducidad: Date?
        ):Comida? {
            val comidaUpdate = readOne(id)
            if(comidaUpdate==null){
                println("Comida con ID ${id} no encontrado")
                return null
            }
            val gson = Gson()

            comidaUpdate.apply {
                if (nombre != null && nombre!="") {
                    this.nombre = nombre
                }
                if (fechaCaducidad != null) {
                    this.fechaCaducidad = fechaCaducidad
                }
                if (precio != null) {
                    this.precio = precio
                }
                if (isGourmet != null) {
                    this.isGourmet = isGourmet
                }
            }
            val jsonActualizado = gson.toJson(listaComida)
            File(dbDir).writeText(jsonActualizado)

            return comidaUpdate
        }

        fun delete(id:Int): Comida? {
            val comidaDelete = readOne(id)
            if(comidaDelete==null){
                println("Comida con ID ${id} no encontrado")
                return null
            }
            val gson = Gson()

            val indiceComida = listaComida.indexOfFirst { it.id == id }
            listaComida.removeAt(indiceComida)

            val jsonActualizado = gson.toJson(listaComida)

            File(dbDir).writeText(jsonActualizado)
            return comidaDelete
        }
    }
}