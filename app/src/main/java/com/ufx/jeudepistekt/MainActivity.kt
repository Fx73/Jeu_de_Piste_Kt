package com.ufx.jeudepistekt

import android.media.Image
import android.os.Bundle
import android.view.Gravity
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import com.ufx.jeudepistekt.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var sAlayout : LinearLayout
    lateinit var sBlayout : LinearLayout

    val scenariolist = listOf("Test1","Test2","Test3","Test4","Test5")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        sAlayout = findViewById(R.id.scenariolayoutA)
        sBlayout = findViewById(R.id.scenariolayoutB)


        var sens = true
        for (scenario in scenariolist){
            val card = CardView(this)
            val img = ImageView(this)

            img.setBackgroundResource(this.resources.getIdentifier("start", "drawable", this.packageName))
            card.addView(img)

            val r = LinearLayout.LayoutParams(0,200)
            r.setMargins(4,4,4,4)

            if (sens) sAlayout.addView(card) else sBlayout.addView(card)

            sens = !sens
        }
        sAlayout.invalidate()
        sBlayout.invalidate()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

}