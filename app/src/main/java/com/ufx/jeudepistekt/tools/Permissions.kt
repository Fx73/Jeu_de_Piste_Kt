package com.ufx.jeudepistekt.tools

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * Permission Class
 * This class handles methods that will ask for permissions
 */
object Permissions {

    fun askStoragePermission(context:Context):Boolean{
        return askPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    fun askPermission(context:Context, permission : String):Boolean{
        return if (ContextCompat.checkSelfPermission( context, permission   ) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(  context as Activity,   arrayOf(permission), 1)
                ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        } else true
    }



}