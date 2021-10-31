package com.ufx.jeudepistekt.jeu.element

import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import com.ufx.jeudepistekt.GameFragment
import com.ufx.jeudepistekt.jeu.Stage


/**
 * BTN: Instanciate a button
 * @param content : text of button (will be the name of the listener)
 * @param additional = 1 - name of under stage to run
 */
class BTN(content: String, additional: Array<String>) : Element(content, additional) {


    override fun instantiate(stage: Stage) {
        val par = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        par.setMargins(10,10,10,10)

        val b = Button(GameFragment.context)
        b.text = content
        b.setOnClickListener{stage.evaluateButtonListener(additional[0])}
        b.layoutParams = par

        GameFragment.layout.addView(b)

    }


}