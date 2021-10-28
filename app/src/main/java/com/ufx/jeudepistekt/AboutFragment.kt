package com.ufx.jeudepistekt

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element
import java.util.*

/**
 * The credits of the games
 * Created with Api mehdi.sakout.aboutpage
 */
class AboutFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appinfo: ApplicationInfo = requireActivity().packageManager.getApplicationInfo(BuildConfig.APPLICATION_ID,
            PackageManager.GET_META_DATA
        )

        val aboutPage: View = AboutPage(requireContext())
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

        requireView().findViewById<FrameLayout>(R.id.aboutlayout).addView(aboutPage)
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
        copyRightsElement.setOnClickListener { Toast.makeText(requireContext(), copyrights, Toast.LENGTH_SHORT).show() }

        return copyRightsElement
    }
}