package com.ufx.jeudepistekt.jeu.element

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.ufx.jeudepistekt.GameFragment
import com.ufx.jeudepistekt.jeu.Stage

/**
 * BTN: Instanciate an image
 * @param content : image name
 */
class IMG(content: String) : Element(content) {


    override fun instantiate(stage: Stage) {
        val par = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        par.setMargins(12,14,12,14)

        val iv = ImageView(GameFragment.context)
        iv.setImageBitmap(GameFragment.scenario.storer.loadImage(content))
        iv.layoutParams = par
        iv.adjustViewBounds = true

        GameFragment.layout.addView(iv)
    }


}