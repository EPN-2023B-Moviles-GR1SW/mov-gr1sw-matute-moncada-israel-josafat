package com.example.examen_ijmm

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
        update(this.id, this.nombre, this.salario, this.isChef, this.fechaLicencia, this.preparaciones)

    }

    fun quitComida(idComida: Int) {
        this.preparaciones = this.preparaciones.filter { p -> p.id != idComida }.toMutableList()
        update(this.id, this.nombre, this.salario, this.isChef, this.fechaLicencia, this.preparaciones)
    }
    override fun toString(): String {
        return "${this.nombre} \n"
    }

    companion object {
        var listaCocineros = mutableListOf<Cocinero>()

        init {
            this.create("Josafat Matute",1750.50, false)
            this.create("Victoria Villa",2150.50, true)
            this.create("Daniela Velez",1925.10, true)
            this.create("Virgilio Perez Torres",1220.50, true)
            this.create("Diego Montes",2300.50, false)

            var index = 0
            for (cocinero in listaCocineros){
                cocinero.addComida(Comida.readAll()[index].id)
                cocinero.addComida(Comida.readAll()[index+1].id)
                index += 2
            }
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
            return newCocinero
        }

        fun readAll(): MutableList<Cocinero> {
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
            return cocineroUpdate
        }

        fun delete(id:Int): Cocinero? {
            val cocineroDelete = readOne(id)
            if(cocineroDelete==null){
                println("Cocinero con ID ${id} no encontrado")
                return null
            }

            val indiceCocinero = listaCocineros.indexOfFirst { it.id == id }
            listaCocineros.removeAt(indiceCocinero)

            return cocineroDelete
        }

    }
}