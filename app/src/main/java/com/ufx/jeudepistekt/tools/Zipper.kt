package com.ufx.jeudepistekt.tools

import android.content.Context
import android.net.Uri
import com.ufx.jeudepistekt.jeu.Scenario
import java.io.BufferedInputStream
import java.io.IOException
import java.util.zip.ZipInputStream


class Zipper {
    val context : Context
    var uri : Uri? = null
    lateinit var storer : Storer

    constructor(context: Context, uri : Uri ){
        this.context = context
        this.uri = uri
        readMetaData()
    }

    constructor(context: Context, title: String , creator: String ){
        this.context = context
        storer = Storer(title,creator)

    }


    fun unpackZip(): Boolean {
        if(uri == null)return false
        try {
            val zipStream = ZipInputStream(BufferedInputStream(context.contentResolver.openInputStream(uri!!)))

            var filename: String
            val buffer = ByteArray(1024)
            var count: Int
            var ze = zipStream.nextEntry
            while (ze != null){
                println(ze.name)
                filename = storer.getKey() +ze.name

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


    private fun readMetaData(){
        val zipStream = ZipInputStream(BufferedInputStream(context.contentResolver.openInputStream(uri!!)))

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
                storer = Storer(s.title,s.creator)

                context.deleteFile("temp.json")

                zipStream.close()
                return
            }

            ze = zipStream.nextEntry
        }

        zipStream.close()
    }


    fun deleteScenarioFiles(){
        val files: Array<String> = context.fileList()
        for(file in files){
            if(file.startsWith(storer.getKey())){
                context.deleteFile(file)
            }
        }

    }




}