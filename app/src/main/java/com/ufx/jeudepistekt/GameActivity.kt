package com.ufx.jeudepistekt

import android.content.Intent
import android.os.Bundle
import com.ufx.jeudepistekt.databinding.ActivityGameBinding

class GameActivity : CommonsActivity() {
    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { ScanQr() }

    }


}