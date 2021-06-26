package com.ufx.jeudepistekt

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator


open class CommonsActivity : AppCompatActivity() {

//region Menu

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_home -> {swapToMain(); true}
            R.id.action_code -> {codeEdit(); true}
            R.id.action_settings -> { swapToSettings(); true }
            R.id.action_about -> { swapToAbout(); true }
            else -> super.onOptionsItemSelected(item)
        }
    }


    open fun codeEdit(){
        object : Dialog(this){
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setContentView(R.layout.dialog_code)
                findViewById<Button>(R.id.code_button).setOnClickListener { evaluateQr(findViewById<EditText>(R.id.code_edit).text.toString());dismiss()}
            }
        }.show()
    }


    open fun swapToMain(){
        finish()
        //val gameActivity = Intent(this, MainActivity::class.java)
        //startActivity(gameActivity)
    }
    open fun swapToSettings(){
        val settingActivity = Intent(this, SettingActivity::class.java)
        startActivity(settingActivity)
    }

    open fun swapToAbout(){
        val aboutActivity = Intent(this, AboutActivity::class.java)
        startActivity(aboutActivity)
    }
//endregion

//region Utils
    fun scanQr()
    {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt("Scan")
        integrator.setCameraId(0)
        integrator.setBeepEnabled(false)
        integrator.setBarcodeImageEnabled(false)
        qrScanner.launch(integrator.createScanIntent())
    }

    private val qrScanner = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val result = IntentIntegrator.parseActivityResult(it.resultCode, it.data)
        if (result.contents == null) {
            Log.e("Scan*******", "Cancelled scan")
        } else {
            Log.e("Scan", "Scanned")
            evaluateQr(result.contents)
        }
    }
    open fun evaluateQr(s : String) {
        Toast.makeText(this, getString(R.string.qr_unknown), Toast.LENGTH_LONG).show()
    }

//endregion

}