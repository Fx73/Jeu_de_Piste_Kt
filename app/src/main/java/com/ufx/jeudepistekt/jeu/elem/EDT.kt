package com.ufx.jeudepistekt.jeu.elem

import android.content.Context
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.ufx.jeudepistekt.jeu.Scenario


/**
 * EDT: Instanciate a edit text
 * content : text to show
 * 2 Additional = answer 1 / answer 2
 */

class EDT(content: String, additional : Array<String>) : Elem(content, additional) {


    override fun instantiate(context : Context, l : LinearLayout,scenario:Scenario) {
        val par = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        par.setMargins(10,10,10,10)

        val et = EditText(context)
        et.textSize = 16f
        et.layoutParams = par
        et.setSingleLine()

        l.addView(et)

        val b = Button(context)
        b.text = content
        b.setOnClickListener{scenario.getEtap().evaluateEditListener(content,et.text.toString(),additional[0],additional[0])}
        b.layoutParams = par

        l.addView(b)
    }

}