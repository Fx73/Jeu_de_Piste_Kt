package com.ufx.jeudepistekt.jeu.elem

import android.content.Context
import android.widget.LinearLayout
import com.ufx.jeudepistekt.jeu.Scenario

class UCK(content: String) : EtapElem(content) {

    override fun instantiate(context : Context, l : LinearLayout, scenario: Scenario) {
        scenario.getEtap().lockers.remove(content.trim().toInt())
    }


}