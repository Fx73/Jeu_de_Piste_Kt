package com.ufx.jeudepistekt.jeu.elem

import android.content.Context
import android.widget.LinearLayout
import com.ufx.jeudepistekt.jeu.Scenario
import kotlinx.serialization.Serializable

@Serializable
abstract class EtapElem(
    var content : String,
    var condition : String = ""
    ){

    open fun instantiate(context : Context, l : LinearLayout, scenario: Scenario){
        println("THIS IS DEFAULT ELEM : IT CANNOT APPEAR !")
    }

        fun factory (type: TYPE, additional : String, additional2 : String):EtapElem{
            return when (type){
                TYPE.IMG -> IMG(content)
                TYPE.TXT -> TXT(content)
                TYPE.QRC -> QRC(content,additional)
                TYPE.VAR -> VAR(content)
                TYPE.BTN -> BTN(content,additional)
                TYPE.EDT -> EDT(content,additional, additional2)
                TYPE.ETP -> ETP(content)
                TYPE.LCK -> LCK(content)
                TYPE.UCK -> UCK(content)
                TYPE.TST -> TST(content)
            }
        }



companion object{
    enum class TYPE {
        IMG, TXT, QRC, VAR , BTN, EDT, ETP, LCK, UCK, TST
    }
}

}