package com.ufx.jeudepistekt

import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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
            R.id.settings -> { swapToSettings(); true }
            R.id.infos -> { swapToInfos(); true }
            else -> super.onOptionsItemSelected(item)
        }
    }



    open fun swapToMain(){
        finish()
        //val gameActivity = Intent(this, MainActivity::class.java)
        //startActivity(gameActivity)
    }
    open fun swapToGame() {
        val gameActivity = Intent(this, GameActivity::class.java)
        startActivity(gameActivity)
    }
    open fun swapToSettings(){
        val settingActivity = Intent(this, SettingActivity::class.java)
        startActivity(settingActivity)
    }

    open fun swapToInfos(){
        val infosActivity = Intent(this, InfosActivity::class.java)
        startActivity(infosActivity)
    }
//endregion

//region Utils
    fun ScanQr()
    {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt("Scan")
        integrator.setCameraId(0)
        integrator.setBeepEnabled(false)
        integrator.setBarcodeImageEnabled(false)
        qrscanner.launch(integrator.createScanIntent())
    }

    private val qrscanner = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val result = IntentIntegrator.parseActivityResult(it.resultCode, it.data)
        if (result.contents == null) {
            Log.e("Scan*******", "Cancelled scan")
        } else {
            Log.e("Scan", "Scanned")
            Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
        }
    }


//endregion

}