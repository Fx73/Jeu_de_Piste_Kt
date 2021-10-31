package com.ufx.jeudepistekt.jeu.element

import com.ufx.jeudepistekt.GameFragment
import com.ufx.jeudepistekt.jeu.Scenario
import com.ufx.jeudepistekt.jeu.Stage

/**
 * ETP: Instanciate an Etape
 * content : step id
 */
class ETP(content: String) : Element(content) {

    override fun instantiate(stage: Stage) {
        GameFragment.scenario.loadStage(content)
        return
    }



}