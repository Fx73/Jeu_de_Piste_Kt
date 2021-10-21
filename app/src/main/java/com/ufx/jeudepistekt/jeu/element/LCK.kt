package com.ufx.jeudepistekt.jeu.element

import android.content.Context
import android.widget.LinearLayout
import com.ufx.jeudepistekt.jeu.Scenario

/**
 * LCK: Instanciate a lock
 * content : lock id
 */
class LCK(content: String) : Element(content) {

    override fun instantiate(context : Context, l : LinearLayout, scenario: Scenario) {
        scenario.getEtape().lockers.add(content.trim().toInt())
    }


}