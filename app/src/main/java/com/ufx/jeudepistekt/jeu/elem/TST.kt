package com.ufx.jeudepistekt.jeu.elem

import android.content.Context
import android.widget.LinearLayout
import android.widget.Toast
import com.ufx.jeudepistekt.jeu.Scenario

class TST(content: String) : EtapElem(content) {

    override fun instantiate(context : Context, l : LinearLayout,scenario:Scenario) {
        showToast(context)
    }

    private fun showToast(context : Context) = Toast.makeText(context,content, Toast.LENGTH_LONG ).show()


}