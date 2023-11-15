
fun add(sum1: Int, sum2: Int):Unit{
    println("${sum1} + ${sum2} = ${sum1 + sum2}")
}
fun calcularsueldo(
    sueldo: Double,
    tasa: Double = 12.00, // Opcional (defecto)
    bonoEspecial: Double? = null , // Opcion
): Double {
    if (bonoEspecial === null){
        return sueldo * (100/tasa)
    }else{
        bonoEspecial.dec()
        return sueldo * ( 100/tasa ) + bonoEspecial
    }
}

abstract class Numeros( // Constructor PRIMARIO
    protected val numeroUno: Int,
    protected val numeroDos: Int,
    //uno: Int
){
    init {
        this.numeroUno; this.numeroDos; // this es opcional
        numeroUno; numeroDos;
        println("Inicializando")
    }
}

abstract class NumerosJAVA {
    protected val numeroUno: Int
    protected val numeroDos: Int

    constructor(numeroUno: Int, numeroDos: Int) {
        this.numeroUno = numeroUno
        this.numeroDos = numeroDos

        println("Inicializando")
    }
}


class Suma (
    uno: Int,
    dos: Int
) : Numeros (uno, dos) {
    init {
        this.numeroDos; numeroUno
        this.numeroDos; numeroUno
    }

    constructor(
        uno: Int?,
        dos: Int
    ) : this (
        if (uno==null) 0 else uno,
        dos
    ){
        numeroUno
    }

    constructor(
        uno: Int,
        dos: Int?
    ) : this (
        uno,
        if (dos==null) 0 else dos,
    ){
        numeroDos
    }
}

fun main(){
    println("Hello World")
    add(3,2)
    println(calcularsueldo(200.0,17.0))

    val sumaUno = Suma(1,2)
    val sumaDos = Suma(null, 2)
    val sumaTres = Suma(1, null)

}
