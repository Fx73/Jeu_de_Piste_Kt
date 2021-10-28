package com.ufx.jeudepistekt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.FrameLayout
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element

/**
 * The view of a scenario information
 * It override the view of the lobby about page, when access from a game
 */
class InfoFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO:Correct intents
        val title = requireActivity().intent.getStringExtra("SCENARIO_TITLE")
        val creator = requireActivity().intent.getStringExtra("SCENARIO_CREATOR")
        val description = requireActivity().intent.getStringExtra("SCENARIO_DESCRIPTION")
        val copyright = requireActivity().intent.getStringExtra("SCENARIO_COPYRIGHT")
        val version = requireActivity().intent.getStringExtra("SCENARIO_VERSION")

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
    }


}