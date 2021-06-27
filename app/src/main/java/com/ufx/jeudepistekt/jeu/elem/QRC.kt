package com.ufx.jeudepistekt.jeu.elem

import android.content.Context
import android.widget.LinearLayout
import com.ufx.jeudepistekt.jeu.Scenario

class QRC(content: String, private val additional : String) : EtapElem(content) {


    override fun instantiate(context : Context, l : LinearLayout, scenario: Scenario) {
        val rid = additional.toInt()
        scenario.getEtap().qrwaiters[content] = rid
    }


}