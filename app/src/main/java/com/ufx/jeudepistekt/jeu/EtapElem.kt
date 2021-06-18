package com.ufx.jeudepistekt.jeu

class EtapElem(
    var type:TYPE,
    var content : String
)
{
    enum class TYPE {
        IMG, TXT
    }
}