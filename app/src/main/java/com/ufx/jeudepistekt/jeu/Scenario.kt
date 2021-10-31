package com.ufx.jeudepistekt.jeu

import com.ufx.jeudepistekt.BuildConfig
import com.ufx.jeudepistekt.GameFragment.Companion.context
import com.ufx.jeudepistekt.GameFragment.Companion.layout
import com.ufx.jeudepistekt.tools.Storer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * Scenario
 * High Level Game Object
 * Contain all the data used to play
 * Handle the high level needs :
 * - Saving
 * - changing stage
 * - link the low level game class to fragment
 */
@Serializable
class Scenario(
    var title: String,
    var creator: String,
    var description: String,
    var copyright: String,
    var version: String = BuildConfig.VERSION_NAME,
    val variables: Variables = Variables(),
    val stages: List<Stage> = listOf()
) {
    @Transient
    lateinit var storer: Storer


    private var stage = stages[0]

    /**
     * loadStage
     * Instantiate all elements of a stage in the view
     * Save the game state
     */
    fun loadStage(name: String) {
        stage = stages.firstOrNull { it.name == name } ?: stages.first()
        User.saveScenario(storer.getKey(), stage.name, variables, context)
        layout.removeAllViews()
        for (e in stage.elements)
            stage.loadElement(e)
    }


    /**
     * evaluateQr
     * Called by activity intent Qr. Contain the Qr scanned
     * Check if usable inside Stage, if not, try to use it to change stage
     */
    fun evaluateQr(s: String): Boolean {
        if (stage.evaluateQrListener(s))
            return true

        for (w in stage.next) {
            if (w == s) {
                loadStage(s)
                return true
            }
        }
        return false
    }


    companion object {
        /**
         * buildScenarioFromJson
         * Get a Scenario class from a Json String
         */
        fun buildScenarioFromJson(jsonFile: String): Scenario {
            return Json.decodeFromString(jsonFile)
        }
    }
}