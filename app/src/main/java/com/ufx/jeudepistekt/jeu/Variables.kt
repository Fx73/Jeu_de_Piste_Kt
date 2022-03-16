package com.ufx.jeudepistekt.jeu

import kotlinx.serialization.Serializable

/**
 * Variable class
 * Used to contain scenario variable
 * can evaluate simple conditions on them
 */
@Serializable
class Variables {
    val variablesvalues: MutableMap<String, Int> = mutableMapOf()


    /**
     * Evaluate a condition between variables
     * For now, can evaluate :
     * a == b
     * a != b
     * x && y
     * x || y
     */
    fun evaluateCondition(cond: String): Boolean {
        var all = true
        val aa = cond.split("&&")
        for (a in aa) {
            val bb = a.split("||")
            var ball = false
            for (b in bb) {
                val c = if (b.contains("==")) b.split("==") else b.split("!=")

                val c0 = variablesvalues[c[0].trim()] ?: c[0].trim().toInt()
                val c1 = variablesvalues[c[1].trim()] ?: c[1].trim().toInt()

                ball = ball || (c0 == c1)
            }
            all = all && ball
        }
        return all
    }
}