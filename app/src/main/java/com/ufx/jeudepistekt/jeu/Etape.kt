package com.ufx.jeudepistekt.jeu

import com.ufx.jeudepistekt.GameActivity
import java.util.*

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
    fun evaluateButtonListener(id:String, loadElem:(EtapElem)-> Unit){
        loadElemsFromWaiters(qrwaiters,id,loadElem)
    }

    fun evaluateEditListener(id:String, response : String ,verif1 : String, verif2 : String,loadElem:(EtapElem)-> Unit){
        if(response.trim().lowercase() == verif1.lowercase() || (verif2 != "" && response.trim().lowercase() == verif2.lowercase()))
            loadElemsFromWaiters(qrwaiters,id,loadElem)

    }

    fun evaluateQr(s: String, loadStep:(Int)-> Unit,loadElem:(EtapElem)-> Unit):Boolean {
        if(loadElemsFromWaiters(qrwaiters,s,loadElem))
            return true

        for (w in next){
            if(w.key == s){
                if (lockers.contains(w.value)) return true
                loadStep(w.value)
                return true
            }
        }
        return false
    }

    private fun loadElemsFromWaiters(waiters : MutableMap<String, Int>, id : String,loadElem:(EtapElem)-> Unit):Boolean{
        val ue = waiters[id]?:return false

        for (e in underelems[ue])
            loadElem(e)

        waiters.remove(id)
        return true
    }
//endregion

}