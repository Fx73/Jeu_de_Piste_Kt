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
import java.util.*

class GameActivity : CommonsActivity() {
    private lateinit var binding: ActivityGameBinding
    private lateinit var glayout: LinearLayout

    private lateinit var scenario : Scenario
    private lateinit var storer : Storer
    private lateinit var user : User

    private var step = 0
    private fun getEtap () = scenario.etapes[step]

    private var lpar = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        glayout = binding.root.findViewById(R.id.gamelayout)
        binding.fab.setOnClickListener { scanQr() }
        lpar.setMargins(10,10,10,10)

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
        }

        loadStep()
    }


    private fun loadStep(){
        user.saveScenario(storer.getKey(),step,scenario.variable)
        glayout.removeAllViews()
        val etape = scenario.etapes[step]
        for (e in etape.elems)
            evaluateElem(e)

    }

    private fun evaluateElem(e : EtapElem){
        if(e.condition != "" && !evaluateCondition(e.condition))
            return

        when(e.type){
            TYPE.IMG -> instanciateImage(e.content)
            TYPE.TXT -> instanciateText(e.content)
            TYPE.VAR -> evaluateVar(e.content)
            TYPE.QRC -> instanciateQrWaiter(e.content)
            TYPE.BTN -> instanciateButton(e.content)
            TYPE.EDT -> instanciateEdit(e.content,e.additional1, e.additional2)
            TYPE.ETP -> instanciateEtape(e.content)
            TYPE.LCK -> instanciateLock(e.content)
            TYPE.UCK -> instanciateUnlock(e.content)
            TYPE.TST -> showToast(e.content)

        }


    }

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


    private fun instanciateText(s:String)
    {
        val tv = TextView(this)
        tv.textSize = 16f
        tv.text = s
        tv.layoutParams = lpar

        glayout.addView(tv)
    }

    private fun instanciateImage(name:String)
    {
        val iv = ImageView(this)
        iv.setImageBitmap(storer.loadImage(name))
        iv.layoutParams = lpar
        iv.adjustViewBounds = true

        glayout.addView(iv)
    }


    private fun instanciateEdit(s:String, response1 : String, response2: String)
    {
        val et = EditText(this)
        et.textSize = 16f
        et.layoutParams = lpar
        et.setSingleLine()

        glayout.addView(et)

        val b = Button(this)
        b.text = s
        b.setOnClickListener{evaluateEditListener(s,et.text.toString(),response1,response2)}
        b.layoutParams = lpar

        glayout.addView(b)

    }

    private fun instanciateButton(s:String)
    {
        val b = Button(this)
        b.text = s
        b.setOnClickListener{evaluateButtonListener(s)}
        b.layoutParams = lpar

        glayout.addView(b)

        getEtap().buttonwaiters.add(s)
    }

    private fun evaluateButtonListener(id:String){
        runElistFromWaiters(getEtap().qrwaiters,id)
    }
    private fun evaluateEditListener(id:String, response : String ,verif1 : String, verif2 : String){
        if(response.trim().lowercase(Locale.getDefault()) == verif1.lowercase(Locale.getDefault()) || (verif2 != "" && response.trim().lowercase(Locale.getDefault()) == verif2.lowercase(Locale.getDefault())))
            runElistFromWaiters(getEtap().qrwaiters,id)

    }

    fun showToast(s: String)= Toast.makeText(this,s, Toast.LENGTH_LONG ).show()

    private fun instanciateQrWaiter(id : String){
        getEtap().qrwaiters.add(id)
    }

    private fun instanciateEtape(s: String){
        step = s.toInt()
        loadStep()
        return
    }
    fun instanciateLock (s: String) {
        getEtap().lockers.add(s.trim().toInt())
    }
    private fun instanciateUnlock (s: String) {
        getEtap().lockers.remove(s.trim().toInt())
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


    override fun evaluateQr(s : String) {
        runElistFromWaiters(getEtap().qrwaiters,s)

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

    private fun runElistFromWaiters(list : MutableList<String>, id : String){
        for(w in getEtap().qrwaiters) {
            if (w == id) {
                list.remove(w)
                for (elist in getEtap().underelems)
                    if (elist.first == id) {
                        for (e in elist.second)
                            evaluateElem(e)
                        return
                    }
            }
        }
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