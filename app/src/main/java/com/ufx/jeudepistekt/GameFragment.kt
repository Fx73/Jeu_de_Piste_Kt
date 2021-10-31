package com.ufx.jeudepistekt

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ufx.jeudepistekt.jeu.Scenario
import com.ufx.jeudepistekt.jeu.Stage
import com.ufx.jeudepistekt.jeu.User
import com.ufx.jeudepistekt.jeu.element.TXT
import com.ufx.jeudepistekt.tools.Storer


/**
 * The real game view, where game elements are showed
 */
class GameFragment : Fragment() {
    private val args: GameFragmentArgs by navArgs()

    /**
     * onCreateView
     * Instanciate game objects
     * Load the scenario, then the save
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)

        GameFragment.context = requireContext()
        GameFragment.layout = view.findViewById(R.id.gamelayout)
        val storer = Storer(args.argumentTitle,args.argumentCreator,requireContext())
        scenario = Scenario.buildScenarioFromJson(storer.loadJson("ScenarioFile"))
        scenario.storer = storer

        val save = User.loadScenario(storer.getKey(),requireContext())
        if(save != null) {
            for (v in save.second.values)
                scenario.variables.values[v.key] = v.value
            scenario.loadStage(save.first)
        }else{
            scenario.loadStage(scenario.stages[0].name)
        }
        return view
    }

    /**
     * evaluateQr
     * Evaluate a Qr code received from Activity
     * Cheats first, the scenario used codes
     */
    fun evaluateQr(s : String) {
        if (cheat(s))
            return

        if(scenario.evaluateQr(s))
            return

    }

    /**
     * evaluateQr
     * Evaluate a Qr code received from Activity
     * Cheats first, the scenario used codes
     */
    private fun cheat(s : String): Boolean {
        if(User.name != getString(R.string.app_author))
            return false

        if(s.startsWith("Force Phase ")){
            val newstep = s.substring("Force Phase ".length)
            scenario.loadStage(newstep)
            return true
        }

        if(s.startsWith("Force Var ")){
            val newval = s.split(" ").last()
            val variable = s.substring("Force Var ".length,s.length - newval.length)
            scenario.variables.values[variable]=newval.toInt()
            return true
        }

        if(s == "Print Vars"){
            val sb = StringBuilder()
            scenario.variables.values.forEach { (key, value) -> sb.append("$key = $value \n") }
            TXT(sb.toString()).instantiate(Stage("Vars"))
            return true
        }

        return false
    }


    companion object{
        lateinit var context : Context
        lateinit var layout: LinearLayout
        lateinit var scenario : Scenario
    }
}