package com.ufx.jeudepistekt.jeu.element

import android.content.Context
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import com.ufx.jeudepistekt.jeu.Scenario


/**
 * BTN: Instanciate a button
 * @param content : text of button (will be the name of the listener)
 * @param Additional = 1 - id of block of elements to run
 */
class BTN(content: String, additional: Array<String>) : Element(content, additional) {


    override fun instantiate(context : Context, l : LinearLayout,scenario:Scenario) {
        val par = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        par.setMargins(10,10,10,10)

        val b = Button(context)
        b.text = content
        b.setOnClickListener{scenario.getEtape().evaluateButtonListener(content)}
        b.layoutParams = par

        l.addView(b)

        val rid = additional[0].toInt()
        scenario.getEtape().buttonwaiters[content] = rid
    }


}