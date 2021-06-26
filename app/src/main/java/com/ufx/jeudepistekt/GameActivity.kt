package com.ufx.jeudepistekt

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.*
import com.ufx.jeudepistekt.databinding.ActivityGameBinding
import com.ufx.jeudepistekt.jeu.EtapElem
import com.ufx.jeudepistekt.jeu.Scenario
import com.ufx.jeudepistekt.jeu.TYPE
import com.ufx.jeudepistekt.tools.Storer
import com.ufx.jeudepistekt.tools.User
import java.util.*

class GameActivity : CommonsActivity() {
    private lateinit var binding: ActivityGameBinding
    private lateinit var glayout: LinearLayout

    private lateinit var scenario : Scenario
    private lateinit var storer : Storer
    private lateinit var user : User

    private var step = 0
    private fun getEtap () = scenario.etapes[step]

    private var ltpar = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
    private var lipar = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        glayout = binding.root.findViewById(R.id.gamelayout)
        binding.fab.setOnClickListener { scanQr() }
        ltpar.setMargins(10,10,10,10)
        lipar.setMargins(12,14,12,14)

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
            step = save.first
            for (v in save.second)
                scenario.variable[v.key] = v.value as Int
        }else{
            println("No save")
        }

        loadStep()
    }


    private fun loadStep(){
        user.saveScenario(storer.getKey(),step,scenario.variable)
        glayout.removeAllViews()
        val etape = scenario.etapes[step]
        for (e in etape.elems)
            loadElem(e)

    }

    private fun loadElem(e : EtapElem){
        if(e.condition != "" && !evaluateCondition(e.condition))
            return

        when(e.type){
            TYPE.IMG -> instantiateImage(e.content)
            TYPE.TXT -> instantiateText(e.content)
            TYPE.VAR -> evaluateVar(e.content)
            TYPE.QRC -> instantiateQrWaiter(e.content)
            TYPE.BTN -> instantiateButton(e.content)
            TYPE.EDT -> instantiateEdit(e.content,e.additional1, e.additional2)
            TYPE.ETP -> instantiateEtape(e.content)
            TYPE.LCK -> instantiateLock(e.content)
            TYPE.UCK -> instantiateUnlock(e.content)
            TYPE.TST -> showToast(e.content)

        }


    }




    private fun instantiateText(s:String)
    {
        val tv = TextView(this)
        tv.textSize = 16f
        tv.text = s
        tv.layoutParams = ltpar

        glayout.addView(tv)
    }

    private fun instantiateImage(name:String)
    {
        val iv = ImageView(this)
        iv.setImageBitmap(storer.loadImage(name))
        iv.layoutParams = lipar
        iv.adjustViewBounds = true

        glayout.addView(iv)
    }


    private fun instantiateEdit(s:String, response1 : String, response2: String)
    {
        val et = EditText(this)
        et.textSize = 16f
        et.layoutParams = ltpar
        et.setSingleLine()

        glayout.addView(et)

        val b = Button(this)
        b.text = s
        b.setOnClickListener{evaluateEditListener(s,et.text.toString(),response1,response2)}
        b.layoutParams = ltpar

        glayout.addView(b)

    }
    private fun evaluateEditListener(id:String, response : String ,verif1 : String, verif2 : String){
        if(response.trim().lowercase(Locale.getDefault()) == verif1.lowercase(Locale.getDefault()) || (verif2 != "" && response.trim().lowercase(Locale.getDefault()) == verif2.lowercase(Locale.getDefault())))
            loadElemsFromWaiters(getEtap().qrwaiters,id)

    }


    private fun instantiateButton(s:String)
    {
        val b = Button(this)
        b.text = s
        b.setOnClickListener{evaluateButtonListener(s)}
        b.layoutParams = ltpar

        glayout.addView(b)

        getEtap().buttonwaiters.add(s)
    }

    private fun evaluateButtonListener(id:String){
        loadElemsFromWaiters(getEtap().qrwaiters,id)
    }


    private fun showToast(s: String)= Toast.makeText(this,s, Toast.LENGTH_LONG ).show()


    private fun instantiateEtape(s: String){
        step = s.toInt()
        loadStep()
        return
    }

    private fun instantiateLock (s: String) {
        getEtap().lockers.add(s.trim().toInt())
    }

    private fun instantiateUnlock (s: String) {
        getEtap().lockers.remove(s.trim().toInt())
    }

    private fun instantiateQrWaiter(id : String){
        getEtap().qrwaiters.add(id)
    }
    override fun evaluateQr(s : String) {
        if (cheat(s)) return

        loadElemsFromWaiters(getEtap().qrwaiters,s)

        for (w in getEtap().next){
            if(w.key == s){
                if (getEtap().lockers.contains(w.value)) return
                step = w.value
                loadStep()
                return
            }
        }
        super.evaluateQr(s)
    }

    private fun cheat(s : String): Boolean {
        if(User.name != getString(R.string.app_author))
            return false

        if(s.startsWith("Forcer Etape ")){
            val newstep = s.substring("Forcer Etape ".length).toInt()
            step = newstep
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

        return false
    }

//region Evaluations Conditions
    private fun evaluateCondition(cond : String):Boolean{
    var all = true
    val aa = cond.split("&&")
    for (a in aa){
        val bb = a.split("||")
        var ball = false
        for (b in bb){
            val c = if(b.contains("==")) b.split("==") else b.split("!=")

            val c0 = scenario.variable[c[0].trim()]?:c[0].trim().toInt()
            val c1 = scenario.variable[c[1].trim()]?:c[1].trim().toInt()

            ball = ball || (c0 == c1)
        }
        all = all && ball
    }
    return  all
    }

    private fun evaluateVar (s: String){
        val split = s.split("=")
        var v = scenario.variable[split[0]] ?: return

        if(split.size == 2){
            v = split[1].toInt()
        }

        if(split.size == 3){
            when (split[1]){
                "+" -> v+= split[2].toInt()
                "-" -> v-= split[2].toInt()
                "*" -> v*= split[2].toInt()
                "/" -> v/= split[2].toInt()
            }
        }

        scenario.variable[split[0]] = v

    }

    private fun loadElemsFromWaiters(list : MutableList<String>, id : String){
        println("testing element to find $id")
        for(w in getEtap().qrwaiters) {
            println("testing $w")
            if (w == id) {
                list.remove(w)
                for (elist in getEtap().underelems)
                    if (elist.first == id) {
                        for (e in elist.second)
                            loadElem(e)
                        return
                    }
            }
        }
    }
//endregion

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