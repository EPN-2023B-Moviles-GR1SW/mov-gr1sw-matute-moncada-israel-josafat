package com.example.b2023gr1sw

import ESqliteHelperEntrenador
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {


    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    // Logica Negocio
                    val data = result.data
                    mostrarSnackbar(
                        "${data?.getStringExtra("nombreModificado")}"
                    )
                }
            }
        }

    fun mostrarSnackbar(texto:String){
        Snackbar.make(
            findViewById(R.id.btn_sqlite),
            texto,
            Snackbar.LENGTH_LONG
        ).show()
    }

    val callbackIntentPickUri =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            result ->
            if (result.resultCode === Activity.RESULT_OK){
                if (result.data!=null){
                    val uri: Uri = result.data!!.data!!
                    val cursor = contentResolver.query(
                        uri, null, null, null, null, null
                    )
                    cursor?.moveToFirst()
                    val indiceTelefono = cursor?.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                    )
                    val telefono = cursor?.getString(indiceTelefono!!)
                    cursor?.close()
                    mostrarSnackbar("Telefono ${telefono}")
                }
            }
        }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        EBaseDeDatos.tablaEntrenador = ESqliteHelperEntrenador(this)

        val btnCicloVida = findViewById<Button>(R.id.btn_ciclo_vida)
        btnCicloVida
            .setOnClickListener {
                irActividad(ACicloVida::class.java)
            }

        val btnListView = findViewById<Button>(R.id.btn_ir_list_view)
        btnListView
            .setOnClickListener{
                irActividad(BListView::class.java)
            }
        val btnIntentImplicito = findViewById<Button>(
            R.id.btn_ir_intent_implicito
        )
        btnIntentImplicito.setOnClickListener{
            val intentConRespuesta = Intent(
                Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            )
            callbackIntentPickUri.launch(intentConRespuesta)
        }

        val btnIntentExplicito = findViewById<Button>(
            R.id.btn_ir_intent_explicito
        )
        btnIntentExplicito.setOnClickListener{
            abrirActividadConParametros(
                CIntentExplicitoParametros::class.java
            )
        }

        val botonSqlite = findViewById<Button>(R.id.btn_sqlite)
        botonSqlite.setOnClickListener{
            irActividad(ECrudEntrenador::class.java)
        }

        val botonRView = findViewById<Button>(R.id.btn_revcycler_view)
        botonRView.setOnClickListener {
            irActividad(FRecyclerView::class.java)
        }

        val botonGMaps = findViewById<Button>(R.id.btn_google_maps)
        botonGMaps.setOnClickListener {
            irActividad(GGoogleMapsActivity::class.java)
        }

        val botonFirebaseUI = findViewById<Button>(R.id.btn_intent_firebase_ui)
        botonFirebaseUI.setOnClickListener{
            irActividad(HFirebaseUIAuth::class.java)
        }

        val btnIFirestore = findViewById<Button>(R.id.btn_firestore)
        btnIFirestore.setOnClickListener{
            irActividad(IFirestore::class.java)
        }

    }

    fun abrirActividadConParametros(
        clase: Class<*>
    ){
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("nombre","Josafat")
        intentExplicito.putExtra("apellido","Matute")
        intentExplicito.putExtra("edad",21)

        callbackContenidoIntentExplicito.launch(intentExplicito)
    }

    fun irActividad (
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}
