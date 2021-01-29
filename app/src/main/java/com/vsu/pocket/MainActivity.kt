package com.vsu.pocket

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_mcanteen, R.id.nav_link, R.id.nav_faq, R.id.nav_schedule, R.id.nav_calls, R.id.nav_plan, R.id.nav_settings), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        Toast.makeText(applicationContext, "С возвращением, студент :)", Toast.LENGTH_LONG).show() // Приветствие
    }   // Regedit

/*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        // Кнопка настроек вверху справа
      menuInflater.inflate(R.menu.main, menu)
      //Toast.makeText(applicationContext, "VSU POCKET v0.3", Toast.LENGTH_LONG).show()
      return true
  }
*/ // 3 точки справа сверху

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }          // Navigation

    // ССЫЛКИ
    fun vsufunc(view: View) { val vsuintent = Intent(Intent.ACTION_VIEW, Uri.parse("https://vsu.by")); startActivity(vsuintent) }
    fun sdofunc(view: View) { val sdointent = Intent(Intent.ACTION_VIEW, Uri.parse("https://sdo.vsu.by/")); startActivity(sdointent) }
    fun youtubefunc(view: View) { val youtubeintent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCo18_krqqaEWSb6_cbHnupQ")); startActivity(youtubeintent) }
    fun instagramfunc(view: View) { val instagramintent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/tvu.vsu/")); startActivity(instagramintent) }
    fun vkfunc(view: View) { val vkintent = Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/vsu_vitebsk")); startActivity(vkintent) }
    fun facebookfunc(view: View) { val facebookintent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/vsu.by/")); startActivity(facebookintent) }
    fun twitterfunc(view: View) { val twitterintent = Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/VSU_Vitebsk")); startActivity(twitterintent) }
    fun telegramfunc(view: View) { val telegramintent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/vsumasherov/")); startActivity(telegramintent) }

    // <- ТУТ ПРОДОЛЖЕНИЕ
}

