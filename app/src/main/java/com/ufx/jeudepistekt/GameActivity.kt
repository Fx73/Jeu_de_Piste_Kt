package com.ufx.jeudepistekt

import android.content.Intent
import android.os.Bundle
import android.widget.*
import com.ufx.jeudepistekt.databinding.ActivityGameBinding
import com.ufx.jeudepistekt.jeu.Scenario
import com.ufx.jeudepistekt.jeu.element.EtapElement
import com.ufx.jeudepistekt.jeu.element.TXT
import com.ufx.jeudepistekt.tools.Storer
import com.ufx.jeudepistekt.tools.User

class GameActivity : CommonsActivity() {
    private lateinit var binding: ActivityGameBinding
    private lateinit var glayout: LinearLayout

    lateinit var scenario : Scenario
    lateinit var storer : Storer
    private lateinit var user : User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        glayout = binding.root.findViewById(R.id.gamelayout)
        binding.fab.setOnClickListener { scanQr() }

        instance = this

        val title = intent.getStringExtra("SCENARIO_TITLE")
        val creator = intent.getStringExtra("SCENARIO_CREATOR")
        if(title == null || creator == null) {
            finish()
            return
        }


        storer = Storer(title,creator,this)
        scenario = Scenario.buildScenarioFromJson(storer.loadJson("ScenarioFile"))

        user = User(this)
        val save = user.loadScenario(storer.getKey())

        if(save != null) {
            scenario.etape = save.first
            for (v in save.second)
                scenario.variable[v.key] = v.value as Int
        }else{
            println("No save")
        }

        loadStep()
    }


    fun loadStep(){
        user.saveScenario(storer.getKey(),scenario.etape,scenario.variable)
        glayout.removeAllViews()
        val etape = scenario.etapes[scenario.etape]
        for (e in etape.elements)
            loadElem(e)
    }
    fun loadElem(e : EtapElement) {
        if(e.condition == "" || scenario.evaluateCondition(e.condition))
            e.instantiate(this,glayout,scenario)
    }



    override fun evaluateQr(s : String) {
        println("Qr  : $s")
        if (cheat(s))
            return

        if(scenario.getEtape().evaluateQr(s))
            return

        super.evaluateQr(s)
    }

    private fun cheat(s : String): Boolean {
        if(User.name != getString(R.string.app_author))
            return false

        if(s.startsWith("Forcer Etape ")){
            val newstep = s.substring("Forcer Etape ".length).toInt()
            scenario.etape = newstep
            loadStep()
            return true
        }

        if(s.startsWith("Forcer Var ")){
            val newval = s.split(" ").last()
            val variable = s.substring("Forcer Var ".length,s.length - newval.length)
            scenario.variable[variable]=newval.toInt()
            loadStep()
            return true
        }

        if(s == "Afficher Vars"){
            val sb = StringBuilder()
            scenario.variable.forEach { (key, value) -> sb.append("$key = $value \n") }
            TXT(sb.toString()).instantiate(this,glayout,scenario)
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
        lateinit var instance :GameActivity
    }

}