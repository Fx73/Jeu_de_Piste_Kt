package com.ufx.jeudepistekt.jeu.element

import com.ufx.jeudepistekt.GameFragment
import com.ufx.jeudepistekt.jeu.Stage

/**
 * VAR: Instanciate a variables
 * content : variables content : "var=value"
 */

class VAR(content: String) : Element(content) {

    override fun instantiate(stage: Stage) {
        val split = content.split("=")
        var v = GameFragment.scenario.variables.values[split[0]] ?: return

        if(split.size == 2){
            v = split[1].toInt()
        }

        if(split.size == 3){
            when (split[1]){
                "+" -> v+= split[2].toInt()
                "-" -> v-= split[2].toInt()
                "*" -> v*= split[2].toInt()
                "/" -> v/= split[2].toInt()
            }
        }

        GameFragment.scenario.variables.values[split[0]] = v

    }

}