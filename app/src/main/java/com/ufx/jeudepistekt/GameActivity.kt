package com.ufx.jeudepistekt

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.marginEnd
import com.ufx.jeudepistekt.databinding.ActivityGameBinding
import com.ufx.jeudepistekt.jeu.EtapElem
import com.ufx.jeudepistekt.jeu.Scenario
import com.ufx.jeudepistekt.tools.Storer

class GameActivity : CommonsActivity() {
    private lateinit var binding: ActivityGameBinding
    private lateinit var glayout: LinearLayout

    private lateinit var scenario : Scenario
    private lateinit var storer : Storer
    private lateinit var user : User

    private var step = 0

    var lpar = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        glayout = binding.root.findViewById(R.id.gamelayout)
        binding.fab.setOnClickListener { ScanQr() }
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
        val save = user.LoadScenario(storer.getKey())

        if(save != null) {
            step = save.first
            for (v in save.second)
                scenario.variable[v.key] = v.value
        }

        LoadStep()
    }


    fun LoadStep(){
        val etape = scenario.etapes[step]
        for (e in etape.elems){
            if(e.type == EtapElem.TYPE.IMG)
                instanciateImage(e.content)
            if(e.type == EtapElem.TYPE.TXT)
                instanciateText(e.content)
        }
    }




    private fun instanciateText(s:String)
    {
        val tv = TextView(this)
        tv.textSize = 16f
        tv.text = s
        tv.layoutParams = lpar

        glayout.addView(tv)
    }

    fun instanciateImage(name:String)
    {
        val iv = ImageView(this)
        iv.setImageBitmap(storer.loadImage(name))
        iv.layoutParams = lpar

        glayout.addView(iv)
    }




    fun InstanciateButton(s:String ,f:() -> Unit )
    {
        val b = Button(this)
        b.text = s
        b.setOnClickListener{f()}
        b.layoutParams = lpar

        glayout.addView(b)
    }






    override fun swapToSettings() {
        finish()
        super.swapToSettings()
    }

    override fun swapToAbout() {
        val infoActivity = Intent(this, InfoActivity::class.java)
        infoActivity.putExtra("SCENARIO_TITLE", scenario.title)
        infoActivity.putExtra("SCENARIO_CREATOR", scenario.creator)
        infoActivity.putExtra("SCENARIO_DESCRIPTION", scenario.description)


        startActivity(infoActivity)
    }
}