package com.ufx.jeudepistekt

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import com.ufx.jeudepistekt.databinding.ActivityMainBinding
import com.ufx.jeudepistekt.tools.Permissions.Companion.askPermission
import com.ufx.jeudepistekt.tools.Storer
import com.ufx.jeudepistekt.tools.Zipper


class MainActivity : CommonsActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var user: User

    private lateinit var scenariolist: MutableList<Pair<String,String>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { scanQr() }

        user = User(this)
        user.loadName()

        scenariolist = user.loadScenarioList()


        createScenarioGrid()

    }


//region Scenario Panel

    private fun createScenarioGrid() {
        val sAlayout: LinearLayout = findViewById(R.id.scenariolayoutA)
        val sBlayout: LinearLayout = findViewById(R.id.scenariolayoutB)

        var sens = true
        for (scenario in scenariolist) {
            val card = createCard(scenario.first, scenario.second,"ScenarioIcon.png")
            card.setOnClickListener { swapToGame(scenario.first, scenario.second) }
            registerForContextMenu(card)
            if (sens) sAlayout.addView(card) else sBlayout.addView(card)
            sens = !sens
        }

        val pluscard = createPlusCard()
        pluscard.setOnClickListener { browseFile() }
        if (sens) sAlayout.addView(pluscard) else sBlayout.addView(pluscard)


    }


    private fun createCard(title: String, creator : String, img: String): CardView {
        val card = CardView(this)
        val imgview = ImageView(this)
        val titleview = TextView(this)

        val cardpar = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500)
        val imgpar = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 300)
        val titlepar = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300)

        cardpar.setMargins(6, 6, 6, 6)
        card.radius = 10f


        imgview.setImageBitmap(Storer(title,creator, this).loadImage(img))

        imgpar.setMargins(8, 8, 8, 8)
        imgpar.gravity = Gravity.CENTER_HORIZONTAL

        titleview.text = title
        titlepar.setMargins(8, 8, 8, 8)
        titlepar.gravity = Gravity.CENTER_HORIZONTAL

        titleview.layoutParams = titlepar
        card.layoutParams = cardpar
        imgview.layoutParams = imgpar


        val l = LinearLayout(this)
        l.orientation = LinearLayout.VERTICAL
        card.addView(l)


        l.addView(imgview)
        l.addView(titleview)

        card.tag = Storer.key(title, creator)

        return card
    }

    private fun createPlusCard(): CardView {
        val card = CardView(this)
        val imgview = ImageView(this)
        val titleview = TextView(this)

        val cardpar = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500)
        val imgpar = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 300)
        val titlepar = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300)

        cardpar.setMargins(6, 6, 6, 6)
        card.radius = 10f


        imgview.setImageResource(R.drawable.plusicon)

        imgpar.setMargins(8, 8, 8, 8)
        imgpar.gravity = Gravity.CENTER_HORIZONTAL

        titleview.text = getString(R.string.addscenario)
        titlepar.setMargins(8, 8, 8, 8)

        titleview.layoutParams = titlepar
        card.layoutParams = cardpar
        imgview.layoutParams = imgpar


        val l = LinearLayout(this)
        l.orientation = LinearLayout.VERTICAL
        card.addView(l)


        l.addView(imgview)
        l.addView(titleview)

        return card
    }
//endregion

//region addScenario
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
    if (uri == null) return@registerForActivityResult
    println("OKAY0")

        val zipper = Zipper(this, uri)

        println(zipper.storer.title)
        for (scenario in scenariolist)
            if(scenario.second+"_"+scenario.first == zipper.storer.getKey()){
                Toast.makeText(this, "Same scenario from same creator already exists. Delete the old one !", Toast.LENGTH_LONG).show()
                return@registerForActivityResult
            }

        if(zipper.unpackZip()){
            scenariolist.add(Pair(zipper.storer.title,zipper.storer.creator))
            user.saveScenarioList(scenariolist)
            finish()
            startActivity(intent)
        }

    }

    private fun browseFile() {
        //Check and ask storage permission
        if(askPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE))
            getContent.launch("application/*")
    }

//endregion

//region Context Menu
    private lateinit var selectedview : View
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        selectedview = v
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.context_menu, menu)
        super.onCreateContextMenu(menu, v, menuInfo)

}

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.scenario_delete -> {
                for (scenario in scenariolist){
                    if (Storer.key(scenario.first,scenario.second) == selectedview.tag.toString()){
                        Zipper(this,scenario.first,scenario.second).deleteScenarioFiles()
                        scenariolist.remove(scenario)
                        user.saveScenarioList(scenariolist)
                        finish()
                        startActivity(intent)
                        return true
                    }
                }

                false
            }
            else -> super.onContextItemSelected(item)
        }
    }


//region swapper
    override fun swapToMain() {
        Toast.makeText(this, "Hello " + User.name, Toast.LENGTH_SHORT).show()
    }

    private fun swapToGame(title : String, creator: String) {
        val gameActivity = Intent(this, GameActivity::class.java)
        gameActivity.putExtra("SCENARIO_TITLE", title)
        gameActivity.putExtra("SCENARIO_CREATOR", creator)
        startActivity(gameActivity)
    }
//endregion
}