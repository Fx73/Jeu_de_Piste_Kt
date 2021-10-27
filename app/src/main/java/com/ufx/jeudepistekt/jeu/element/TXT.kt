package com.ufx.jeudepistekt.jeu.element

import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.ufx.jeudepistekt.GameActivity
import com.ufx.jeudepistekt.jeu.Stage


/**
 * TXT: Instanciate a qr code waiter
 * @param content : text text
 */

class TXT(content: String) : Element(content) {

    override fun instantiate(stage: Stage) {
        val par = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        par.setMargins(10,10,10,10)

        val tv = TextView(GameActivity.context)
        tv.textSize = 18f
        tv.text = content
        tv.layoutParams = par

        GameActivity.layout.addView(tv)
    }

}