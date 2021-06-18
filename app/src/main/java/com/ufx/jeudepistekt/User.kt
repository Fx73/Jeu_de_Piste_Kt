package com.ufx.jeudepistekt

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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



    fun SaveName(newname : String){
        name = newname
        with (sharedPref.edit()) {
            putString(KEY_TEAMNAME, name)
            commit()
        }
    }

    fun LoadName(){
        name = sharedPref.getString(KEY_TEAMNAME, "Default")?:"ERROR"
    }

    fun SaveScenarioList(list :MutableList<Pair<String,String>>){
        val json: String = Gson().toJson(list)

        with (sharedPref.edit()) {
            putString(KEY_SCELIST, json)
            commit()
        }
    }

    fun LoadScenarioList(): MutableList<Pair<String,String>> {
        val json = sharedPref.getString(KEY_SCELIST, "") ?: ""
        if (json == "") return mutableListOf()
        val outtype = object : TypeToken<List<Pair<String,String>>>() {}.type
        return Gson().fromJson(json, outtype)
    }



    fun SaveScenario(scenariokey: String, step : Int, vars : MutableMap<String, Any>){
        val keyvar = name + scenariokey + KEY_SCENARIOVARS
        val keystep = name + scenariokey + KEY_SCENARIOSTEP

        with (sharedPref.edit()) {
            putInt(keyvar, step)
            commit()
        }
        val stream = ObjectOutputStream( context.openFileOutput(keystep, Context.MODE_PRIVATE))
        stream.writeObject(vars)
        stream.flush()
        stream.close()
    }


    fun LoadScenario(scenariokey: String): Pair<Int, MutableMap<String, Any>>? {
        val keyvar = name + scenariokey + KEY_SCENARIOVARS
        val keystep = name + scenariokey + KEY_SCENARIOSTEP

        val step = sharedPref.getInt(keystep,0)
        val stream : ObjectInputStream
        try{
            stream = ObjectInputStream( context.openFileInput(keyvar))
        }catch (e : FileNotFoundException){return null}

        @Suppress("UNCHECKED_CAST") val vars : MutableMap<String, Any> = stream.readObject() as MutableMap<String, Any>

        return Pair(step,vars)
}

}