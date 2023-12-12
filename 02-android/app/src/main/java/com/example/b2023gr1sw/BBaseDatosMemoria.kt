package com.example.b2023gr1sw

class BBaseDatosMemoria {

    companion object {
        val arregloBEntrenador = arrayListOf<BEntrenador>()

        init {
            arregloBEntrenador
                .add(
                    BEntrenador(1,"Israel","x@x.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(2,"Josafat","y@y.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(3,"Josa","z@z.com")
                )
        }
    }

}