package com.ufx.jeudepistekt

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.ufx.jeudepistekt.jeu.Scenario
import com.ufx.jeudepistekt.jeu.Stage
import com.ufx.jeudepistekt.jeu.User
import com.ufx.jeudepistekt.jeu.element.TXT
import com.ufx.jeudepistekt.tools.Storer


/**
 * The real game view, where game elements are showed
 */
class GameFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GameActivity.context = requireContext()
        GameActivity.layout = requireView().findViewById(R.id.gamelayout)

        val storer = Storer("title","creator",requireContext())
        GameActivity.scenario = Scenario.buildScenarioFromJson(storer.loadJson("ScenarioFile"))
        GameActivity.scenario.storer = storer

        val save = User.loadScenario(storer.getKey(),requireContext())

        if(save != null) {
            for (v in save.second.values)
                GameActivity.scenario.variables.values[v.key] = v.value
            GameActivity.scenario.loadStage(save.first)
        }else{
            println("No save")
            GameActivity.scenario.loadStage(GameActivity.scenario.stages[0].name)
        }
    }

    fun evaluateQr(s : String) {
        println("Qr  : $s")
        if (cheat(s))
            return

        if(GameActivity.scenario.evaluateQr(s))
            return

    }

    private fun cheat(s : String): Boolean {
        if(User.name != getString(R.string.app_author))
            return false

        if(s.startsWith("Forcer Etape ")){
            val newstep = s.substring("Forcer Etape ".length)
            GameActivity.scenario.loadStage(newstep)
            return true
        }

        if(s.startsWith("Forcer Var ")){
            val newval = s.split(" ").last()
            val variable = s.substring("Forcer Var ".length,s.length - newval.length)
            GameActivity.scenario.variables.values[variable]=newval.toInt()
            return true
        }

        if(s == "Afficher Vars"){
            val sb = StringBuilder()
            GameActivity.scenario.variables.values.forEach { (key, value) -> sb.append("$key = $value \n") }
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