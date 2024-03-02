package com.example.examen_ijmm

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ComidaSQLHelper(
    val contexto: Context?,
    ) : SQLiteOpenHelper(
            contexto,
      "COMIDA",
      null,
      1
    ){
    override fun onCreate(db: SQLiteDatabase?) {
        var scriptSQLCreateTableComida =
            """
                CREATE TABLE COMIDA (
                    id INTEGER PRIMARY KEY,
                    nombre VARCHAR(50),
                    fechaCaducidad VARCHAR(150),
                    precio REAL,
                    isGourmet BOOLEAN,
                    idCocinero INTEGER REFERENCES Cocinero(id)
                );
            """.trimIndent()
        db?.execSQL(scriptSQLCreateTableComida)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun crearComidaSQL (
        nombre: String,
        id: Int,
        fechaCaducidad: String,
        precio: Double,
        isGourmet: Boolean,
        idCocinero: Int
    ): Boolean {

        try {
            val writeDB = writableDatabase
            val saveValues = ContentValues()
            saveValues.put("id",id)
            saveValues.put("nombre",nombre)
            saveValues.put("fechaCaducidad", fechaCaducidad)
            saveValues.put("precio",precio)
            saveValues.put("isGourmet",isGourmet)
            saveValues.put("idCocinero",idCocinero)

            val saveResult = writeDB.insert(
                "COMIDA",
                null,
                saveValues
            )
            writeDB.close()
            Toast.makeText(contexto, "COMIDA AÑADIDO EN BD", Toast.LENGTH_SHORT).show()

            return if (saveResult.toInt()==-1) false else true
        }catch (e: Exception) {
            Toast.makeText(contexto, "Error: ${e.message}\n${e.toString()}", Toast.LENGTH_LONG).show()
            return false
        }


    }

    fun readComidaCocineroSQL (
        idCocinero: Int
    ):MutableList<Comida>{
        val preparaciones = mutableListOf<Comida>()

        try {

            val bd = readableDatabase
            val scriptQuery = """
              SELECT * FROM COMIDA WHERE idCocinero = ?
          """.trimIndent()
            val params = arrayOf(idCocinero.toString())
            val resultQuery = bd.rawQuery(
                scriptQuery,
                params
            )

            val existeComida = resultQuery.moveToFirst()
            if(existeComida){
                do{
                    val id = resultQuery.getInt(0)
                    val nombre = resultQuery.getString(1)
                    val dateCaducidadString = resultQuery.getString(2)
                    val dateCaducidad = try {
                        SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(dateCaducidadString)
                    } catch (e: Exception) {
                        Date()
                    }
                    val precio = resultQuery.getDouble(3)
                    val isGourmet = resultQuery.getInt(4) == 1
                    val comida = Comida(nombre, id, dateCaducidad, precio, isGourmet, "")
                    preparaciones.add(comida)

                }while(resultQuery.moveToNext())
            }
            resultQuery.close()
            bd.close()
        } catch (e: Exception) {
            Toast.makeText(contexto, "Error: ${e.message}\n${e.toString()}", Toast.LENGTH_LONG)
                .show()
        }

        return preparaciones

    }

    fun eliminarComidaSQL(
        id:Int
    ):Boolean {
        try {
            val writeDB = writableDatabase
            val parametersDelete = arrayOf(id.toString())
            val resultDelete = writeDB.delete(
                "COMIDA",
                "id=?",
                parametersDelete
            )
            Toast.makeText(contexto, "COMIDA ELIMINADO EN BD", Toast.LENGTH_LONG)

            writeDB.close()
            return if(resultDelete.toInt() == -1) false else true

        }catch (e: Exception) {

            Toast.makeText(contexto, "Error: ${e.message}\n${e.toString()}", Toast.LENGTH_LONG)
                .show()

            return false
        }

    }

    fun updateComidaSQL(
        nombre: String,
        id: Int,
        fechaCaducidad: String,
        precio: Double,
        isGourmet: Boolean,
        idCocinero: Int
    ):Boolean {

        try {
            val writeDB = writableDatabase
            val updateValues = ContentValues()
            updateValues.put("id", id)
            updateValues.put("nombre", nombre)
            updateValues.put("fechaCaducidad",fechaCaducidad)
            updateValues.put("precio", precio)
            updateValues.put("isGourmet", isGourmet)
            updateValues.put("idCocinero", idCocinero)

            val parametersUpdate = arrayOf(id.toString())
            val resultUpdate = writeDB.update(
                "COMIDA",
                updateValues,
                "id=?",
                parametersUpdate
            )
            Toast.makeText(contexto, "COMIDA ACTUALIZADA EN BD", Toast.LENGTH_LONG).show()
            writeDB.close()
            return if (resultUpdate.toInt() == -1) false else true
        } catch (e: Exception) {

            Toast.makeText(contexto, "Error: ${e.message}\n${e.toString()}", Toast.LENGTH_LONG)
                .show()

            return false
        }
    }


    fun readComidaSQL(id: Int): Comida? {

        try {
            val bd = readableDatabase
            val scriptQuery = """
              SELECT * FROM COMIDA WHERE ID = ?
          """.trimIndent()
            val params = arrayOf(id.toString())
            val resultQuery = bd.rawQuery(
                scriptQuery,
                params
            )

            val existeComida = resultQuery.moveToFirst()
            val comidaEncontrada = Comida("",0, Date(),0.0,false,"")
            if(existeComida){
                do{
                    val id = resultQuery.getInt(0)
                    val nombre = resultQuery.getString(1)
                    val dateCaducidadString = resultQuery.getString(2)
                    val dateCaducidad = try {
                        SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(dateCaducidadString)
                    } catch (e: Exception) {
                        Date()
                    }
                    val precio = resultQuery.getDouble(3)
                    val isGourmet = resultQuery.getInt(4) == 1
                    if(id!=null){
                        comidaEncontrada.id = id
                        comidaEncontrada.nombre = nombre
                        comidaEncontrada.fechaCaducidad = dateCaducidad
                        comidaEncontrada.precio = precio
                        comidaEncontrada.isGourmet = isGourmet
                    }
                }while(resultQuery.moveToNext())
            }
            resultQuery.close()
            bd.close()
            return comidaEncontrada
        } catch (e: Exception) {

            Toast.makeText(contexto, "E:${e.message}\n${e.toString()}", Toast.LENGTH_LONG)
                .show()

            return null
        }
    }

}