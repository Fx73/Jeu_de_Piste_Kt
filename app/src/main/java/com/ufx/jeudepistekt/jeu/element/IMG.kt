package com.ufx.jeudepistekt.jeu.element

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.ufx.jeudepistekt.GameActivity
import com.ufx.jeudepistekt.jeu.Scenario

/**
 * BTN: Instanciate an image
 * content : image name
 */
class IMG(content: String) : Element(content) {


    override fun instantiate(context : Context, l : LinearLayout,scenario:Scenario) {
        val par = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        par.setMargins(12,14,12,14)

        val iv = ImageView(context)
        iv.setImageBitmap(GameActivity.instance.storer.loadImage(content))
        iv.layoutParams = par
        iv.adjustViewBounds = true

        l.addView(iv)
    }


}