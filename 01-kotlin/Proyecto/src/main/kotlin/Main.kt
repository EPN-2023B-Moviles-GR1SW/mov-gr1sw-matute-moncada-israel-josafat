
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

    constructor(
        uno: Int?,
        dos: Int?
    ) : this (
        if (uno==null) 0 else uno,
        if (dos==null) 0 else dos
    ){
        numeroUno
    }

    public fun sumar(): Int {
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }

    companion object {
        val pi = 3.14

        fun elevarALCuadrado(num: Int): Int {
            return num * num
        }

        val historialSumas = arrayListOf<Int>()

        fun agregarHistorial(valorNuevaSuma: Int){
            historialSumas.add((valorNuevaSuma))
        }
    }
}
val arregloEstatico: Array<Int>  = arrayOf<Int>(1,2,3)
val arregloDinámico: ArrayList<Int>  = arrayListOf<Int>(1,2,3)

fun main(){
    println("Hello World")
    add(3,2)
    println(calcularsueldo(200.0,17.0))

    val sumaUno = Suma(1,2)
    val sumaDos = Suma(null, 2)
    val sumaTres = Suma(1, null)
    val sumaCuatro = Suma(null, null)
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.pi)
    print(Suma.elevarALCuadrado((2)))
    print(Suma.historialSumas)
    println(arregloEstatico)

    arregloDinámico.add(7)
    arregloDinámico.add(3)
    println(arregloDinámico)

    val respuestasForEach: Unit = arregloDinámico.forEach { value: Int ->
        println("Valor Actual ${value}")
    }

    arregloDinámico.forEach{println(it)}

    arregloEstatico.forEachIndexed{indice: Int, value: Int ->
        println("Valor ${value} indice: ${indice}")
    }

    println(respuestasForEach)

    var respuestasFilter: List<Int> = arregloDinámico.filter { value:Int ->
        val mayoresACinco: Boolean = value > 5
        return@filter mayoresACinco
    }

    println(respuestasFilter)

    val respuestasMap: List<Double> = arregloDinámico.map{ value: Int ->
        return@map value.toDouble() + 100.0
    }

    println(respuestasMap)

    val respuestaAny: Boolean = arregloDinámico.any { value: Int ->
        return@any (value > 5)
    }
    println(respuestaAny) // true
    val respuestaAll: Boolean = arregloDinámico.all { value: Int ->
        return@all (value > 5)
    }

    println(respuestaAll) // true

    val respuestaReduce: Int = arregloDinámico.reduce{acum: Int, value: Int ->
        return@reduce (acum + value)
    }

    println(respuestaReduce)

}
