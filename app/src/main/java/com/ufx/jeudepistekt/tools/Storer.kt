package com.ufx.jeudepistekt.tools

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.ufx.jeudepistekt.R
import java.io.FileNotFoundException

/**
 * Storer
 * This class handles methods to store and load file or content to the right place
 * Used by Scenario at Game run and Zipper at scenario import
 */
class Storer(val title: String, val creator: String, val context: Context) {
    /**
     * The Key is used to link files to a scenario, by renaming at unzip
     * Key is composed with name + creator
     */
    companion object {
        fun key(title: String, creator: String): String = creator + "_" + title + "_"
    }

    fun getKey(): String = key(title, creator)

    /**
     * loadImage
     * Get an image from storage
     */
    fun loadImage(img: String): Bitmap {
        return try {
            BitmapFactory.decodeStream(context.openFileInput(getKey() + img + ".png"))
        } catch (e: FileNotFoundException) {
            try {
                BitmapFactory.decodeStream(context.openFileInput(getKey() + img + ".jpg"))
            } catch (e: FileNotFoundException) {
                BitmapFactory.decodeResource(context.resources, R.drawable.kotlin)
            }
        }
    }

    /**
     * loadJson
     * Get a json from storage and return it as string
     */
    fun loadJson(name: String): String {
        val filestream = context.openFileInput(getKey() + name + ".json")
        return filestream.bufferedReader().use { it.readText() }
    }


}