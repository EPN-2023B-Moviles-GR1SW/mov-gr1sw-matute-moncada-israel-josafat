package com.example.examen_ijmm

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
        return "${this.nombre} \n"
    }

    companion object {
        var listaComida = mutableListOf<Comida>()

        fun create(
            nombre: String,
            precio: Double,
            isGourmet: Boolean
        ): Comida? {
            val consumirHasta = Calendar.getInstance()
            consumirHasta.add(Calendar.MONTH, 1)
            var id = Random.nextInt(1, 1001)
            var newComida = Comida(nombre, id,consumirHasta.time,precio,isGourmet)
            return newComida
        }

        fun readOne(id:Int): Comida? {
            val comida = DB.tableComida?.readComidaSQL(id)
            return comida
        }

        fun update(
            id:Int,
            nombre:String?,
            precio: Double?,
            isGourmet: Boolean?,
            fechaCaducidad: Date?,
        ):Comida? {
            val comidaUpdate = readOne(id)
            if(comidaUpdate==null){
                println("Comida con ID ${id} no encontrado")
                return null
            }

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

            return comidaUpdate
        }

        fun delete(id:Int): Comida? {
            val comidaDelete = readOne(id)
            if(comidaDelete==null){
                println("Comida con ID ${id} no encontrado")
                return null
            }
            //val indiceComida = listaComida.indexOfFirst { it.id == id }
            //listaComida.removeAt(indiceComida)
            return comidaDelete
        }
    }
}