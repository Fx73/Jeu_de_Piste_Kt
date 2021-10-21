package com.ufx.jeudepistekt.jeu.element

import android.content.Context
import android.widget.LinearLayout
import com.ufx.jeudepistekt.jeu.Scenario

/**
 * UCK: Instanciate an unlock
 * content : lock name
 *
 */

class UCK(content: String) : Element(content) {

    override fun instantiate(context : Context, l : LinearLayout, scenario: Scenario) {
        scenario.getEtape().lockers.remove(content.trim().toInt())
    }


}