package com.ufx.jeudepistekt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class GameActivity : CommonsActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
    }



    override fun swapToMain() {
        finish()
    }
}