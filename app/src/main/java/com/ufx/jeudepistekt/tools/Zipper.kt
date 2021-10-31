package com.ufx.jeudepistekt.tools

import android.content.Context
import android.net.Uri
import com.ufx.jeudepistekt.jeu.Scenario
import java.io.BufferedInputStream
import java.io.IOException
import java.util.zip.ZipInputStream

/**
 * Zipper
 * This class handles methods to unzip a scenario package properly.
 * It require the Storer class to work !
 */
class Zipper {
    val context: Context
    private var uri: Uri? = null
    lateinit var storer: Storer

    constructor(context: Context, uri: Uri) {
        this.context = context
        this.uri = uri
        readMetaData()
    }

    constructor(context: Context, title: String, creator: String) {
        this.context = context
        storer = Storer(title, creator, context)

    }


    /**
     * unpackZip
     * Copy the zip contents in the app data folder, renaming with the proper key
     * WARNING : it need the storer built (with one the constructor)
     */
    fun unpackZip(): Boolean {
        if (uri == null) return false
        try {
            val zipStream =
                ZipInputStream(BufferedInputStream(context.contentResolver.openInputStream(uri!!)))

            var filename: String
            val buffer = ByteArray(1024)
            var count: Int
            var ze = zipStream.nextEntry
            while (ze != null) {
                filename = storer.getKey() + ze.name

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


    /**
     * readMetaData
     * Build a storer to a proper file storage, from the name + creator found in the zip
     * Needed before unzipping
     */
    private fun readMetaData() {
        val zipStream =
            ZipInputStream(BufferedInputStream(context.contentResolver.openInputStream(uri!!)))

        val buffer = ByteArray(1024)
        var count: Int
        var ze = zipStream.nextEntry
        while (ze != null) {

            if (ze.name == "ScenarioFile.json") {
                context.openFileOutput("temp.json", Context.MODE_PRIVATE).use { it ->
                    while (zipStream.read(buffer).also { count = it } != -1) {
                        it.write(buffer, 0, count)
                    }
                }
                val json = context.openFileInput("temp.json").bufferedReader().use { it.readText() }
                val s = Scenario.buildScenarioFromJson(json)
                storer = Storer(s.title, s.creator, context)

                context.deleteFile("temp.json")

                zipStream.close()
                return
            }

            ze = zipStream.nextEntry
        }

        zipStream.close()
    }

    /**
     * deleteScenarioFiles
     * Delete files linked to the scenario
     */
    fun deleteScenarioFiles() {
        val files: Array<String> = context.fileList()
        for (file in files) {
            if (file.startsWith(storer.getKey())) {
                context.deleteFile(file)
            }
        }

    }


}