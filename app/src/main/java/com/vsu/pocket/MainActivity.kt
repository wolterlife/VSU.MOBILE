package com.vsu.pocket

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
                R.id.nav_map,R.id.nav_mcanteen, R.id.nav_schedule_system, R.id.nav_link, R.id.nav_faq, R.id.nav_schedule, R.id.nav_calls, R.id.nav_plan, R.id.nav_settings, R.id.nav_map), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        val prefs : SharedPreferences?= this.getPreferences(Context.MODE_PRIVATE);
        var privetstvie = prefs?.getBoolean("data_privetstvie",true)
        if (privetstvie == true) Toast.makeText(applicationContext, "С возвращением, студент :)", Toast.LENGTH_LONG).show() // Приветствие



        // SETTINGS VAR


        // SETTINGS VAR */


    }   // Regedit

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }          // Navigation

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Кнопка настроек вверху справа
      menuInflater.inflate(R.menu.main, menu)
        // settings
        val prefs : SharedPreferences?= this?.getPreferences(Context.MODE_PRIVATE);

        var status_map = prefs?.getBoolean("s_map",false)
        if (status_map == true) {
            menu?.findItem(R.id.menucheck1)?.setVisible(true)
            menu?.findItem(R.id.menucheck2)?.setVisible(true)
            menu?.findItem(R.id.menucheck3)?.setVisible(true)
            menu?.findItem(R.id.menucheck4)?.setVisible(true)
            menu?.findItem(R.id.menucheck5)?.setVisible(true)
        }
        else {
            menu?.findItem(R.id.menucheck1)?.setVisible(false)
            menu?.findItem(R.id.menucheck2)?.setVisible(false)
            menu?.findItem(R.id.menucheck3)?.setVisible(false)
            menu?.findItem(R.id.menucheck4)?.setVisible(false)
            menu?.findItem(R.id.menucheck5)?.setVisible(false)
        }

        var s_univer = prefs?.getBoolean("data_select_univer",true)
        var s_food = prefs?.getBoolean("data_select_food",true)
        var s_hostels = prefs?.getBoolean("data_select_hostels",true)
        var s_stadions = prefs?.getBoolean("data_select_stadions",true)
        var s_garden = prefs?.getBoolean("data_select_garden",true)

        if (s_univer == true) { menu.findItem(R.id.menucheck1).setChecked(true)
        }
        else menu.findItem(R.id.menucheck1).setChecked(false)
        if (s_hostels == true) menu.findItem(R.id.menucheck2).setChecked(true)
        else menu.findItem(R.id.menucheck2).setChecked(false)
        if (s_food == true) menu.findItem(R.id.menucheck3).setChecked(true)
        else menu.findItem(R.id.menucheck3).setChecked(false)
        if (s_stadions == true) menu.findItem(R.id.menucheck4).setChecked(true)
        else menu.findItem(R.id.menucheck4).setChecked(false)
        if (s_garden == true) menu.findItem(R.id.menucheck5).setChecked(true)
        else menu.findItem(R.id.menucheck5).setChecked(false)



        // </> settings
      return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val prefs : SharedPreferences?= this?.getPreferences(Context.MODE_PRIVATE);
        var s_univer = prefs?.getBoolean("data_select_univer",true)
        var s_food = prefs?.getBoolean("data_select_food",true)
        var s_hostels = prefs?.getBoolean("data_select_hostels",true)
        var s_stadions = prefs?.getBoolean("data_select_stadions",true)
        var s_garden = prefs?.getBoolean("data_select_garden",true)
        val navController = findNavController(R.id.nav_host_fragment)

        when(item.itemId){

            R.id.menucheck1 -> {
                if (s_univer == true) { prefs?.edit()?.putBoolean("data_select_univer" , false)?.apply(); item.setChecked(false); }
                else { prefs?.edit()?.putBoolean("data_select_univer" , true)?.apply(); item.setChecked(true)}
                navController.navigate(R.id.nav_map)
            }
            R.id.menucheck2 -> {
                if (s_hostels == true) { prefs?.edit()?.putBoolean("data_select_hostels" , false)?.apply(); item.setChecked(false)}
                else { prefs?.edit()?.putBoolean("data_select_hostels" , true)?.apply(); item.setChecked(true)}
                navController.navigate(R.id.nav_map)
            }
            R.id.menucheck3 -> {
                if (s_food == true) { prefs?.edit()?.putBoolean("data_select_food" ,false)?.apply(); item.setChecked(false)}
                else { prefs?.edit()?.putBoolean("data_select_food" ,true)?.apply(); item.setChecked(true)}
                navController.navigate(R.id.nav_map)
            }
            R.id.menucheck4 -> {
                if (s_stadions == true) { prefs?.edit()?.putBoolean("data_select_stadions" ,false)?.apply(); item.setChecked(false)}
                else { prefs?.edit()?.putBoolean("data_select_stadions" ,true)?.apply(); item.setChecked(true)}
                navController.navigate(R.id.nav_map)
            }
            R.id.menucheck5 -> {
                if (s_garden == true) { prefs?.edit()?.putBoolean("data_select_garden" ,false)?.apply(); item.setChecked(false)}
                else { prefs?.edit()?.putBoolean("data_select_garden" ,true)?.apply(); item.setChecked(true)}
                navController.navigate(R.id.nav_map)
            }
        }
        return super.onOptionsItemSelected(item)
    }





    override fun onPause() {
        super.onPause()

    }


}

