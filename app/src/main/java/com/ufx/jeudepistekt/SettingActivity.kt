package com.ufx.jeudepistekt

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager.GET_META_DATA
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import com.ufx.jeudepistekt.databinding.ActivitySettingBinding
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element
import java.util.*


class SettingActivity : CommonsActivity() {
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val appinfo: ApplicationInfo = this.packageManager.getApplicationInfo(BuildConfig.APPLICATION_ID, GET_META_DATA)

        val aboutPage: View = AboutPage(this)
            .isRTL(false)
            .setDescription(getString(R.string.app_description))
            .setImage(R.drawable.my_ic_launcher)
            .addGroup(getString(R.string.about))
            .addItem(Element().setTitle("Version "+BuildConfig.VERSION_NAME))
            .addItem(Element().setTitle("Android SDK "+ appinfo.targetSdkVersion + " / " + Build.VERSION.SDK_INT))
            .addItem(Element().setTitle("Libs : "+ appinfo.sharedLibraryFiles))
            .addGroup(getString(R.string.contacts))
            .addEmail("fx73000@yahoo.fr")
            .addWebsite("https://github.com/Fx73")
            .addPlayStore("com.ufx")
            .addGitHub("Fx73/Jeu_de_Piste_Kt")
            .addItem(getCopyRightsElement())
            .create()

        findViewById<FrameLayout>(R.id.settinglayout).addView(aboutPage)

    }

    fun getCopyRightsElement(): Element? {
        val copyRightsElement = Element()
        val copyrights =
            String.format(getString(R.string.copy_right), Calendar.getInstance().get(Calendar.YEAR))
        copyRightsElement.setTitle(copyrights)
        copyRightsElement.setIconDrawable(R.drawable.about_icon_copy_right)
        copyRightsElement.setAutoApplyIconTint(true)
        copyRightsElement.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color)
        copyRightsElement.setIconNightTint(R.color.white)
        copyRightsElement.setGravity(Gravity.CENTER)
        copyRightsElement.setOnClickListener { Toast.makeText(this, copyrights, Toast.LENGTH_SHORT).show() }

        return copyRightsElement
    }


    override fun swapToMain() {
        finish()
    }
}