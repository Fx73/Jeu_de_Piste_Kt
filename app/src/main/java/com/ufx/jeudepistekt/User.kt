package com.ufx.jeudepistekt

import android.content.Context
import android.content.SharedPreferences


class User (context: Context)
{
    companion object{
        const val KEY_TEAMNAME= "prefUserNameKey"
        const val KEY_SCENARIO= "KeyScenario"
        var name : String = "Default"

        private fun KEY_SCENARIO(hash:String):String = name+KEY_SCENARIO+hash
    }



    private val sharedPref : SharedPreferences = context.getSharedPreferences("JeuDePisteKtPreferenceFileKey",Context.MODE_PRIVATE)



    fun SaveName(){
        with (sharedPref.edit()) {
            putString(KEY_TEAMNAME, name)
            commit()
        }
    }

    fun SaveScenario(hash: String, scenariosave: String){
        with (sharedPref.edit()) {
            putString(KEY_SCENARIO(hash), scenariosave)
            commit()
        }
    }


    fun LoadScenario(hash: String): String {
        return sharedPref.getString(KEY_SCENARIO(hash), hash) ?: ""
    }

}