package com.ufx.jeudepistekt.jeu.element

import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.ufx.jeudepistekt.GameFragment
import com.ufx.jeudepistekt.jeu.Stage


/**
 * EDT: Instantiate a edit text
 * content : text to show
 * 3 Additional = 1- name of under stage to run / 2 -  answer possible / 3 - answer possible
 */

class EDT(content: String, additional: Array<String>) : Element(content, additional) {


    override fun instantiate(stage: Stage) {
        val par = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        par.setMargins(10, 10, 10, 10)

        val et = EditText(GameFragment.context)
        et.textSize = 16f
        et.layoutParams = par
        et.setSingleLine()

        GameFragment.layout.addView(et)

        val b = Button(GameFragment.context)
        b.text = content
        b.setOnClickListener {
            stage.evaluateEditListener(
                additional[0],
                et.text.toString(),
                additional[1],
                additional[2]
            )
        }
        b.layoutParams = par

        GameFragment.layout.addView(b)
    }

}