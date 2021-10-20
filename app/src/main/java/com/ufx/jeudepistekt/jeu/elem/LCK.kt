package com.ufx.jeudepistekt.jeu.elem

import android.content.Context
import android.widget.LinearLayout
import com.ufx.jeudepistekt.jeu.Scenario

/**
 * LCK: Instanciate a lock
 * content : lock id
 */
class LCK(content: String) : Elem(content) {

    override fun instantiate(context : Context, l : LinearLayout, scenario: Scenario) {
        scenario.getEtap().lockers.add(content.trim().toInt())
    }


}