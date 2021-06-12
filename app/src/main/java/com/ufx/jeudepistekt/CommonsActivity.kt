package com.ufx.jeudepistekt

import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if(item.itemId == R.id.action_home)
            swapToMain()

        return when (item.itemId) {
            R.id.action_home -> true
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    open fun swapToMain(){
        Toast.makeText(this, "Hello " + User.name, Toast.LENGTH_SHORT).show()
    }
//endregion

//region QR Code

    fun ScanQr()
    {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt("Scan")
        integrator.setCameraId(0)
        integrator.setBeepEnabled(false)
        integrator.setBarcodeImageEnabled(false)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Log.e("Scan*******", "Cancelled scan")
            } else {
                Log.e("Scan", "Scanned")
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
            }
        } else { // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

//endregion

}