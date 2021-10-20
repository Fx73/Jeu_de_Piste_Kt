package com.ufx.jeudepistekt.jeu.elem

import android.content.Context
import android.widget.LinearLayout
import com.ufx.jeudepistekt.jeu.Scenario

/**
 * UCK: Instanciate an unlock
 * content : lock name
 *
 */

class UCK(content: String) : Elem(content) {

    override fun instantiate(context : Context, l : LinearLayout, scenario: Scenario) {
        scenario.getEtap().lockers.remove(content.trim().toInt())
    }


}