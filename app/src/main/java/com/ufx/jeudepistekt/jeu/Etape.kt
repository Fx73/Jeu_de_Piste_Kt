package com.ufx.jeudepistekt.jeu

import com.ufx.jeudepistekt.GameActivity
import com.ufx.jeudepistekt.jeu.elem.EtapElem
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
class Etape(
    var number : Int,
    var elems : List<EtapElem>,
    var next : Map<String,Int>,
    var underelems : List<List<EtapElem>> = listOf()
){
    val qrwaiters : MutableMap<String,Int>  = mutableMapOf()
    val buttonwaiters : MutableMap<String,Int>  = mutableMapOf()
    val lockers : MutableList<Int>  = mutableListOf()



//region waiters
    fun evaluateButtonListener(id:String){
        loadElemsFromWaiters(qrwaiters,id)
    }

    fun evaluateEditListener(id:String, response : String ,verif1 : String, verif2 : String){
        if(response.trim().lowercase(Locale.getDefault()) == verif1.lowercase(Locale.getDefault()) || (verif2 != "" && response.trim().lowercase(
                Locale.getDefault()) == verif2.lowercase(Locale.getDefault())))
            loadElemsFromWaiters(qrwaiters,id)

    }

    fun evaluateQr(s : String):Boolean {
        if(loadElemsFromWaiters(qrwaiters,s))
            return true

        for (w in next){
            if(w.key == s){
                if (lockers.contains(w.value)) return true
                GameActivity.instance.scenario.etape = w.value
                GameActivity.instance.loadStep()
                return true
            }
        }
        return false
    }

    private fun loadElemsFromWaiters(waiters : MutableMap<String, Int>, id : String):Boolean{
        val ue = waiters[id]?:return false

        for (e in underelems[ue])
            GameActivity.instance.loadElem(e)

        waiters.remove(id)
        return true
    }
//endregion

}

