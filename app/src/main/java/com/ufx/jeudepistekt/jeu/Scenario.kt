package com.ufx.jeudepistekt.jeu


class Scenario {
    lateinit var name : String
    lateinit var icon : String
    lateinit var creator : String
    lateinit var idkey : String

    val variable : MutableMap<String, Any> = mutableMapOf()
    val etapes : List<Etape> = listOf()

}