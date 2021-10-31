package com.ufx.jeudepistekt.jeu.element

import com.ufx.jeudepistekt.jeu.Stage


/**
 * QRC: Instantiate a qr code waiter
 * content : qr content = name of the under stage to run
 */
class QRC(content: String) : Element(content) {


    override fun instantiate(stage: Stage) {
        // stage.waiters[content] = additional[0]
    }


}