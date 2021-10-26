package com.ufx.jeudepistekt.jeu.element

import android.content.Context
import android.widget.LinearLayout
import com.ufx.jeudepistekt.jeu.Scenario
import kotlinx.serialization.Serializable

@Serializable
open class EtapElement(
    var type : TYPE,
    var content : String,
    var additional: Array<String> = arrayOf(),
    var condition : String = ""
    ){

    open fun instantiate(context : Context, l : LinearLayout, scenario: Scenario){
        factory(type).instantiate(context,l,scenario)
    }

    fun factory (type: TYPE):Element{
            return when (type){
                TYPE.IMG -> IMG(content)
                TYPE.TXT -> TXT(content)
                TYPE.QRC -> QRC(content,additional)
                TYPE.VAR -> VAR(content)
                TYPE.BTN -> BTN(content,additional)
                TYPE.EDT -> EDT(content,additional)
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