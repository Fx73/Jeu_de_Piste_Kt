package com.ufx.jeudepistekt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import com.ufx.jeudepistekt.databinding.ActivityGameBinding
import com.ufx.jeudepistekt.jeu.Scenario
import com.ufx.jeudepistekt.jeu.Stage
import com.ufx.jeudepistekt.jeu.element.TXT
import com.ufx.jeudepistekt.tools.Storer
import com.ufx.jeudepistekt.jeu.User

class GameActivity : CommonsActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.fab.setOnClickListener { scanQr() }
        context = this
        layout = binding.root.findViewById(R.id.gamelayout)


        val title = intent.getStringExtra("SCENARIO_TITLE")
        val creator = intent.getStringExtra("SCENARIO_CREATOR")
        if(title == null || creator == null) {
            finish()
            return
        }


        val storer = Storer(title,creator,this)
        scenario = Scenario.buildScenarioFromJson(storer.loadJson("ScenarioFile"))
        scenario.storer = storer

        val save = User.loadScenario(storer.getKey(),this)

        if(save != null) {
            for (v in save.second.values)
                scenario.variables.values[v.key] = v.value
            scenario.loadStage(save.first)
        }else{
            println("No save")
            scenario.loadStage(scenario.stages[0].name)
        }
    }




    override fun evaluateQr(s : String) {
        println("Qr  : $s")
        if (cheat(s))
            return

        if(scenario.evaluateQr(s))
            return

        super.evaluateQr(s)
    }

    private fun cheat(s : String): Boolean {
        if(User.name != getString(R.string.app_author))
            return false

        if(s.startsWith("Forcer Etape ")){
            val newstep = s.substring("Forcer Etape ".length)
            scenario.loadStage(newstep)
            return true
        }

        if(s.startsWith("Forcer Var ")){
            val newval = s.split(" ").last()
            val variable = s.substring("Forcer Var ".length,s.length - newval.length)
            scenario.variables.values[variable]=newval.toInt()
            return true
        }

        if(s == "Afficher Vars"){
            val sb = StringBuilder()
            scenario.variables.values.forEach { (key, value) -> sb.append("$key = $value \n") }
            TXT(sb.toString()).instantiate(Stage("Vars"))
            return true
        }

        return false
    }



//region swapper
    override fun swapToSettings() {
        finish()
        super.swapToSettings()
    }

    override fun swapToAbout() {
        val infoActivity = Intent(this, InfoActivity::class.java)
        infoActivity.putExtra("SCENARIO_TITLE", scenario.title)
        infoActivity.putExtra("SCENARIO_CREATOR", scenario.creator)
        infoActivity.putExtra("SCENARIO_DESCRIPTION", scenario.description)
        infoActivity.putExtra("SCENARIO_COPYRIGHT",scenario.copyright)

        startActivity(infoActivity)
    }
//endregion

    companion object{
        lateinit var context :Context
        lateinit var layout: LinearLayout
        lateinit var scenario : Scenario
    }

}