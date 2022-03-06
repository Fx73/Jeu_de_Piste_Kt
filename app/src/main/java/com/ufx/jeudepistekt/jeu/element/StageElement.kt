package com.ufx.jeudepistekt.jeu.element

import com.ufx.jeudepistekt.jeu.Stage
import kotlinx.serialization.Serializable

/**
 * StageElement
 * Elements used to show an item on screen
 * Constructed element
 */
@Serializable
open class StageElement(
    var type: TYPE,
    var content: String,
    private var additional: Array<String> = arrayOf()
) {
    var condition: String = ""

    open fun instantiate(stage: Stage) {
        factory(type).instantiate(stage)
    }

    private fun factory(type: TYPE): Element {
        return when (type) {
            TYPE.IMG -> IMG(content)
            TYPE.TXT -> TXT(content)
            TYPE.QRC -> QRC(content)
            TYPE.VAR -> VAR(content, additional)
            TYPE.BTN -> BTN(content, additional)
            TYPE.EDT -> EDT(content, additional)
            TYPE.ETP -> ETP(content)
            TYPE.LCK -> LCK(content)
            TYPE.UCK -> UCK(content)
            TYPE.TST -> TST(content)
        }
    }


    companion object {
        enum class TYPE {
            IMG, TXT, QRC, VAR, BTN, EDT, ETP, LCK, UCK, TST
        }
    }

}