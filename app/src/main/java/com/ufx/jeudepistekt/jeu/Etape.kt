package com.ufx.jeudepistekt.jeu

class Etape(
    var number : Int,
    var elems : List<EtapElem>,
    var next : Map<String,Int>,
    var underelems : List<Pair<String,List<EtapElem>>> = listOf()
){
    val qrwaiters : MutableList<String>  = mutableListOf()
    val buttonwaiters : MutableList<String>  = mutableListOf()
    val lockers : MutableList<Int>  = mutableListOf()

}

