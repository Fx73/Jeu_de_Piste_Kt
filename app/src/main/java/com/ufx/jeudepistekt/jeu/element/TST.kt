package com.ufx.jeudepistekt.jeu.element

import android.content.Context
import android.widget.LinearLayout
import android.widget.Toast
import com.ufx.jeudepistekt.jeu.Scenario

/**
 * TST: Instanciate a toast
 * content : toast text
 *
 */
class TST(content: String) : Element(content) {

    override fun instantiate(context : Context, l : LinearLayout,scenario:Scenario) {
        showToast(context)
    }

    private fun showToast(context : Context) = Toast.makeText(context,content, Toast.LENGTH_LONG ).show()


}