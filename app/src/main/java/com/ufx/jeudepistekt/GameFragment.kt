package com.ufx.jeudepistekt

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.ufx.jeudepistekt.jeu.Scenario
import com.ufx.jeudepistekt.jeu.Stage
import com.ufx.jeudepistekt.jeu.User
import com.ufx.jeudepistekt.jeu.User.resetScenario
import com.ufx.jeudepistekt.jeu.element.TXT
import com.ufx.jeudepistekt.tools.Storer


/**
 * The real game view, where game elements are showed
 */
class GameFragment : Fragment() {
    private val args: GameFragmentArgs by navArgs()

    /**
     * onCreateView
     * Instantiate game objects
     * Load the scenario, then the save
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)

        @Suppress("RemoveRedundantQualifierName")
        GameFragment.layout = view.findViewById(R.id.gamelayout)
        GameFragment.context = requireContext()

        val storer = Storer(args.argumentTitle, args.argumentCreator, requireContext())
        scenario = Scenario.buildScenarioFromJson(storer.loadJson("ScenarioFile"))
        scenario.storer = storer

        val save = User.loadScenario(storer.getKey(), requireContext())
        if (save != null) {
            for ((key, value) in save.second.variablesvalues)
                scenario.variables.variablesvalues[key] = value
            scenario.loadStage(save.first)
        } else {
            scenario.loadStage(scenario.stages[0].name)
        }
        return view
    }

    /**
     * evaluateQr
     * Evaluate a Qr code received from Activity
     * Cheats first, the scenario used codes
     */
    fun evaluateQr(s: String) {
        if (cheat(s))
            return

        if (scenario.evaluateQr(s))
            return

    }

    /**
     * evaluateQr
     * Evaluate a Qr code received from Activity
     * Cheats first, the scenario used codes
     */
    private fun cheat(s: String): Boolean {
        if (s.startsWith("Reset All")) {
            resetScenario(scenario.storer.getKey())
        }

        if (s.startsWith("Force Phase ")) {
            scenario.loadStage(s.substring("Force Phase ".length))
            return true
        }

        if (s.startsWith("Force Var ")) {
            val variable = s.substring("Force Var ".length).trim().split(" ").first()
            val value = s.split(" ").last()
            scenario.variables.variablesvalues[variable] = value.toInt()
            return true
        }

        if (s == "Print Vars") {
            val sb = StringBuilder()
            scenario.variables.variablesvalues.forEach { (key, value) -> sb.append("$key = $value \n") }
            TXT(sb.toString()).instantiate(Stage("Vars"))
            return true
        }

        return false
    }


    companion object {
        lateinit var scenario: Scenario

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        @SuppressLint("StaticFieldLeak")
        lateinit var layout: LinearLayout
    }
}