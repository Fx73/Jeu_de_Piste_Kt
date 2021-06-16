package com.ufx.jeudepistekt

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class User (context: Context)
{
    companion object{
        const val KEY_TEAMNAME= "prefUserNameKey"
        const val KEY_SCELIST= "KeyScenarioList"
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

    fun LoadName(){
        name = sharedPref.getString(KEY_TEAMNAME, "Default")?:"ERROR"
    }

    fun SaveScenarioList(list :List<Pair<String,String>>){
        val json: String = Gson().toJson(list)

        with (sharedPref.edit()) {
            putString(KEY_SCELIST, json)
            commit()
        }
    }

    fun LoadScenarioList(): List<Pair<String,String>> {
        val json = sharedPref.getString(KEY_SCELIST, "") ?: ""
        if (json == "") return listOf()
        val outtype = object : TypeToken<List<Pair<String,String>>>() {}.type
        return Gson().fromJson(json, outtype)
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