package com.ufx.jeudepistekt.jeu

import com.ufx.jeudepistekt.GameFragment
import com.ufx.jeudepistekt.jeu.element.StageElement
import kotlinx.serialization.Serializable
import java.util.Locale.getDefault

/**
 * Scenario
 * Medium Level Game Object
 * Handle the medium level needs :
 * - Show current used element
 * - Wait for user game inputs and run the elements needed
 */
@Serializable
class Stage(
    var name: String,
    var elements: List<StageElement> = listOf(),
    var next: MutableList<String> = mutableListOf(),
    var understages: MutableList<Stage> = mutableListOf()
) {

    //region waiters
    fun evaluateButtonListener(id: String) {
        loadElementsFromWaiters(id)
    }

    fun evaluateEditListener(id: String, response: String, verifier1: String, verifier2: String) {
        if (response.trim()
                .lowercase(getDefault()) == verifier1.lowercase(getDefault()) || (verifier2 != "" && response.trim()
                .lowercase(getDefault()) == verifier2.lowercase(getDefault()))
        )
            loadElementsFromWaiters(id)

    }

    fun evaluateQrListener(s: String): Boolean {
        return loadElementsFromWaiters(s)
    }

    private fun loadElementsFromWaiters(id: String): Boolean {
        val us = understages.find { it.name == id } ?: return false

        for (e in us.elements)
            loadElement(e)

        understages.remove(us)
        return true
    }


//endregion

    /**
     * loadElement
     * Run the content of a single game element
     * Eventually, it checks before if there are some conditions and skip it if their is some which are not true
     */
    fun loadElement(e: StageElement) {
        if (e.condition == "" || GameFragment.scenario.variables.evaluateCondition(e.condition))
            e.instantiate(this)
    }


}

