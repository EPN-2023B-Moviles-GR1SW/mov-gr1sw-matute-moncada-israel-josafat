
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random


val directorioBase = System.getProperty("user.dir")

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
    }

    fun quitComida(idComida:Int){
        this.preparaciones = this.preparaciones.filter { p -> p.id !== idComida}.toMutableList()
    }

    override fun toString(): String {
        return "-----------COCINERO-------------\n" +
                "NAME: ${this.nombre} \n" +
                "ID: ${this.id} \n" +
                "LICENCIA: ${this.fechaLicencia} \n" +
                "SALARIO: ${this.salario} \n" +
                "CHEF: ${this.isChef} \n" +
                "PREPARACIONES: [ ${this.preparaciones?.map { comida -> 
                    comida.toString()
                }} ] "
    }

    companion object {
        private var listaCocineros = mutableListOf<Cocinero>()
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
                    1 -> Comida.delete(id)
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