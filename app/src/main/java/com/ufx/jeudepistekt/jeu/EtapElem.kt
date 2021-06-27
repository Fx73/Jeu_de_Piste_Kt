package com.ufx.jeudepistekt.jeu

import android.view.ViewGroup
import android.widget.*
import com.ufx.jeudepistekt.GameActivity

class EtapElem(
    var type: TYPE,
    var content : String,
    var condition : String = "",
    var additional1: String = "",
    var additional2: String = "",

    ){

    fun instantiateText(game : GameActivity)
    {
        val tv = TextView(game)
        tv.textSize = 18f
        tv.text = content
        val par = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        par.setMargins(10,10,10,10)

        tv.layoutParams = par

        game.glayout.addView(tv)
    }

    fun instantiateImage(game : GameActivity)
    {
        val iv = ImageView(game)
        iv.setImageBitmap(game.storer.loadImage(content))
        val par = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        par.setMargins(12,14,12,14)
        iv.layoutParams = par
        iv.adjustViewBounds = true

        game.glayout.addView(iv)
    }


    fun instantiateEdit(game : GameActivity)
    {
        val par = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        par.setMargins(10,10,10,10)

        val et = EditText(game)
        et.textSize = 16f
        et.layoutParams = par
        et.setSingleLine()

        game.glayout.addView(et)

        val b = Button(game)
        b.text = content
        b.setOnClickListener{game.scenario.getEtap().evaluateEditListener(content,et.text.toString(),additional1,additional2,game.loadElemFun)}
        b.layoutParams = par

        game.glayout.addView(b)

    }


    fun instantiateButton(game : GameActivity)
    {
        val par = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        par.setMargins(10,10,10,10)

        val b = Button(game)
        b.text = content
        b.setOnClickListener{game.scenario.getEtap().evaluateButtonListener(content,game.loadElemFun)}
        b.layoutParams = par

        game.glayout.addView(b)

        val rid = additional1.toInt()
        game.scenario.getEtap().buttonwaiters[content] = rid
    }


    fun showToast(game : GameActivity)= Toast.makeText(game,content, Toast.LENGTH_LONG ).show()


    fun instantiateEtape(game : GameActivity){
        game.loadStep(content.toInt())
        return
    }

    fun instantiateLock (game : GameActivity) {
        game.scenario.getEtap().lockers.add(content.trim().toInt())
    }

    fun instantiateUnlock (game : GameActivity) {
        game.scenario.getEtap().lockers.remove(content.trim().toInt())
    }

    fun instantiateQrWaiter(game : GameActivity){
        val rid = additional1.toInt()
        game.scenario.getEtap().qrwaiters[content] = rid

    }


    fun evaluateVar (game : GameActivity){
        val split = content.split("=")
        var v = game.scenario.variable[split[0]] ?: return

        if(split.size == 2){
            v = split[1].toInt()
        }

        if(split.size == 3){
            when (split[1]){
                "+" -> v+= split[2].toInt()
                "-" -> v-= split[2].toInt()
                "*" -> v*= split[2].toInt()
                "/" -> v/= split[2].toInt()
            }
        }

        game.scenario.variable[split[0]] = v
    }
}