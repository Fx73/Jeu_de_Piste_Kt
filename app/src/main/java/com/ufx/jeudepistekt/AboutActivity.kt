package com.ufx.jeudepistekt

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager.GET_META_DATA
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import com.ufx.jeudepistekt.databinding.ActivityAboutBinding
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element
import java.util.*


class AboutActivity : CommonsActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val appinfo: ApplicationInfo = this.packageManager.getApplicationInfo(BuildConfig.APPLICATION_ID, GET_META_DATA)

        val aboutPage: View = AboutPage(this)
            .isRTL(false)
            .setDescription(getString(R.string.aide))
            .setImage(R.drawable.ic_banner)
            .addGroup(getString(R.string.about))
            .addItem(Element().setTitle("Version "+BuildConfig.VERSION_NAME))
            .addItem(Element().setTitle("Android SDK "+ Build.VERSION.SDK_INT + " / " + appinfo.targetSdkVersion))
            .addGroup(getString(R.string.contacts))
            .addItem(Element().setTitle("Application par : " + getString(R.string.app_author)))
            .addEmail(getString(R.string.app_mail))
            .addWebsite("https://github.com/Fx73")
            .addPlayStore("com.ufx")
            .addGitHub("Fx73/Jeu_de_Piste_Kt")
            .addItem(getCopyRightsElement())
            .create()

        binding.root.findViewById<FrameLayout>(R.id.aboutlayout2).addView(aboutPage)

    }

    private fun getCopyRightsElement(): Element {
        val copyRightsElement = Element()
        val copyrights =
            String.format(getString(R.string.copy_right), Calendar.getInstance().get(Calendar.YEAR))
        copyRightsElement.title = copyrights
        copyRightsElement.iconDrawable = R.drawable.about_icon_copy_right
        copyRightsElement.autoApplyIconTint = true
        copyRightsElement.iconTint = mehdi.sakout.aboutpage.R.color.about_item_icon_color
        copyRightsElement.iconNightTint = R.color.white
        copyRightsElement.gravity = Gravity.CENTER
        copyRightsElement.setOnClickListener { Toast.makeText(this, copyrights, Toast.LENGTH_SHORT).show() }

        return copyRightsElement
    }


//region swapper
    override fun swapToAbout(){

    }

    override fun swapToSettings() {
        finish()
        super.swapToSettings()
    }
//endregion
}