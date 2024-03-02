package com.example.examen_ijmm

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CocineroSQLHelper(
    val contexto: Context?,
): SQLiteOpenHelper(
    contexto,
    "COCINERO",
    null,
    1
) {



    override fun onCreate(db: SQLiteDatabase?) {
        var scriptSQLCreateTableCocinero =
            """
                CREATE TABLE COCINERO (
                id INTEGER PRIMARY KEY,
                nombre VARCHAR(50),
                fechaLicencia VARCHAR(150),
                salario REAL,
                isChef BOOLEAN 
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCreateTableCocinero)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun crearCocineroSQL (
        nombre: String,
        id: Int,
        fechaLicencia: String,
        salario: Double,
        isChef: Boolean
    ): Boolean {

        try {
            val writeDB = writableDatabase

            val saveValues = ContentValues()
            saveValues.put("id",id)
            saveValues.put("nombre",nombre)
            saveValues.put("fechaLicencia", fechaLicencia)
            saveValues.put("salario",salario)
            saveValues.put("isChef",isChef)
            val saveResult = writeDB.insert(
                "COCINERO",
                null,
                saveValues
            )

            writeDB.close()
            Toast.makeText(contexto, "COCINERO AÑADIDO EN BD", Toast.LENGTH_SHORT).show()
            return if (saveResult.toInt()==-1) false else true

        }catch (e: Exception) {
            Toast.makeText(contexto, "Error: ${e.message}\n${e.toString()}", Toast.LENGTH_LONG).show()
            return false
        }

    }


    fun readAllCocinerosSQL():MutableList<Cocinero>{

        val scriptQuery = """
        SELECT * FROM COCINERO
    """.trimIndent()

        val cocineros = mutableListOf<Cocinero>()
        try {
            val bd = readableDatabase
            val resultQuery = bd.rawQuery(scriptQuery, null)
            val existeUsuario = resultQuery.moveToFirst()

            if (existeUsuario) {
                do {
                    val id = resultQuery.getInt(0)
                    val nombre = resultQuery.getString(1)
                    val dateLicenciaString = resultQuery.getString(2)
                    val dateLicencia = try {
                        SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(dateLicenciaString)
                    } catch (e: Exception) {
                        Date()
                    }
                    val salario = resultQuery.getDouble(3)
                    val isChef = resultQuery.getInt(4) == 1
                    val cocinero = Cocinero(nombre, id, dateLicencia, salario, isChef,"")
                    cocineros.add(cocinero)
                } while (resultQuery.moveToNext())
            }
            resultQuery.close()
            bd.close()

        }catch (e: Exception) {
            Toast.makeText(contexto, "Error: ${e.message}\n${e.toString()}", Toast.LENGTH_LONG).show()
        }
        return cocineros
    }

      fun eliminarCocineroSQL(
          id:Int
      ):Boolean {
          try {
              val writeDB = writableDatabase
              val parametersDelete = arrayOf(id.toString())
              val resultDelete = writeDB.delete(
                  "COCINERO",
                  "id=?",
                  parametersDelete
              )
              Toast.makeText(contexto, "COCINERO ELIMINADO EN BD", Toast.LENGTH_LONG).show()
              writeDB.close()
              return if (resultDelete.toInt() == -1) false else true
          } catch (e: Exception) {

              Toast.makeText(contexto, "Error: ${e.message}\n${e.toString()}", Toast.LENGTH_LONG)
                  .show()

              return false
          }

      }


      fun updateCocineroSQL(
          nombre: String,
          id: Int,
          fechaLicencia: String,
          salario: Double,
          isChef: Boolean
      ):Boolean {

          try {
              val writeDB = writableDatabase
              val updateValues = ContentValues()
              updateValues.put("id", id)
              updateValues.put("nombre", nombre)
              updateValues.put("fechaLicencia",fechaLicencia)
              updateValues.put("salario", salario)
              updateValues.put("isChef", isChef)
              val parametersUpdate = arrayOf(id.toString())
              val resultUpdate = writeDB.update(
                  "COCINERO",
                  updateValues,
                  "id=?",
                  parametersUpdate
              )
              Toast.makeText(contexto, "COCINERO ACTUALIZADO EN BD", Toast.LENGTH_LONG).show()
              writeDB.close()
              return if (resultUpdate.toInt() == -1) false else true
          } catch (e: Exception) {

              Toast.makeText(contexto, "Error: ${e.message}\n${e.toString()}", Toast.LENGTH_LONG)
                  .show()

              return false
          }
      }

    fun readCocineroSQL(id: Int): Cocinero? {


        try {

        val bd = readableDatabase
        val scriptQuery = """
              SELECT * FROM COCINERO WHERE ID = ?
          """.trimIndent()
        val params = arrayOf(id.toString())
        val resultQuery = bd.rawQuery(
            scriptQuery,
            params
        )

        val existeUsuario = resultQuery.moveToFirst()
        val usuarioEncontrado = Cocinero("",0, Date(),0.0,false,"")
        val arreglo = arrayListOf<Cocinero>()
        if(existeUsuario){
            do{
                val id = resultQuery.getInt(0)
                val nombre = resultQuery.getString(1)
                val dateLicenciaString = resultQuery.getString(2)
                val dateLicencia = try {
                    SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(dateLicenciaString)
                } catch (e: Exception) {
                    Date()
                }
                val salario = resultQuery.getDouble(3)
                val isChef = resultQuery.getInt(4) == 1

                if(id!=null){
                    usuarioEncontrado.id = id
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.fechaLicencia = dateLicencia
                    usuarioEncontrado.salario = salario
                    usuarioEncontrado.isChef = isChef
                }
            }while(resultQuery.moveToNext())
        }
        resultQuery.close()
        bd.close()
        return usuarioEncontrado
        } catch (e: Exception) {

            Toast.makeText(contexto, "Error: ${e.message}\n${e.toString()}", Toast.LENGTH_LONG)
                .show()

            return null
        }
    }

}

    /*


       */

