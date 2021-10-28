package com.ufx.jeudepistekt.jeu.element

import com.ufx.jeudepistekt.jeu.Stage

/**
 * LCK: Instanciate a lock
 * content : lock id
 */
class LCK(content: String) : Element(content) {

    override fun instantiate(stage: Stage) {
        stage.next.remove(content.trim())
    }


}