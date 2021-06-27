package com.ufx.jeudepistekt.jeu.elem

import android.content.Context
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import com.ufx.jeudepistekt.jeu.Scenario

class BTN(content: String, private val additional : String) : EtapElem(content) {


    override fun instantiate(context : Context, l : LinearLayout,scenario:Scenario) {
        val par = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        par.setMargins(10,10,10,10)

        val b = Button(context)
        b.text = content
        b.setOnClickListener{scenario.getEtap().evaluateButtonListener(content)}
        b.layoutParams = par

        l.addView(b)

        val rid = additional.toInt()
        scenario.getEtap().buttonwaiters[content] = rid
    }


}