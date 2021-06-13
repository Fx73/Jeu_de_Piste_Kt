package com.ufx.jeudepistekt

import android.Manifest
import android.R.attr
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.FileUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

//region Utils
    val BROWSEFILECODE = 2000

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

    fun BrowseFile() {
        //Check and ask storage permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
            return
        }

        var chooseFileIntent = Intent(Intent.ACTION_GET_CONTENT)
        chooseFileIntent.type = "*/*"
        // Only return URIs that can be opened with ContentResolver
        chooseFileIntent.addCategory(Intent.CATEGORY_OPENABLE)
        chooseFileIntent = Intent.createChooser(chooseFileIntent, "Choose a file")
        startActivityForResult(chooseFileIntent, BROWSEFILECODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //File Browser Result
        if(requestCode == BROWSEFILECODE){
            if (resultCode == RESULT_OK) {
                val fileUri: Uri? = data?.data
                println(fileUri?.path)
            }
            super.onActivityResult(requestCode, resultCode, data)
            return
        }

        //QRCode Result
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