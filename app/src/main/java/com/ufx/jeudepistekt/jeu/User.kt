package com.ufx.jeudepistekt.jeu

import android.content.Context
import android.content.SharedPreferences
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream


object User {
    private const val KEY_USERNAME = "prefUserNameKey"
    private const val KEY_SCENARIOS = "KeyScenarioList"
    private const val KEY_SCENARIO_VARS = "KeyScenarioVars"
    private const val KEY_SCENARIO_PHASE = "KeyScenarioStep"

    var name: String = "Default"

    private lateinit var sharedPref: SharedPreferences

    private fun getKeyVar(scenarioKey: String) = name + scenarioKey + KEY_SCENARIO_VARS
    private fun getKeyPhase(scenarioKey: String) = name + scenarioKey + KEY_SCENARIO_PHASE


    fun initSharedPref(context: Context) {
        sharedPref =
            context.getSharedPreferences("JeuDePisteKtPreferenceFileKey", Context.MODE_PRIVATE)
    }

    fun saveName(name: String) {
        this.name = name
        with(sharedPref.edit()) {
            putString(KEY_USERNAME, name)
            commit()
        }
    }

    fun loadName() {
        name = sharedPref.getString(KEY_USERNAME, "Default") ?: "ERROR"
    }


    fun saveScenarioList(list: MutableList<Pair<String, String>>) {
        val json: String = Json.encodeToString(list)

        with(sharedPref.edit()) {
            putString(KEY_SCENARIOS, json)
            commit()
        }
    }

    fun loadScenarioList(): MutableList<Pair<String, String>> {
        val json = sharedPref.getString(KEY_SCENARIOS, "") ?: ""
        if (json == "") return mutableListOf()
        return Json.decodeFromString(json)
    }


    fun saveScenario(scenarioKey: String, phase: String, variables: Variables, context: Context) {
        with(sharedPref.edit()) {
            putString(getKeyPhase(scenarioKey), phase)
            commit()
        }

        val stream = context.openFileOutput(getKeyVar(scenarioKey), Context.MODE_PRIVATE)
        stream.write(Json.encodeToString(variables).toByteArray())
        stream.flush()
        stream.close()
    }


    fun loadScenario(scenarioKey: String, context: Context): Pair<String, Variables>? {
        val step = sharedPref.getString(getKeyPhase(scenarioKey), "").orEmpty()
        val stream: InputStream
        try {
            stream = context.openFileInput(getKeyVar(scenarioKey))
        } catch (e: FileNotFoundException) {
            return null
        }
        val variables: Variables = Json.decodeFromString(String(stream.readBytes())) as Variables

        return Pair(step, variables)
    }

    fun resetScenario(scenarioKey: String) {
        with(sharedPref.edit()) {
            remove(getKeyPhase(scenarioKey))
            commit()
        }
        try {
            File(getKeyVar(scenarioKey)).delete()
        } catch (e: FileNotFoundException) {
        }

    }
}