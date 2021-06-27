package com.ufx.jeudepistekt

import android.content.Intent
import android.os.Bundle
import android.widget.*
import com.ufx.jeudepistekt.databinding.ActivityGameBinding
import com.ufx.jeudepistekt.jeu.EtapElem
import com.ufx.jeudepistekt.jeu.Scenario
import com.ufx.jeudepistekt.jeu.TYPE
import com.ufx.jeudepistekt.tools.Storer
import com.ufx.jeudepistekt.tools.User

class GameActivity : CommonsActivity() {
    private lateinit var binding: ActivityGameBinding
    lateinit var glayout: LinearLayout

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

    var loadStepFun =  {s:Int -> loadStep(s)}
    fun loadStep(step:Int = scenario.etape){
        scenario.etape = step
        user.saveScenario(storer.getKey(),step,scenario.variable)
        glayout.removeAllViews()
        val etape = scenario.etapes[step]
        for (e in etape.elems)
            loadElem(e)

    }

    var loadElemFun =  {e:EtapElem -> loadElem(e)}
    private fun loadElem(e : EtapElem){
        if(e.condition != "" && !scenario.evaluateCondition(e.condition))
            return

        when(e.type){
            TYPE.IMG -> e.instantiateImage(this)
            TYPE.TXT -> e.instantiateText(this)
            TYPE.VAR -> e.evaluateVar(this)
            TYPE.QRC -> e.instantiateQrWaiter(this)
            TYPE.BTN -> e.instantiateButton(this)
            TYPE.EDT -> e.instantiateEdit(this)
            TYPE.ETP -> e.instantiateEtape(this)
            TYPE.LCK -> e.instantiateLock(this)
            TYPE.UCK -> e.instantiateUnlock(this)
            TYPE.TST -> e.showToast(this)

        }


    }



    override fun evaluateQr(s : String) {
        println("Qr  : $s")
        if (cheat(s))
            return

        if(scenario.getEtap().evaluateQr(s,loadStepFun,loadElemFun))
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
            EtapElem(TYPE.TXT,sb.toString()).instantiateText(this)
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

}