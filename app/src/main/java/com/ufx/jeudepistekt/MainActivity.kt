package com.ufx.jeudepistekt

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.ufx.jeudepistekt.databinding.ActivityMainBinding
import com.ufx.jeudepistekt.jeu.Scenario

class MainActivity : CommonsActivity() {

    private lateinit var binding: ActivityMainBinding

    val scenariolist = listOf("Test1","Test2","Test3","Test4","Test5", "Test6", "Test7", "Test8","Add a scenario")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.fab.setOnClickListener { ScanQr() }


        createScenarioGrid()

        var s = Scenario.buildScenarioFromJson(this,"essai.json")
        println(s.title)
        println(s.creator)
        println(s.variable["var1"])
        println(s.etapes[0].code)



    }

//region Scenario Panel

    fun createScenarioGrid(){
        val sAlayout : LinearLayout = findViewById(R.id.scenariolayoutA)
        val sBlayout : LinearLayout = findViewById(R.id.scenariolayoutB)

        var sens = true
        for (scenario in scenariolist){
            val card = CardView(this)
            val img = ImageView(this)
            val title = TextView(this)

            val cardpar = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,500)
            val imgpar = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,300)
            val titlepar = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,300)

            cardpar.setMargins(6,6,6,6)
            card.radius = 8f


            img.setBackgroundResource(this.resources.getIdentifier("start", "drawable", this.packageName))
            imgpar.setMargins(8,8,8,8)

            title.text = scenario
            titlepar.setMargins(8,8,8,8)

            title.layoutParams = titlepar
            card.layoutParams = cardpar
            img.layoutParams = imgpar

            val l = LinearLayout(this)
            l.orientation = LinearLayout.VERTICAL
            card.addView(l)
            card.setOnClickListener{swapToGame()}
            l.addView(img)
            l.addView(title)
            if (sens) sAlayout.addView(card) else sBlayout.addView(card)

            sens = !sens
        }


    }

//endregion




    fun swapToGame(){
        val gameActivity = Intent(this@MainActivity, GameActivity::class.java)
        startActivity(gameActivity)
    }
}