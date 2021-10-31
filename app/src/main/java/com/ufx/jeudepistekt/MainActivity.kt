package com.ufx.jeudepistekt

import android.app.Dialog
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
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import com.google.zxing.integration.android.IntentIntegrator
import com.ufx.jeudepistekt.databinding.ActivityMainBinding
import com.ufx.jeudepistekt.jeu.User


class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    lateinit var fragmentManager: FragmentManager

    /**
     * On Create / OnStart :
     * Initialize vars and inflate views depending on lifecycle
     * Load Username
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        User.initSharedPref(this)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { scanQr() }

        User.loadName()

    }
    override fun onStart() {
        super.onStart()
        navController = findNavController(this, R.id.fragment_container_view)
        fragmentManager = supportFragmentManager.fragments.first().childFragmentManager
    }


    //region Menu
    /**
     * onCreateOptionsMenu :
     * Inflate the menu
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    /**
     * onOptionsItemSelected
     * Navigate to the right page as menu item is selected
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_home -> { navController.navigate(R.id.lobbyFragment); true}
            R.id.action_code -> {manualQrCode(); true}
            R.id.action_settings -> { navController.navigate(R.id.settingFragment); true }
            R.id.action_about -> {
                if(fragmentManager.fragments.first() is GameFragment)
                    navController.navigate(R.id.infoFragment)
                else
                    navController.navigate(R.id.aboutFragment)
                true }
            else -> super.onOptionsItemSelected(item)
        }
    }
//endregion


//region Qr Scanner
    /**
     * scanQr
     * Lauch the intent of the Qr scanner
     */
    private fun scanQr()
    {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt("Scan")
        integrator.setCameraId(0)
        integrator.setBeepEnabled(false)
        integrator.setBarcodeImageEnabled(false)
        qrScanner.launch(integrator.createScanIntent())
    }

    /**
     * qrScanner
     * object registered to get the qrScanner activity result
     * Pass the result to evaluateQr
     */
    private val qrScanner = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val result = IntentIntegrator.parseActivityResult(it.resultCode, it.data)
        if (result.contents == null) {
            Log.e("Scan*******", "Cancelled scan")
        } else {
            Log.e("Scan", "Scanned")
            evaluateQr(result.contents)
        }
    }
    fun evaluateQr(s : String) {
        if(fragmentManager.fragments.first() is GameFragment)
            (fragmentManager.fragments.first() as GameFragment).evaluateQr(s)
        else
            Toast.makeText(this, getString(R.string.qr_unknown) + " : " + s, Toast.LENGTH_LONG).show()
    }

    private fun manualQrCode(){
        object : Dialog(this){
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setContentView(R.layout.dialog_code)
                findViewById<Button>(R.id.code_button).setOnClickListener { evaluateQr(findViewById<EditText>(R.id.code_edit).text.toString());dismiss()}
            }
        }.show()
    }
//endregion

    override fun onBackPressed() {
        if(fragmentManager.fragments.first() !is LobbyFragment)
            navController.navigate(R.id.lobbyFragment)
        else
            super.onBackPressed()

    }
}