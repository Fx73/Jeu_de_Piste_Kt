package com.ufx.jeudepistekt

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import com.ufx.jeudepistekt.databinding.ActivityMainBinding
import com.ufx.jeudepistekt.tools.PathFinder
import com.ufx.jeudepistekt.tools.Permissions.Companion.askPermission
import com.ufx.jeudepistekt.tools.Zipper


class MainActivity : CommonsActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var user: User

    lateinit var scenariolist: List<Pair<String,String>>


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

    fun createScenarioGrid() {
        val sAlayout: LinearLayout = findViewById(R.id.scenariolayoutA)
        val sBlayout: LinearLayout = findViewById(R.id.scenariolayoutB)

        var sens = true
        for (scenario in scenariolist) {
            val card = createCard(scenario.first, scenario.second,"")
            card.setOnClickListener { swapToGame() }
            registerForContextMenu(card)
            if (sens) sAlayout.addView(card) else sBlayout.addView(card)
            sens = !sens
        }

        val pluscard = createCard("Ajouter un scenario","", "plusicon")
        pluscard.setOnClickListener { BrowseFile() }
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


        imgview.setImageResource(this.resources.getIdentifier(img, "drawable", this.packageName))
        imgpar.setMargins(8, 8, 8, 8)
        imgpar.gravity = Gravity.CENTER_HORIZONTAL

        titleview.text = title
        titlepar.setMargins(8, 8, 8, 8)

        titleview.layoutParams = titlepar
        card.layoutParams = cardpar
        imgview.layoutParams = imgpar


        val l = LinearLayout(this)
        l.orientation = LinearLayout.VERTICAL
        card.addView(l)


        l.addView(imgview)
        l.addView(titleview)

        card.tag = Zipper.key(title, creator)

        return card
    }
//endregion

//region addScenario
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri == null) return@registerForActivityResult
        val path = PathFinder().getPath(this, uri) ?: return@registerForActivityResult

        if (Zipper(this).unpackScenario(path,scenariolist)){
            val bf = this.openFileInput("moi_Kalteeeee_test.txt").bufferedReader()
            println(bf.readLine())

        }



    }

    private fun BrowseFile() {
        //Check and ask storage permission
        if(askPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE))
            getContent.launch("application/octet-stream")
    }

//endregion

//region Context Menu
    lateinit var selectedview : View
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        selectedview = v
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.context_menu, menu)
        super.onCreateContextMenu(menu, v, menuInfo)

}

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.scenario_delete -> {
                println(selectedview.tag.toString())
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
 //endregion
    override fun swapToMain(){
     val settingActivity = Intent(this@MainActivity, SettingActivity::class.java)
     startActivity(settingActivity)
    }

    private fun swapToGame() {
        val gameActivity = Intent(this@MainActivity, GameActivity::class.java)
        startActivity(gameActivity)
    }
}