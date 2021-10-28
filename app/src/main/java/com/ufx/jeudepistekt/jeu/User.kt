package com.ufx.jeudepistekt.jeu

import android.content.Context
import android.content.SharedPreferences
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.*


object User
{
    const val KEY_TEAMNAME= "prefUserNameKey"
    const val KEY_SCELIST= "KeyScenarioList"
    const val KEY_SCENARIOVARS= "KeyScenarioVars"
    const val KEY_SCENARIOPHASE= "KeyScenarioStep"

    var name : String = "Default"

    private lateinit var sharedPref : SharedPreferences

    private fun getKeyVar(scenariokey: String) = name + scenariokey + KEY_SCENARIOVARS
    private fun getKeyPhase(scenariokey: String) = name + scenariokey + KEY_SCENARIOPHASE



    fun initSharedPref(context: Context){
        sharedPref = context.getSharedPreferences("JeuDePisteKtPreferenceFileKey",Context.MODE_PRIVATE)
    }

    fun saveName(newname : String){
        name = newname
        with (sharedPref.edit()) {
            putString(KEY_TEAMNAME, name)
            commit()
        }
    }

    fun loadName(){
        name = sharedPref.getString(KEY_TEAMNAME, "Default")?:"ERROR"
    }


    fun saveScenarioList(list :MutableList<Pair<String,String>>){
        val json: String = Json.encodeToString(list)

        with (sharedPref.edit()) {
            putString(KEY_SCELIST, json)
            commit()
        }
    }

    fun loadScenarioList(): MutableList<Pair<String,String>> {
        val json = sharedPref.getString(KEY_SCELIST, "") ?: ""
        if (json == "") return mutableListOf()
        return Json.decodeFromString(json)
    }



    fun saveScenario(scenariokey: String, phase : String, variables : Variables,context: Context){
        with (sharedPref.edit()) {
            putString(getKeyPhase(scenariokey), phase)
            commit()
        }

        val stream = context.openFileOutput(getKeyVar(scenariokey), Context.MODE_PRIVATE)
        stream.write(Json.encodeToString(variables).toByteArray())
        stream.flush()
        stream.close()
    }


    fun loadScenario(scenariokey: String,context: Context): Pair<String, Variables>? {
        val step = sharedPref.getString(getKeyPhase(scenariokey),"").orEmpty()
        val stream : InputStream
        try{
            stream = context.openFileInput(getKeyVar(scenariokey))
        }catch (e : FileNotFoundException){return null}
        val variables : Variables = Json.decodeFromString(String(stream.readBytes())) as Variables

        return Pair(step,variables)
}

}