package com.ufx.jeudepistekt.jeu.elem

import android.content.Context
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.ufx.jeudepistekt.jeu.Scenario

class EDT(content: String, private val additional1 : String, private val additional2 : String) : EtapElem(content) {


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
        b.setOnClickListener{scenario.getEtap().evaluateEditListener(content,et.text.toString(),additional1,additional2)}
        b.layoutParams = par

        l.addView(b)
    }

}