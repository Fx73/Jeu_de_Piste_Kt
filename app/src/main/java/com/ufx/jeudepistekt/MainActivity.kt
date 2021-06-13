package com.ufx.jeudepistekt

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import com.ufx.jeudepistekt.databinding.ActivityMainBinding


class MainActivity : CommonsActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var user: User

    lateinit var scenariolist : Map<String,String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.fab.setOnClickListener { ScanQr() }

        user = User(this)
        user.LoadName()

        scenariolist = user.LoadScenarioList()


        createScenarioGrid()

    }

//region Scenario Panel

    fun createScenarioGrid(){
        val sAlayout : LinearLayout = findViewById(R.id.scenariolayoutA)
        val sBlayout : LinearLayout = findViewById(R.id.scenariolayoutB)

        var sens = true
        for (scenario in scenariolist){
            val card = createCard(scenario.key, scenario.value)
            card.setOnClickListener{swapToGame()}
            if (sens) sAlayout.addView(card) else sBlayout.addView(card)
            sens = !sens
        }
        val pluscard = createCard("Ajouter un scenario","plusicon")
        pluscard.setOnClickListener{BrowseFile()}
        if (sens) sAlayout.addView(pluscard) else sBlayout.addView(pluscard)


    }


    fun createCard(ttext : String, img : String):CardView{
        val card = CardView(this)
        val imgv = ImageView(this)
        val title = TextView(this)

        val cardpar = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,500)
        val imgpar = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,300)
        val titlepar = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,300)

        cardpar.setMargins(6,6,6,6)
        card.radius = 10f


        imgv.setImageResource(this.resources.getIdentifier(img, "drawable", this.packageName))
        imgpar.setMargins(8,8,8,8)
        imgpar.gravity = Gravity.CENTER_HORIZONTAL

        title.text = ttext
        titlepar.setMargins(8,8,8,8)

        title.layoutParams = titlepar
        card.layoutParams = cardpar
        imgv.layoutParams = imgpar


        val l = LinearLayout(this)
        l.orientation = LinearLayout.VERTICAL
        card.addView(l)


        l.addView(imgv)
        l.addView(title)

        return card
    }
//endregion

//region addScenario



//endregion

    fun swapToGame(){
        val gameActivity = Intent(this@MainActivity, GameActivity::class.java)
        startActivity(gameActivity)
    }
}