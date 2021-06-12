package com.ufx.jeudepistekt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ufx.jeudepistekt.databinding.ActivityGameBinding
import com.ufx.jeudepistekt.databinding.ActivityMainBinding

class GameActivity : CommonsActivity() {
    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { ScanQr() }

    }



    override fun swapToMain() {
        finish()
    }
}