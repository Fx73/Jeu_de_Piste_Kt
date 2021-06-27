package com.ufx.jeudepistekt.jeu

class Etape(
    var number : Int,
    var elems : List<EtapElem>,
    var next : Map<String,Int>,
    var underelems : List<List<EtapElem>> = listOf()
){
    val qrwaiters : MutableMap<String,Int>  = mutableMapOf()
    val buttonwaiters : MutableMap<String,Int>  = mutableMapOf()
    val lockers : MutableList<Int>  = mutableListOf()

}

