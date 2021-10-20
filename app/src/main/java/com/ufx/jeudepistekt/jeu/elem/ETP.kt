package com.ufx.jeudepistekt.jeu.elem

import android.content.Context
import android.widget.LinearLayout
import com.ufx.jeudepistekt.GameActivity
import com.ufx.jeudepistekt.jeu.Scenario

/**
 * ETP: Instanciate an Etape
 * content : step id
 */
class ETP(content: String) : Elem(content) {

    override fun instantiate(context : Context, l : LinearLayout, scenario: Scenario) {
        scenario.etape = content.toInt()
        GameActivity.instance.loadStep()
        return
    }



}