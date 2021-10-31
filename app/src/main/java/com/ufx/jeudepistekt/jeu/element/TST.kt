package com.ufx.jeudepistekt.jeu.element

import android.content.Context
import android.widget.Toast
import com.ufx.jeudepistekt.GameFragment
import com.ufx.jeudepistekt.jeu.Stage

/**
 * TST: Instantiate a toast
 * content : toast text
 *
 */
class TST(content: String) : Element(content) {

    override fun instantiate(stage: Stage) {
        showToast(GameFragment.context)
    }

    private fun showToast(context: Context) =
        Toast.makeText(context, content, Toast.LENGTH_LONG).show()


}