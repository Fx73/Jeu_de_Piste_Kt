package com.ufx.jeudepistekt

import android.Manifest
import android.content.Intent
import android.graphics.Color
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
import com.ufx.jeudepistekt.jeu.User
import com.ufx.jeudepistekt.tools.Zipper


class MainActivity : CommonsActivity() {

    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { scanQr() }

        User.initSharedPref(this)
        User.loadName()
        
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