package com.ufx.jeudepistekt

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.ufx.jeudepistekt.databinding.ActivitySettingBinding
import com.ufx.jeudepistekt.tools.User

class SettingActivity : CommonsActivity() {
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        findViewById<EditText>(R.id.nameedit).setText(User.name)
        findViewById<EditText>(R.id.nameedit).setOnKeyListener{ _: View, _: Int, keyEvent: KeyEvent -> saveKey(keyEvent)}
        findViewById<Button>(R.id.button_name).setOnClickListener{saveName()}
    }

    private fun saveKey(keyEvent: KeyEvent):Boolean{
        if (keyEvent.keyCode == KeyEvent.KEYCODE_ENTER)
            saveName()
        return true
    }
    private fun saveName(){
        val newname = findViewById<EditText>(R.id.nameedit).text.toString()
        User(this).saveName(newname)
        Toast.makeText(this, getString(R.string.newname) + newname, Toast.LENGTH_LONG).show()
        findViewById<EditText>(R.id.nameedit).clearFocus()
    }


//region swapper
    override fun swapToSettings() {

    }
    override fun swapToAbout() {
        finish()
        super.swapToAbout()
    }
//endregion
}