package com.vsu.pocket.ui.schedule

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.AdapterView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.vsu.pocket.MainActivity
import com.vsu.pocket.R
import com.vsu.pocket.ui.schedule_system.ScheduleSystemViewModel
import kotlinx.android.synthetic.main.fragment_mcanteen.*
import kotlinx.android.synthetic.main.fragment_schedule.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.FileInputStream
import java.nio.file.FileSystem
import java.nio.file.Files
import java.nio.file.Files.createFile
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.concurrent.timer
import kotlin.io.path.ExperimentalPathApi


// фгияк учёт специальности
class ScheduleFragment : Fragment() {

    private lateinit var scheduleViewModel: ScheduleSystemViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        scheduleViewModel =
            ViewModelProviders.of(this).get(ScheduleSystemViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_schedule, container, false)
        return root

    }

    @SuppressLint("ShowToast")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // date
        val sdf = SimpleDateFormat("EEEE")
        val d = Date()
        val dayOfTheWeek: String = sdf.format(d)
        textView14.setText(dayOfTheWeek);
        //


        // SETTINGS

        var flag_load = false;
        var goodConnection = false;
        val prefs: SharedPreferences? = activity?.getPreferences(Context.MODE_PRIVATE);
        var selected = prefs?.getString("department", "- Не выбран -")
        var selectedcourse =
            prefs?.getString("department_course", "- Не выбран -")// стринги принятые
        var Smerc = prefs?.getBoolean("data_mercanie", false)
        prefs?.edit()?.putBoolean("s_map", false)?.apply();
        setHasOptionsMenu(false);

        // settings ### if selected устранение мерцания
        if (Smerc == true) webschedule.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        else webschedule.setLayerType(View.LAYER_TYPE_NONE, null);
        var napomchange = prefs?.getBoolean("data_napom", true)
        // settings ###

        // FB CONNECT //
        val database = Firebase.database
        val myRef = database.getReference("/")
        //
        val remoteConfig = FirebaseRemoteConfig.getInstance();
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(3600)
            .setFetchTimeoutInSeconds(60)
            .build()
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate().addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                goodConnection = true;
                if (napomchange == true && selected != "- Не выбран -") Toast.makeText(
                    context,
                    "Факультет можно изменить в настройках приложения",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                goodConnection = false;
                Toast.makeText(
                    context,
                    "Загрузка не удалась. Проверьте подключение к интернету и перезагрузите расписание",
                    Toast.LENGTH_LONG
                ).show()
            }
        }


        sp_option.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View, selectedItemPosition: Int, selectedId: Long
            ) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        if ((selected != "- Не выбран -") && (selectedcourse != "- Не выбран -")) {
            imageView2.visibility = View.GONE
            textView6.visibility = View.GONE
            sp_option.visibility = View.GONE
            sp_option2.visibility = View.GONE
            textView8.visibility = View.GONE
            divider3.visibility = View.GONE
            divider6.visibility = View.GONE
            divider7.visibility = View.GONE
            divider8.visibility = View.GONE
            buttonSave.visibility = View.GONE
            webschedule.visibility = View.GONE
            progressBar?.visibility = View.GONE

            webschedule.getSettings().setJavaScriptEnabled(true);
            webschedule.setInitialScale(100) // SCALE
            if ((napomchange == true) && goodConnection) Toast.makeText(context, "Факультет и курс можно изменить в настройках приложения", Toast.LENGTH_SHORT).show()
            progressBar?.visibility = View.VISIBLE;
            // ФХБИГН

            fun getUrl(url: String) { // TODO: ПРОДУБЛИРОВАТЬ ЭТОТ КОД В КОНЦЕ В САМОМ НИЗУ
                // FB PART
                val storage = Firebase.storage
                val storageRef = storage.reference;
                val islandRef = storageRef.child("$url.xls")

                // Получение файла если существует
                fun getFile(): File { // Если в настройках задано, что файла нет = создаёт его, иначе возвращает его
                    val pathStr = prefs?.getString("path_schedule", "null_when_get_prefs");
                    if (pathStr == "null_when_get_prefs") {
                        val file = File.createTempFile("schedule", "xls")
                        prefs.edit().putString("path_schedule", file.toString()).apply();
                        return file
                    }
                    else {
                        return File(pathStr)
                    }

                }
                val localFile = getFile();
                // *


                // Проверка интернет соединения
                fun checkNetworkConnection(connectivityManager: ConnectivityManager, network: Network?): Boolean {
                    connectivityManager.getNetworkCapabilities(network)?.also {
                        if (it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                            return true
                        }
                        else if (it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                            return true
                        }
                    }
                    return false
                }

                fun isNetworkConnected(context: Context): Boolean {
                    var result = false;
                    (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            result = checkNetworkConnection(this, this.activeNetwork)
                        } else {
                            val networks = this.allNetworks
                            for (network in networks) {
                                if (checkNetworkConnection(this, network)) {
                                    result = true
                                }
                            }
                        }
                    }
                    return result;
                }
                //
                println(context?.let { isNetworkConnected(it) })
                println(context?.let { isNetworkConnected(it) })
                println(context?.let { isNetworkConnected(it) })
                println(context?.let { isNetworkConnected(it) })
                println(context?.let { isNetworkConnected(it) })
                println(context?.let { isNetworkConnected(it) })
                println(context?.let { isNetworkConnected(it) })
                println(context?.let { isNetworkConnected(it) })
                println(context?.let { isNetworkConnected(it) })
                println(localFile);
                println(localFile);
                println(localFile);
                println(localFile);
                println(localFile);
                println(localFile);
                println(localFile);
                println(localFile.length());
                println(localFile.length());
                println(localFile.length());
                println(localFile.length());
                println(localFile.length());
                println(localFile.length());
                println(localFile.length());
                println(localFile.length());

                islandRef.getFile(localFile).addOnSuccessListener {

                }.addOnFailureListener {
                    Toast.makeText(context, "DOWNLOAD ERROR! $url", Toast.LENGTH_LONG).show()
                }

//                Thread.sleep(1500)
                while(localFile.length().toInt() == 0) {}
                println(localFile.length().toInt())
                val inputStream = FileInputStream(localFile)
                //

                // excel reading (schedule.xls)

                //Instantiate Excel workbook using existing file:
                var xlWb = WorkbookFactory.create(inputStream)

                //Row index specifies the row in the worksheet (starting at 0):
                val rowNumber = 0
                //Cell index specifies the column within the chosen row (starting at 0):
                val columnNumber = 2

                //Get reference to first sheet:
                val xlWs = xlWb.getSheetAt(0)
                Toast.makeText(context, (xlWs?.getRow(rowNumber)?.getCell(columnNumber)).toString(), Toast.LENGTH_LONG).show();
                //
            }

            // СЕКЦИЯ НЕ ПЕРВОЙ ЗАГРУЗКИ, А УЖЕ ЗАДАННОЙ
            // БИО
            if (selected == "ФХБИГН" && selectedcourse == "1 курс") { getUrl("BIO (1)"); }
            if (selected == "ФХБИГН" && selectedcourse == "2 курс") { getUrl("BIO (2)"); }
            if (selected == "ФХБИГН" && selectedcourse == "3 курс") { getUrl("BIO (3)"); }
            if (selected == "ФХБИГН" && selectedcourse == "4 курс") { getUrl("BIO (4)"); }
            // ПФ
            if (selected == "ПФ" && selectedcourse == "1 курс") { getUrl("PF (1)"); }
            if (selected == "ПФ" && selectedcourse == "2 курс") { getUrl("PF (2)"); }
            if (selected == "ПФ" && selectedcourse == "3 курс") { getUrl("PF (3)"); }
            if (selected == "ПФ" && selectedcourse == "4 курс") { getUrl("PF (4)"); }
            // ФМИИТ
            if (selected == "ФМИИТ" && selectedcourse == "1 курс") { getUrl("FMIT (1)"); }
            if (selected == "ФМИИТ" && selectedcourse == "2 курс") { getUrl("FMIT (2)"); }
            if (selected == "ФМИИТ" && selectedcourse == "3 курс") { getUrl("FMIT (3)"); }
            if (selected == "ФМИИТ" && selectedcourse == "4 курс") { getUrl("FMIT (4)"); }
            // ФСПИП
            if (selected == "ФСПИП" && selectedcourse == "1 курс") { getUrl("FSPIP (1)"); }
            if (selected == "ФСПИП" && selectedcourse == "2 курс") { getUrl("FSPIP (2)"); }
            if (selected == "ФСПИП" && selectedcourse == "3 курс") { getUrl("FSPIP (3)"); }
            if (selected == "ФСПИП" && selectedcourse == "4 курс") { getUrl("FSPIP (4)"); }
            // ФФКИС
            if (selected == "ФФКИС" && selectedcourse == "1 курс") { getUrl("FKIS (1)"); }
            if (selected == "ФФКИС" && selectedcourse == "2 курс") { getUrl("FKIS (2)"); }
            if (selected == "ФФКИС" && selectedcourse == "3 курс") { getUrl("FKIS (3)"); }
            if (selected == "ФФКИС" && selectedcourse == "4 курс") { getUrl("FKIS (4)"); }
            // ФГИЯК
            if (selected == "ФГИЯК") webschedule.setInitialScale(200) // EXPERIMENTAL LARGE BUTTON
            if (selected == "ФГИЯК" && selectedcourse == "1 курс") { getUrl("FGIYAK (1)"); }
            if (selected == "ФГИЯК" && selectedcourse == "2 курс") { getUrl("FGIYAK (2)"); }
            if (selected == "ФГИЯК" && selectedcourse == "3 курс") { getUrl("FGIYAK (3)"); }
            if (selected == "ФГИЯК" && selectedcourse == "4 курс") { getUrl("FGIYAK (4)"); }
            if (selected == "ФГИЯК" && selectedcourse == "5 курс") { getUrl("FGIYAK (5)"); }
            // ХГФ
            if (selected == "ХГФ" && selectedcourse == "1 курс") { getUrl("HGF (1)"); }
            if (selected == "ХГФ" && selectedcourse == "2 курс") { getUrl("HGF (2)"); }
            if (selected == "ХГФ" && selectedcourse == "3 курс") { getUrl("HGF (3)"); }
            if (selected == "ХГФ" && selectedcourse == "4 курс") { getUrl("HGF (4)"); }
            if (selected == "ХГФ" && selectedcourse == "5 курс") { getUrl("HGF (5)"); }
            // ЮФ
            if (selected == "ЮФ" && selectedcourse == "1 курс") { getUrl("YF (1)"); }
            if (selected == "ЮФ" && selectedcourse == "2 курс") { getUrl("YF (2)"); }
            if (selected == "ЮФ" && selectedcourse == "3 курс") { getUrl("YF (3)"); }
            if (selected == "ЮФ" && selectedcourse == "4 курс") { getUrl("YF (4)"); }
            // ФОИГ
            if (selected == "ФОИГ" && selectedcourse == "1 курс") { getUrl("FOIG (1)"); }
            if (selected == "ФОИГ" && selectedcourse == "2 курс") { getUrl("FOIG (2)"); }
            if (selected == "ФОИГ" && selectedcourse == "3 курс") { getUrl("FOIG (3)"); }
            if (selected == "ФОИГ" && selectedcourse == "4 курс") { getUrl("FOIG (4)"); }
            // hide elements
            webschedule.setWebViewClient(object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    progressBar?.visibility = View.GONE;
                    webschedule?.loadUrl(
                        "javascript:(function() { " +
                                "document.getElementsByClassName('docs_panel_wrap')[0].style.display='none'; })()"
                    )
                    if (flag_load == false) {
                        flag_load = true;
                        webschedule?.visibility = View.VISIBLE;
                        textView14?.visibility = View.VISIBLE;
                        textView15?.visibility = View.VISIBLE;
                        progressBar?.visibility = View.GONE;
                        webschedule?.reload();
                    };
                }
            })
        }

        buttonSave.setOnClickListener {

            // Проверка интернет соединения
            fun checkNetworkConnection(connectivityManager: ConnectivityManager, network: Network?): Boolean {
                connectivityManager.getNetworkCapabilities(network)?.also {
                    if (it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true
                    }
                    else if (it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true
                    }
                }
                return false
            }

            fun isNetworkConnected(context: Context): Boolean {
                var result = false;
                (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        result = checkNetworkConnection(this, this.activeNetwork)
                    } else {
                        val networks = this.allNetworks
                        for (network in networks) {
                            if (checkNetworkConnection(this, network)) {
                                result = true
                            }
                        }
                    }
                }
                return result;
            }

            var selected: String = sp_option.getSelectedItem().toString()
            var selectedcourse: String = sp_option2.getSelectedItem().toString()
            if ((selectedcourse != "- Не выбран -") && (selected == "- Не выбран -")) {
                Toast.makeText(context, "Вы не выбрали факультет!", Toast.LENGTH_LONG).show()
            }
            if ((selectedcourse == "- Не выбран -") && (selected != "- Не выбран -")) {
                Toast.makeText(context, "Вы не выбрали курс!", Toast.LENGTH_LONG).show()
            }
            if ((selectedcourse == "- Не выбран -") && (selected == "- Не выбран -")) {
                Toast.makeText(context, "Вы не выбрали курс и факультет!", Toast.LENGTH_LONG).show()
            }

            if ((selectedcourse != "- Не выбран -") && (selected != "- Не выбран -")) {
                if (context?.let { isNetworkConnected(it) } == true) {
                prefs?.edit()?.putString("department", selected)
                    ?.apply(); // Если не было настроек, но они введены успешно
                prefs?.edit()?.putString("department_course", selectedcourse)?.apply();
                // скрывание всего
                imageView2.visibility = View.GONE
                textView6.visibility = View.GONE
                sp_option.visibility = View.GONE
                sp_option2.visibility = View.GONE
                textView8.visibility = View.GONE
                divider3.visibility = View.GONE
                divider6.visibility = View.GONE
                divider7.visibility = View.GONE
                divider8.visibility = View.GONE
                buttonSave.visibility = View.GONE
                progressBar?.visibility = View.VISIBLE;
                textView14.visibility = View.VISIBLE;
                textView15.visibility = View.VISIBLE;
                webschedule.visibility = View.GONE


                // Отправка данных на FireBase
                val mFirebaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(
                    requireContext()
                )
                val params = Bundle()
                params.putString(FirebaseAnalytics.Param.ITEM_ID, selected)
                params.putString(FirebaseAnalytics.Param.ITEM_NAME, selectedcourse)
                mFirebaseAnalytics.logEvent("Course_Info", params)
                //

                //
                webschedule.getSettings().setJavaScriptEnabled(true);
                webschedule.setInitialScale(100)
                //if (napomchange == true) Toast.makeText(context, "Факультет можно изменить в настройках !!! приложения", Toast.LENGTH_SHORT).show()

                // hide elements
                webschedule.setWebViewClient(object : WebViewClient() {
                    override fun onPageFinished(view: WebView, url: String) {
                        webschedule.loadUrl(
                            "javascript:(function() { " +
                                    "document.getElementsByClassName('docs_panel_wrap')[0].style.display='none'; })()"
                        )
                        if (flag_load == false) {
                            flag_load = true;
                            textView14.visibility = View.VISIBLE;
                            textView15.visibility = View.VISIBLE;
                            progressBar?.visibility = View.GONE;
                            //webschedule.reload();
                            webschedule.visibility = View.VISIBLE;
                        }
                    }
                })

                // ФХБИГН
                // Выводит то что надо, но только при задаче первичных настроек
//                    /*

                fun getUrl(url: String) { // TODO: ПРОДУБЛИРОВАТЬ ЭТОТ КОД В КОНЦЕ В САМОМ НИЗУ
                    // FB PART
                    val storage = Firebase.storage
                    val storageRef = storage.reference;
                    val islandRef = storageRef.child("$url.xls")

                    // Получение файла если существует

                    fun getFile(): File { // Если в настройках задано, что файла нет = создаёт его, иначе возвращает его
                        val pathStr = prefs?.getString("path_schedule", "null_when_get_prefs");
                        if (pathStr == "null_when_get_prefs") {
                            val file = File.createTempFile("schedule", "xls")
                            prefs.edit().putString("path_schedule", file.toString()).apply();
                            return file
                        }
                        else {
                            return File(pathStr)
                        }

                    }

                    val localFile = getFile();
                    // *


                    println(localFile);
                    println(localFile);
                    println(localFile);
                    println(localFile);
                    println(localFile);
                    println(localFile);
                    println(localFile);
                    println(localFile.length());
                    println(localFile.length());
                    println(localFile.length());
                    println(localFile.length());
                    println(localFile.length());
                    println(localFile.length());
                    println(localFile.length());
                    println(localFile.length());
                    islandRef.getFile(localFile).addOnSuccessListener {

                    }.addOnFailureListener {
                        Toast.makeText(context, "DOWNLOAD ERROR! $url", Toast.LENGTH_LONG).show()
                    }

                    while(localFile.length().toInt() == 0) {}
                    val inputStream = FileInputStream(localFile)
                    //

                    // excel reading (schedule.xls)

                    //Instantiate Excel workbook using existing file:
                    var xlWb = WorkbookFactory.create(inputStream)

                    //Row index specifies the row in the worksheet (starting at 0):
                    val rowNumber = 0
                    //Cell index specifies the column within the chosen row (starting at 0):
                    val columnNumber = 2

                    //Get reference to first sheet:
                    val xlWs = xlWb.getSheetAt(0)
                    Toast.makeText(context, (xlWs.getRow(rowNumber).getCell(columnNumber)).toString(), Toast.LENGTH_LONG).show();
                    //
                }

                // БИО
            if (selected == "ФХБИГН" && selectedcourse == "1 курс") { getUrl("BIO (1)"); }
            if (selected == "ФХБИГН" && selectedcourse == "2 курс") { getUrl("BIO (2)"); }
            if (selected == "ФХБИГН" && selectedcourse == "3 курс") { getUrl("BIO (3)"); }
            if (selected == "ФХБИГН" && selectedcourse == "4 курс") { getUrl("BIO (4)"); }
            // ПФ
            if (selected == "ПФ" && selectedcourse == "1 курс") { getUrl("PF (1)"); }
            if (selected == "ПФ" && selectedcourse == "2 курс") { getUrl("PF (2)"); }
            if (selected == "ПФ" && selectedcourse == "3 курс") { getUrl("PF (3)"); }
            if (selected == "ПФ" && selectedcourse == "4 курс") { getUrl("PF (4)"); }
            // ФМИИТ
            if (selected == "ФМИИТ" && selectedcourse == "1 курс") { getUrl("FMIT (1)"); }
            if (selected == "ФМИИТ" && selectedcourse == "2 курс") { getUrl("FMIT (2)"); }
            if (selected == "ФМИИТ" && selectedcourse == "3 курс") { getUrl("FMIT (3)"); }
            if (selected == "ФМИИТ" && selectedcourse == "4 курс") { getUrl("FMIT (4)"); }
            // ФСПИП
            if (selected == "ФСПИП" && selectedcourse == "1 курс") { getUrl("FSPIP (1)"); }
            if (selected == "ФСПИП" && selectedcourse == "2 курс") { getUrl("FSPIP (2)"); }
            if (selected == "ФСПИП" && selectedcourse == "3 курс") { getUrl("FSPIP (3)"); }
            if (selected == "ФСПИП" && selectedcourse == "4 курс") { getUrl("FSPIP (4)"); }
            // ФФКИС
            if (selected == "ФФКИС" && selectedcourse == "1 курс") { getUrl("FKIS (1)"); }
            if (selected == "ФФКИС" && selectedcourse == "2 курс") { getUrl("FKIS (2)"); }
            if (selected == "ФФКИС" && selectedcourse == "3 курс") { getUrl("FKIS (3)"); }
            if (selected == "ФФКИС" && selectedcourse == "4 курс") { getUrl("FKIS (4)"); }
            // ФГИЯК
            if (selected == "ФГИЯК" && selectedcourse == "1 курс") { getUrl("FGIYAK (1)"); }
            if (selected == "ФГИЯК" && selectedcourse == "2 курс") { getUrl("FGIYAK (2)"); }
            if (selected == "ФГИЯК" && selectedcourse == "3 курс") { getUrl("FGIYAK (3)"); }
            if (selected == "ФГИЯК" && selectedcourse == "4 курс") { getUrl("FGIYAK (4)"); }
            if (selected == "ФГИЯК" && selectedcourse == "5 курс") { getUrl("FGIYAK (5)"); }
            // ХГФ
            if (selected == "ХГФ" && selectedcourse == "1 курс") { getUrl("HGF (1)"); }
            if (selected == "ХГФ" && selectedcourse == "2 курс") { getUrl("HGF (2)"); }
            if (selected == "ХГФ" && selectedcourse == "3 курс") { getUrl("HGF (3)"); }
            if (selected == "ХГФ" && selectedcourse == "4 курс") { getUrl("HGF (4)"); }
            if (selected == "ХГФ" && selectedcourse == "5 курс") { getUrl("HGF (5)"); }
            // ЮФ
            if (selected == "ЮФ" && selectedcourse == "1 курс") { getUrl("YF (1)"); }
            if (selected == "ЮФ" && selectedcourse == "2 курс") { getUrl("YF (2)"); }
            if (selected == "ЮФ" && selectedcourse == "3 курс") { getUrl("YF (3)"); }
            if (selected == "ЮФ" && selectedcourse == "4 курс") { getUrl("YF (4)"); }
            // ФОИГ
            if (selected == "ФОИГ" && selectedcourse == "1 курс") { getUrl("FOIG (1)"); }
            if (selected == "ФОИГ" && selectedcourse == "2 курс") { getUrl("FOIG (2)"); }
            if (selected == "ФОИГ" && selectedcourse == "3 курс") { getUrl("FOIG (3)"); }
            if (selected == "ФОИГ" && selectedcourse == "4 курс") { getUrl("FOIG (4)"); }
//                     */
                }
                else {
                        Toast.makeText(context,"Проверьте подключение к интернету и повторите попытку",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}