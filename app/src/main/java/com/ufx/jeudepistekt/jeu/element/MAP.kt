package com.ufx.jeudepistekt.jeu.element
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.startActivity
import com.ufx.jeudepistekt.GameFragment
import com.ufx.jeudepistekt.jeu.Stage

/**
 * MAP : Give a maps link
 * content : Button message
 * additional : 1.longitude - 2.latitude
 */
class MAP(content: String, additional: Array<String>) : Element(content, additional) {

    override fun instantiate(stage: Stage) {
        val lon = additional[0]
        val lat = additional[1]
        val url = "http://maps.google.com/maps?z=20&t=m&q=loc:$lon+-$lat"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

        val par = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        par.setMargins(10, 10, 10, 10)

        val b = Button(GameFragment.context)
        b.text = content
        b.setOnClickListener { startActivity(GameFragment.context, intent, null) }
        b.layoutParams = par

        GameFragment.layout.addView(b)

    }

}