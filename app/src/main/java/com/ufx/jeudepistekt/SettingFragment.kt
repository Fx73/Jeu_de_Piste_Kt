package com.ufx.jeudepistekt

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ufx.jeudepistekt.jeu.User


/**
 * The app parameters. Here you can :
 *  - Change the user/name (link with data save)
 */
class SettingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        view.findViewById<EditText>(R.id.nameedit).setText(User.name)
        view.findViewById<EditText>(R.id.nameedit).setOnKeyListener{ _: View, _: Int, keyEvent: KeyEvent -> saveKey(keyEvent)}
        view.findViewById<Button>(R.id.button_name).setOnClickListener{saveName()}
        return view
    }

    private fun saveKey(keyEvent: KeyEvent):Boolean{
        if (keyEvent.keyCode == KeyEvent.KEYCODE_ENTER)
            saveName()
        return true
    }
    private fun saveName(){
        val newname = requireView().findViewById<EditText>(R.id.nameedit).text.toString()
        User.saveName(newname)
        Toast.makeText(requireContext(), getString(R.string.newname) + newname, Toast.LENGTH_LONG).show()
        requireView().findViewById<EditText>(R.id.nameedit).clearFocus()
    }

}