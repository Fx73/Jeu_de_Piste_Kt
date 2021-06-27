package com.ufx.jeudepistekt

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.ufx.jeudepistekt.databinding.ActivityInfoBinding
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element

class InfoActivity : CommonsActivity() {
    private lateinit var binding: ActivityInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val title = intent.getStringExtra("SCENARIO_TITLE")
        val creator = intent.getStringExtra("SCENARIO_CREATOR")
        val description = intent.getStringExtra("SCENARIO_DESCRIPTION")
        val copyright = intent.getStringExtra("SCENARIO_COPYRIGHT")
        val version = intent.getStringExtra("SCENARIO_VERSION")

        val aboutPage: View = AboutPage(this)
            .isRTL(false)
            .setDescription(description)
            //.setImage()
            .addGroup(title)
            .addItem(Element().setTitle("par : $creator"))
            .addItem(Element().setTitle(copyright))
            .addItem(Element().setTitle("version de l'app : $version"))

            .create()

        binding.root.findViewById<FrameLayout>(R.id.infolayout).addView(aboutPage)
    }

//region swapper
    override fun swapToSettings() {
        finish()
        super.swapToSettings()
    }

    override fun swapToMain() {
        finish()
        super.swapToMain()
    }
//endregion
}