package com.ufx.jeudepistekt.jeu.elem

import android.content.Context
import android.widget.LinearLayout
import com.ufx.jeudepistekt.jeu.Scenario


/**
 * QRC: Instanciate a qr code waiter
 * content : qr content
 * 1 Additional = buttonwaiters.id
 */
class QRC(content: String, additional : Array<String>) : Elem(content,additional) {


    override fun instantiate(context : Context, l : LinearLayout, scenario: Scenario) {
        val rid = additional[0].toInt()
        scenario.getEtap().qrwaiters[content] = rid
    }


}