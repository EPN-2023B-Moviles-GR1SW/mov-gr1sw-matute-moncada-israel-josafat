package com.company
import java.text.SimpleDateFormat
import java.util.*


val directorioBase = System.getProperty("user.dir")!!

fun menuCocinero() {
    while (true) {
        println("\nMenú de Cocinero")
        println("1. CREAR COCINERO")
        println("2. VER TODOS LOS COCINEROS")
        println("3. ACTUALIZAR UN COCINERO")
        println("4. ASIGNAR PREPARACIONES A UN COCINERO")
        println("5. ELIMINAR UN COCINERO")
        println("6. VOLVER")
        print("Seleccione una opción: ")

        when (readLine()?.toIntOrNull()) {
            1 -> {
                println("\nCREATE COCINERO")
                print("NOMBRE: ")
                val name = readLine() ?: ""
                print("SALARIO: ")
                val salario = readLine()?.toDoubleOrNull() ?: 0.0
                print("CHEF: (true - false): ")
                val isChef = readLine()?.toBoolean() ?: false
                if(name=="") {
                    println("NO SE PUEDE CREAR COCINERO CON NOMBRE VACIO")
                    break
                }
                Cocinero.create(name, salario, isChef)
                println("COCINERO CREADO :)")
            }
            2 -> {
                println("\nREAD COCINEROS")
                Cocinero.readAll()
            }
            3 -> {
                println("\nUPDATE COCINERO")
                Cocinero.readAll()
                println("Ingresar el ID del cocinero que deseas Actualizar")
                val id = readLine()?.toIntOrNull() ?: 0
                val cocinero = Cocinero.readOne(id)
                if(cocinero==null){
                    println("COCINERO CON ID ${id} NO ENCONTRADO")
                    break
                }
                println("CONICERO A ACTUALIZAR: ${cocinero.id} - ${cocinero.nombre} ")
                println("------------ACTUALIZACIÓN DE INFORMACIÓN--------")
                println("Si no deseas actualizar un campo presiona ENTER")
                print("NOMBRE: ")
                val name = readlnOrNull()
                print("SALARIO: ")
                val salario = readlnOrNull()?.toDoubleOrNull()
                print("CHEF: (true - false): ")
                val isChef = readlnOrNull()?.toBoolean() ?: false
                print("FECHA LICENCIA: dd/MM/yyyy")
                val fechaStr = readlnOrNull()?.trim()

                try {
                    val formato = SimpleDateFormat("dd/MM/yyyy")
                    val fecha: Date? = formato.parse(fechaStr)
                    if (fecha != null) {
                        Cocinero.update(id,name,salario,isChef,fecha,null)
                        println("COCINERO ACTUALIZADO :)")
                    }
                } catch (e: Exception) {
                    println("Formato de fecha incorrecto.")
                    Cocinero.update(id,name,salario,isChef,null,null)
                    println("COCINERO ACTUALIZADO (Se Mantiene la misma Fecha):)")

                }
            }
            4 -> {
                println("\nASIGNAR PREPARACIÓN A COCINERO")
                Cocinero.readAll()
                println("Ingresar el ID del cocinero al que deseas asginar preparaciones")
                val id = readLine()?.toIntOrNull() ?: 0
                val cocinero = Cocinero.readOne(id)
                if(cocinero==null){
                    println("COCINERO CON ID ${id} NO ENCONTRADO")
                    break
                }
                println("COCINERO: ${cocinero.id} - ${cocinero.nombre} ")
                val listaComidas = Comida.readAll()

                println("AGREGAR O ELIMINAR PREPARACIONES")
                println("1. ASIGNAR")
                println("2. ELIMINAR")

                when (readLine()?.toIntOrNull()) {
                    1 -> {
                        while (true) {
                            println("\nLISTA DE PREPARACIONES DISPONIBLES:")
                            listaComidas.forEach { comida ->
                                if (!cocinero.preparaciones.contains(comida)){
                                    println("ID: ${comida.id} - Nombre: ${comida.nombre}")
                                }
                            }

                            println("0. SALIR AL MENÚ PRINCIPAL")
                            print(
                                "Seleccione el ID de la preparación que desea asignar al cocinero ${cocinero.id}" +
                                        "\nIngrese 0 para volver al menú principal: "
                            )

                            val opcion = readLine()?.toIntOrNull() ?: continue

                            if (opcion == 0) {
                                return
                            } else {
                                val comidaSeleccionada = listaComidas.find { it.id == opcion }
                                if (comidaSeleccionada != null) {
                                    println("Comida seleccionada: ${comidaSeleccionada.nombre}")
                                    cocinero.addComida(comidaSeleccionada.id)
                                    Cocinero.update(cocinero.id,null,null,null,null,cocinero.preparaciones)
                                } else {
                                    println("ID de comida no válido. Intente de nuevo.")
                                }
                            }
                        }

                    }
                    2-> {
                        while (true) {
                            println("\nLISTA DE PREPARACIONES DEL COCINERO ${cocinero.nombre}:")
                            cocinero.preparaciones.forEach { comida ->
                                println("ID: ${comida.id} - Nombre: ${comida.nombre}")
                            }

                            println("0. SALIR AL MENÚ PRINCIPAL")
                            print(
                                "Seleccione el ID de la preparación que desea asignar al cocinero ${cocinero.id}" +
                                        "\nIngrese 0 para volver al menú principal: "
                            )

                            val opcion = readLine()?.toIntOrNull() ?: continue

                            if (opcion == 0) {
                                return
                            } else {
                                val comidaSeleccionada = listaComidas.find { it.id == opcion }
                                if (comidaSeleccionada != null) {
                                    println("Comida seleccionada: ${comidaSeleccionada.nombre}")
                                    cocinero.quitComida(comidaSeleccionada.id)
                                    Cocinero.update(cocinero.id,null,null,null,null,cocinero.preparaciones)
                                } else {
                                    println("ID de comida no válido. Intente de nuevo.")
                                }
                            }
                        }

                    }
                    else -> {
                        println("ASIGNACION DE PREPARACIONES CANCELADO")
                        break
                    }
                }

            }
            5 -> {
                println("\nDELETE COCINERO")
                Cocinero.readAll()
                println("Ingresar el ID del cocinero que deseas ELIMINAR")
                val id = readLine()?.toIntOrNull() ?: 0
                val cocinero = Cocinero.readOne(id)
                if(cocinero==null){
                    println("COCINERO CON ID ${id} NO ENCONTRADO")
                    break
                }
                println("CONICERO A ELIMINAR: ${cocinero.id} - ${cocinero.nombre} ")
                println("¿Estas seguro que deseae Eliminar el cocinero con ID: ${cocinero.id}?")
                println("1. SI")
                println("2. NO")
                when (readLine()?.toIntOrNull()) {
                    1 -> Cocinero.delete(id)
                    else -> println("DELETE CANCELADO")
                }
            }
            6 -> {
                return
            }
            else -> println("Opción no válida. Intente de nuevo.")
        }
    }
}

fun menuComida() {
    while (true) {
        println("\nMenú de Comida")
        println("1. CREAR COMIDA")
        println("2. VER TODAS LAS COMIDAS")
        println("3. ACTUALIZAR UNA COMIDA")
        println("4. ELIMINAR UNA COMIDA")
        println("5. VOLVER")
        print("Seleccione una opción: ")

        when (readLine()?.toIntOrNull()) {
            1 -> {
                println("\nCREATE COMIDA")
                print("NOMBRE: ")
                val name = readLine() ?: ""
                print("PRECIO: ")
                val precio = readLine()?.toDoubleOrNull() ?: 0.0
                print("GOURMET: (true - false): ")
                val isGourmet = readLine()?.toBoolean() ?: false
                if(name=="") {
                    println("NO SE PUEDE CREAR COMIDA CON NOMBRE VACIO")
                    break
                }
                Comida.create(name, precio, isGourmet)
                println("COMIDA CREADA :)")
            }
            2 -> {
                println("\nREAD COMIDAS")
                Comida.readAll()
            }
            3 -> {
                println("\nUPDATE COMIDA")
                Comida.readAll()
                println("Ingresar el ID de la comida que deseas Actualizar")
                val id = readLine()?.toIntOrNull() ?: 0
                val comida = Comida.readOne(id)
                if(comida==null){
                    println("COMIDA CON ID ${id} NO ENCONTRADA")
                    break
                }
                println("COMIDA A ACTUALIZAR: ${comida.id} - ${comida.nombre} ")
                println("------------ACTUALIZACIÓN DE INFORMACIÓN--------")
                println("Si no deseas actualizar un campo presiona ENTER")
                print("NOMBRE: ")
                val name = readlnOrNull()
                print("PRECIO: ")
                val precio = readlnOrNull()?.toDoubleOrNull()
                print("GOURMET: (true - false): ")
                val isGourmet = readlnOrNull()?.toBoolean() ?: false
                print("FECHA CADUCIDAD: dd/MM/yyyy")
                val fechaStr = readlnOrNull()?.trim()
                try {
                    val formato = SimpleDateFormat("dd/MM/yyyy")
                    val fecha: Date? = formato.parse(fechaStr)
                    if (fecha != null) {
                        Comida.update(id,name,precio,isGourmet,fecha)
                        println("COMIDA ACTUALIZADA :)")
                    }

                } catch (e: Exception) {
                    println("Formato de fecha incorrecto.")
                    Comida.update(id,name,precio,isGourmet,null)
                    println("COMIDA ACTUALIZADA (Se Mantiene la misma Fecha de Caducidad):)")

                }
                Cocinero.listaCocineros.map {cocinero ->
                    cocinero.preparaciones.map {prep ->
                        if(prep.id==comida.id){
                            cocinero.quitComida(comida.id)
                            cocinero.addComida(prep.id)
                        }
                    }

                }
            }
            4 -> {
                println("\nDELETE COMIDA")
                Comida.readAll()
                println("Ingresar el ID de la comida que deseas ELIMINAR")
                val id = readLine()?.toIntOrNull() ?: 0
                val comida = Comida.readOne(id)
                if(comida==null){
                    println("COMIDA CON ID ${id} NO ENCONTRADO")
                    break
                }
                println("COMIDA A ELIMINAR: ${comida.id} - ${comida.nombre} ")
                println("¿Estas seguro que deseae Eliminar la comida con ID: ${comida.id}?")
                println("1. SI")
                println("2. NO")
                when (readLine()?.toIntOrNull()) {
                    1 -> {
                        Comida.delete(id)
                        Cocinero.listaCocineros.map {cocinero ->
                            cocinero.preparaciones.map {prep ->
                                if(prep.id==comida.id){
                                    cocinero.quitComida(prep.id)
                                }
                            }

                        }
                    }
                    else -> println("DELETE CANCELADO")
                }
            }
            5 -> {
                return
            }
            else -> println("Opción no válida. Intente de nuevo.")
        }
    }
}

fun main() {
    while (true) {
        println("Menu Principal")
        println("1. COCINERO")
        println("2. COMIDA")
        println("3. SALIR")
        print("Seleccione una opción: ")

        when (readLine()?.toIntOrNull()) {
            1 -> menuCocinero()
            2 -> menuComida()
            3 -> {
                println("------------ FIN APP ------------")
                return
            }
            else -> println("Opción no válida. Intente de nuevo.")
        }
    }
}