package com.ufx.jeudepistekt.jeu

class Etape(
    var number : Int,
    val variable : MutableMap<String, Any> = mutableMapOf(),
    var images : List<EtapElem> = listOf(),
    var texts : List<EtapElem> = listOf(),
    var code : String=""
)
{



}

class EtapElem(
    var content : String,
    var position : Int
)
{

}