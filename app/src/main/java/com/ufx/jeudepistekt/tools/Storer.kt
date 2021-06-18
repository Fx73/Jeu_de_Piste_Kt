package com.ufx.jeudepistekt.tools

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.ufx.jeudepistekt.R
import java.io.FileNotFoundException

class Storer(val title: String,val creator: String, val context : Context) {
    companion object{
        fun key(title: String, creator: String) = creator+"_"+title+"_"
    }
    fun getKey() = key(title,creator)


    fun loadImage(img : String): Bitmap {
        var bitmap : Bitmap
        try {
            bitmap = BitmapFactory.decodeStream(context.openFileInput(getKey()+img))
        }catch (e: FileNotFoundException){
            bitmap = BitmapFactory.decodeResource(context.resources,
                R.drawable.kotlin)
        }
        return bitmap
    }

    fun loadJson(name: String): String {
        val filestream = context.openFileInput(getKey() + name + ".json")
        return filestream.bufferedReader().use { it.readText() }
    }


}