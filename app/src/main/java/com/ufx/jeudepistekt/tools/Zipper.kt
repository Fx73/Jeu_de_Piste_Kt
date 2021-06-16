package com.ufx.jeudepistekt.tools

import android.content.Context
import android.widget.Toast
import com.ufx.jeudepistekt.jeu.Scenario
import java.io.BufferedInputStream
import java.io.FileInputStream
import java.io.IOException
import java.util.zip.ZipInputStream


class Zipper(val context: Context) {


    fun unpackScenario(path: String, scenariolist: List<Pair<String,String>>):Boolean{
        val key = readMetaData(path)
        for (scenario in scenariolist){
            if(scenario.second+"_"+scenario.first == key){
                Toast.makeText(context, "Same scenario from same creator already exists. Delete the old one !", Toast.LENGTH_LONG).show()
                return false
            }
        }
        unpackZip(path, key)

        return true
    }


    private fun unpackZip(path: String, key:String): Boolean {

        try {
            val zipStream = ZipInputStream(BufferedInputStream(FileInputStream(path)))

            var filename: String
            val buffer = ByteArray(1024)
            var count: Int
            var ze = zipStream.nextEntry
            while (ze != null){
                filename = key+"_"+ze.name

                context.openFileOutput(filename, Context.MODE_PRIVATE).use { it ->
                    while (zipStream.read(buffer).also { count = it } != -1) {
                        it.write(buffer, 0, count)
                    }
                }

                zipStream.closeEntry()
                ze = zipStream.nextEntry
            }

            zipStream.close()
        } catch (e: IOException) {
            return false
        }
        return true
    }


    private fun readMetaData(path: String):String{
        val zipStream = ZipInputStream(BufferedInputStream(FileInputStream(path)))

        var key = ""
        val buffer = ByteArray(1024)
        var count: Int
        var ze = zipStream.nextEntry
        while (ze != null) {

            if(ze.name == "ScenarioFile.json"){

                context.openFileOutput("temp.json", Context.MODE_PRIVATE).use { it ->
                    while (zipStream.read(buffer).also { count = it } != -1) {
                        it.write(buffer, 0, count)
                    }
                }

                val s = Scenario.buildScenarioFromJson(context,"temp.json")
                key = key(s.creator,s.title)

                context.deleteFile("temp.json")
            }

            ze = zipStream.nextEntry
        }

        zipStream.close()
        return key
    }


    fun deleteScenarioFiles(title : String, creator : String){
        val key = key(creator,title)
        val files: Array<String> = context.fileList()
        for(file in files){
            if(file.startsWith(key)){
                context.deleteFile(file)
            }
        }

    }

    fun deleteScenarioFiles(key : String){
        val files: Array<String> = context.fileList()
        for(file in files){
            if(file.startsWith(key)){
                context.deleteFile(file)
            }
        }

    }
    companion object{
        fun key(title: String,creator: String)= creator+"_"+title
    }

}