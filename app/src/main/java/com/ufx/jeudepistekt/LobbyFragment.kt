package com.ufx.jeudepistekt

import android.Manifest
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.ufx.jeudepistekt.jeu.User
import com.ufx.jeudepistekt.tools.Permissions
import com.ufx.jeudepistekt.tools.Storer
import com.ufx.jeudepistekt.tools.Zipper


/**
 * The view of the lobby with all card with scenario
 * 2 regions :
 *  - Scenario Panel : instantiate all the cards with scenario
 *  - Add and remove : function to add or to remove a scenario
 */
class LobbyFragment : Fragment() {

    private lateinit var scenariolist: MutableList<Pair<String,String>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scenariolist = User.loadScenarioList()
        createScenarioGrid()
    }



//region Scenario Panel

    private fun createScenarioGrid() {
        val sAlayout: LinearLayout = requireView().findViewById(R.id.scenariolayoutA)
        val sBlayout: LinearLayout = requireView().findViewById(R.id.scenariolayoutB)

        var sens = true
        for (scenario in scenariolist) {
            val card = createCard(scenario.first, scenario.second)
            //TODO : card.setOnClickListener { swapToGame(scenario.first, scenario.second) }
            registerForContextMenu(card)
            if (sens) sAlayout.addView(card) else sBlayout.addView(card)
            sens = !sens
        }

        val pluscard = createPlusCard()
        pluscard.setOnClickListener { browseFile() }
        if (sens) sAlayout.addView(pluscard) else sBlayout.addView(pluscard)


    }


    private fun createCard(title: String, creator : String): CardView {
        val card = CardView(requireContext())
        val imgview = ImageView(requireContext())
        val titleview = TextView(requireContext())
        val creatorview = TextView(requireContext())

        val cardpar = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500)
        val imgpar = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 300)
        val titlepar = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val creatorpar = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        cardpar.setMargins(6, 6, 6, 6)
        card.radius = 10f


        imgview.setImageBitmap(Storer(title,creator, requireContext()).loadImage("ScenarioIcon"))

        imgpar.setMargins(8, 8, 8, 8)
        imgpar.gravity = Gravity.CENTER_HORIZONTAL

        titleview.text = title
        titleview.textSize = 18f
        titlepar.setMargins(10, 10, 10, 10)

        creatorview.text = creator
        creatorview.setTextColor(Color.LTGRAY)
        creatorview.textSize = 10f
        creatorview.gravity = Gravity.END
        creatorpar.setMargins(10, 10, 10, 10)


        titleview.layoutParams = titlepar
        card.layoutParams = cardpar
        imgview.layoutParams = imgpar
        creatorview.layoutParams = creatorpar


        val l = LinearLayout(requireContext())
        l.orientation = LinearLayout.VERTICAL
        card.addView(l)


        l.addView(imgview)
        l.addView(titleview)
        l.addView(creatorview)

        card.tag = Storer.key(title, creator)

        return card
    }

    private fun createPlusCard(): CardView {
        val card = CardView(requireContext())
        val imgview = ImageView(requireContext())
        val titleview = TextView(requireContext())

        val cardpar = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500)
        val imgpar = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 300)
        val titlepar = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300)

        cardpar.setMargins(6, 6, 6, 6)
        card.radius = 10f


        imgview.setImageResource(R.drawable.plusicon)

        imgpar.setMargins(8, 8, 8, 8)
        imgpar.gravity = Gravity.CENTER_HORIZONTAL

        titleview.text = getString(R.string.addscenario)
        titlepar.setMargins(8, 8, 8, 8)

        titleview.layoutParams = titlepar
        card.layoutParams = cardpar
        imgview.layoutParams = imgpar


        val l = LinearLayout(requireContext())
        l.orientation = LinearLayout.VERTICAL
        card.addView(l)


        l.addView(imgview)
        l.addView(titleview)

        return card
    }
//endregion
// region Add and remove scenario

    private fun browseFile() {
        //Check and ask storage permission
        if(Permissions.askPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE))
            getContent.launch("application/*")
    }

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri == null) return@registerForActivityResult
        println("OKAY0")

        val zipper = Zipper(requireContext(), uri)

        println(zipper.storer.title)
        for (scenario in scenariolist)
            if(Storer.key(scenario.first,scenario.second)  == zipper.storer.getKey()){
                Toast.makeText(requireContext(), "Same scenario from same creator already exists. Delete the old one !", Toast.LENGTH_LONG).show()
                return@registerForActivityResult
            }

        if(zipper.unpackZip()){
            scenariolist.add(Pair(zipper.storer.title,zipper.storer.creator))
            User.saveScenarioList(scenariolist)
            //TODO: reload
        }

    }

    private lateinit var selectedview : View
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        selectedview = v
        val inflater: MenuInflater = requireActivity().menuInflater
        inflater.inflate(R.menu.context_menu, menu)
        super.onCreateContextMenu(menu, v, menuInfo)

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.scenario_delete -> {
                for (scenario in scenariolist){
                    if (Storer.key(scenario.first,scenario.second) == selectedview.tag.toString()){
                        Zipper(requireContext(),scenario.first,scenario.second).deleteScenarioFiles()
                        scenariolist.remove(scenario)
                        User.saveScenarioList(scenariolist)
                        //TODO: reload

                        return true
                    }
                }

                false
            }
            else -> super.onContextItemSelected(item)
        }
    }

    //endregion

}