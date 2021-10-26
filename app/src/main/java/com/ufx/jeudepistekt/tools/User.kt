package com.ufx.jeudepistekt.tools

import android.content.Context
import android.content.SharedPreferences
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.FileNotFoundException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream


class User (val context: Context)
{
    companion object{
        const val KEY_TEAMNAME= "prefUserNameKey"
        const val KEY_SCELIST= "KeyScenarioList"
        const val KEY_SCENARIOVARS= "KeyScenarioVars"
        const val KEY_SCENARIOSTEP= "KeyScenarioStep"

        var name : String = "Default"
    }

    private val sharedPref : SharedPreferences = context.getSharedPreferences("JeuDePisteKtPreferenceFileKey",Context.MODE_PRIVATE)

    private fun getKeyVar(scenariokey: String) = name + scenariokey + KEY_SCENARIOVARS
    private fun getKeyStep(scenariokey: String) = name + scenariokey + KEY_SCENARIOSTEP

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



    fun saveScenario(scenariokey: String, step : Int, vars : MutableMap<String, Int>){
        val keyvar = getKeyVar(scenariokey)
        val keystep = getKeyStep(scenariokey)

        with (sharedPref.edit()) {
            putInt(keystep, step)
            commit()
        }
        val stream = ObjectOutputStream( context.openFileOutput(keyvar, Context.MODE_PRIVATE))
        stream.writeObject(vars)
        stream.flush()
        stream.close()
    }


    fun loadScenario(scenariokey: String): Pair<Int, MutableMap<String, Any>>? {
        val keyvar = getKeyVar(scenariokey)
        val keystep = getKeyStep(scenariokey)

        val step = sharedPref.getInt(keystep,0)
        val stream : ObjectInputStream
        try{
            stream = ObjectInputStream( context.openFileInput(keyvar))
        }catch (e : FileNotFoundException){return null}

        @Suppress("UNCHECKED_CAST") val vars : MutableMap<String, Any> = stream.readObject() as MutableMap<String, Any>

        return Pair(step,vars)
}

}