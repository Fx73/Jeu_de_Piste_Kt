package com.ufx.jeudepistekt.tools

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Permissions {
    companion object{
        fun askPermission(context:Context, permission : String):Boolean{
            return if (ContextCompat.checkSelfPermission( context, permission   ) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(  context as Activity,   arrayOf(permission), 1)
                ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
            } else true
        }


    }


}