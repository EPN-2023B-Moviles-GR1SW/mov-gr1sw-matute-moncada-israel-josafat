package com.example.b2023gr1sw

class BEntrenador (
    var id: Int,
    var nombre: String?,
    var descripción: String?
){

    override fun toString(): String{
        return "${nombre} - ${descripción}"
    }
}