package com.ufx.jeudepistekt

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.navigation.fragment.navArgs
import com.ufx.jeudepistekt.GameFragment.Companion.scenario
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element

/**
 * The view of a scenario information
 * It override the view of the lobby about page, when access from a game
 */
class InfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_info, container, false)



        val title = scenario.title
        val creator = scenario.creator
        val description = scenario.description
        val copyright = scenario.copyright
        val version = scenario.version

        val aboutPage: View = AboutPage(requireContext())
            .isRTL(false)
            .setDescription(description)
            //.setImage()
            .addGroup(title)
            .addItem(Element().setTitle("par : $creator"))
            .addItem(Element().setTitle(copyright))
            .addItem(Element().setTitle("version de l'app : $version"))

            .create()

        requireView().findViewById<FrameLayout>(R.id.infolayout).addView(aboutPage)
        return view
    }


}