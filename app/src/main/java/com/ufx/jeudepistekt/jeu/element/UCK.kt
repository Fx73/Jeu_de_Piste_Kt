package com.ufx.jeudepistekt.jeu.element

import com.ufx.jeudepistekt.jeu.Stage

/**
 * UCK: Instantiate an unlock
 * content : lock name
 *
 */

class UCK(content: String) : Element(content) {

    override fun instantiate(stage: Stage) {
        stage.next.add(content.trim())
    }


}