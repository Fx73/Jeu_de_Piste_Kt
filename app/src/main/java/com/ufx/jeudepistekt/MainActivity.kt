package com.ufx.jeudepistekt

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ufx.jeudepistekt.databinding.ActivityMainBinding
import com.ufx.jeudepistekt.tools.PathFinder
import com.ufx.jeudepistekt.tools.Permissions.Companion.askPermission
import org.apache.commons.io.IOUtils
import java.io.File
import java.io.FileInputStream


class MainActivity : CommonsActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var user: User

    lateinit var scenariolist: Map<String, String>


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
            val card = createCard(scenario.key, scenario.value)
            card.setOnClickListener { swapToGame() }
            if (sens) sAlayout.addView(card) else sBlayout.addView(card)
            sens = !sens
        }
        val pluscard = createCard("Ajouter un scenario", "plusicon")
        pluscard.setOnClickListener { BrowseFile() }
        if (sens) sAlayout.addView(pluscard) else sBlayout.addView(pluscard)


    }


    private fun createCard(ttext: String, img: String): CardView {
        val card = CardView(this)
        val imgv = ImageView(this)
        val title = TextView(this)

        val cardpar = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500)
        val imgpar = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 300)
        val titlepar = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300)

        cardpar.setMargins(6, 6, 6, 6)
        card.radius = 10f


        imgv.setImageResource(this.resources.getIdentifier(img, "drawable", this.packageName))
        imgpar.setMargins(8, 8, 8, 8)
        imgpar.gravity = Gravity.CENTER_HORIZONTAL

        title.text = ttext
        titlepar.setMargins(8, 8, 8, 8)

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
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri == null) return@registerForActivityResult
        val src = PathFinder().getPath(this, uri) ?: return@registerForActivityResult
        val source = File(src)
        val filename = "test.txt"

        val filein = FileInputStream(source)

        this.openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(IOUtils.toByteArray(filein))
        }
        filein.close()

        val bf = this.openFileInput(filename).bufferedReader()
        println(bf.readLine())
        println(bf.readLine())
    }

    private fun BrowseFile() {
        //Check and ask storage permission
        if(askPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE))
            getContent.launch("*/*")
    }

//endregion

    private fun swapToGame() {
        val gameActivity = Intent(this@MainActivity, GameActivity::class.java)
        startActivity(gameActivity)
    }
}