package com.ufx.jeudepistekt.jeu.elem

import android.content.Context
import android.widget.LinearLayout
import com.ufx.jeudepistekt.jeu.Scenario

class LCK(content: String) : EtapElem(content) {

    override fun instantiate(context : Context, l : LinearLayout, scenario: Scenario) {
        scenario.getEtap().lockers.add(content.trim().toInt())
    }


}