package com.ufx.jeudepistekt.jeu.element

import com.ufx.jeudepistekt.GameFragment
import com.ufx.jeudepistekt.jeu.Stage

/**
 * VAR: Instantiate a variables
 * content : variable name
 * additional 1 : operator
 * additional 2 : value
 */

class VAR(content: String, additional: Array<String>) : Element(content, additional) {

    override fun instantiate(stage: Stage) {
        var v = GameFragment.scenario.variables.values[content] ?: return

        when (additional[0]) {
            "=" -> v = additional[1].toInt()
            "+=" -> v += additional[1].toInt()
            "-=" -> v -= additional[1].toInt()
            "*=" -> v *= additional[1].toInt()
            "/=" -> v /= additional[1].toInt()
        }


        GameFragment.scenario.variables.values[content] = v

    }

}