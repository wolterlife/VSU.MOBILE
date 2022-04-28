package com.vsu.pocket.ui.schedule

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.AdapterView
import android.widget.Toast
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
import java.text.SimpleDateFormat
import java.util.*


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


        /////////////////////////////////////////// Взятие переменных их firebase
        var linkFXBIGN1 = remoteConfig.getString("BIO_1")
        var linkFXBIGN2 = remoteConfig.getString("BIO_2")
        var linkFXBIGN3 = remoteConfig.getString("BIO_3")
        var linkFXBIGN4 = remoteConfig.getString("BIO_4")

        var linkPF1 = remoteConfig.getString("PF_1")
        var linkPF2 = remoteConfig.getString("PF_2")
        var linkPF3 = remoteConfig.getString("PF_3")
        var linkPF4 = remoteConfig.getString("PF_4")

        var linkFMIIT1 = remoteConfig.getString("FMIT_1")
        var linkFMIIT2 = remoteConfig.getString("FMIT_2")
        var linkFMIIT3 = remoteConfig.getString("FMIT_3")
        var linkFMIIT4 = remoteConfig.getString("FMIT_4")

        var linkFSPIP1 = remoteConfig.getString("FSPIP_1")
        var linkFSPIP2 = remoteConfig.getString("FSPIP_2")
        var linkFSPIP3 = remoteConfig.getString("FSPIP_3")
        var linkFSPIP4 = remoteConfig.getString("FSPIP_4")

        var linkFKIS1 = remoteConfig.getString("FKIS_1")
        var linkFKIS2 = remoteConfig.getString("FKIS_2")
        var linkFKIS3 = remoteConfig.getString("FKIS_3")
        var linkFKIS4 = remoteConfig.getString("FKIS_4")

        var linkFGIYAK1 = remoteConfig.getString("FGIYAK_1")
        var linkFGIYAK2 = remoteConfig.getString("FGIYAK_2")
        var linkFGIYAK3 = remoteConfig.getString("FGIYAK_3")
        var linkFGIYAK4 = remoteConfig.getString("FGIYAK_4")
        var linkFGIYAK5 = remoteConfig.getString("FGIYAK_5")


        var linkHGF1 = remoteConfig.getString("HGF_1")
        var linkHGF2 = remoteConfig.getString("HGF_2")
        var linkHGF3 = remoteConfig.getString("HGF_3")
        var linkHGF4 = remoteConfig.getString("HGF_4")
        var linkHGF5 = remoteConfig.getString("HGF_5")

        var linkYF1 = remoteConfig.getString("YUF_1")
        var linkYF2 = remoteConfig.getString("YUF_2")
        var linkYF3 = remoteConfig.getString("YUF_3")
        var linkYF4 = remoteConfig.getString("YUF_4")

        var linkFOIG1 = remoteConfig.getString("FOIG_1")
        var linkFOIG2 = remoteConfig.getString("FOIG_2")
        var linkFOIG3 = remoteConfig.getString("FOIG_3")
        var linkFOIG4 = remoteConfig.getString("FOIG_4")
        //////////////////////////////////////////////

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
            //if ((napomchange == true) && goodConnection) Toast.makeText(context, "Факультет и курс можно изменить в настройках приложения", Toast.LENGTH_SHORT).show()
            progressBar?.visibility = View.VISIBLE;
            // ФХБИГН

            fun getUrl(url: String) {
                myRef.child(url).get().addOnSuccessListener {
                    webschedule?.loadUrl(it.value as String);
                }.addOnFailureListener {
                    Log.w("firebase", "Error in getUrl()")
                }
            }

            // БИО
            if (selected == "ФХБИГН" && selectedcourse == "1 курс") { getUrl("BIO/1"); }
            if (selected == "ФХБИГН" && selectedcourse == "2 курс") { getUrl("BIO/2"); }
            if (selected == "ФХБИГН" && selectedcourse == "3 курс") { getUrl("BIO/3"); }
            if (selected == "ФХБИГН" && selectedcourse == "4 курс") { getUrl("BIO/4"); }
            // ПФ
            if (selected == "ПФ" && selectedcourse == "1 курс") { getUrl("PF/1"); }
            if (selected == "ПФ" && selectedcourse == "2 курс") { getUrl("PF/2"); }
            if (selected == "ПФ" && selectedcourse == "3 курс") { getUrl("PF/3"); }
            if (selected == "ПФ" && selectedcourse == "4 курс") { getUrl("PF/4"); }
            // ФМИИТ
            if (selected == "ФМИИТ" && selectedcourse == "1 курс") { getUrl("FMIT/1"); }
            if (selected == "ФМИИТ" && selectedcourse == "2 курс") { getUrl("FMIT/2"); }
            if (selected == "ФМИИТ" && selectedcourse == "3 курс") { getUrl("FMIT/3"); }
            if (selected == "ФМИИТ" && selectedcourse == "4 курс") { getUrl("FMIT/4"); }
            // ФСПИП
            if (selected == "ФСПИП" && selectedcourse == "1 курс") { getUrl("FSPIP/1"); }
            if (selected == "ФСПИП" && selectedcourse == "2 курс") { getUrl("FSPIP/2"); }
            if (selected == "ФСПИП" && selectedcourse == "3 курс") { getUrl("FSPIP/3"); }
            if (selected == "ФСПИП" && selectedcourse == "4 курс") { getUrl("FSPIP/4"); }
            // ФФКИС
            if (selected == "ФФКИС" && selectedcourse == "1 курс") { getUrl("FKIS/1"); }
            if (selected == "ФФКИС" && selectedcourse == "2 курс") { getUrl("FKIS/2"); }
            if (selected == "ФФКИС" && selectedcourse == "3 курс") { getUrl("FKIS/3"); }
            if (selected == "ФФКИС" && selectedcourse == "4 курс") { getUrl("FKIS/4"); }
            // ФГИЯК
            if (selected == "ФГИЯК") webschedule.setInitialScale(200) // EXPERIMENTAL LARGE BUTTON
            if (selected == "ФГИЯК" && selectedcourse == "1 курс") { getUrl("FGIYAK/1"); }
            if (selected == "ФГИЯК" && selectedcourse == "2 курс") { getUrl("FGIYAK/2"); }
            if (selected == "ФГИЯК" && selectedcourse == "3 курс") { getUrl("FGIYAK/3"); }
            if (selected == "ФГИЯК" && selectedcourse == "4 курс") { getUrl("FGIYAK/4"); }
            if (selected == "ФГИЯК" && selectedcourse == "5 курс") { getUrl("FGIYAK/5"); }
            // ХГФ
            if (selected == "ХГФ" && selectedcourse == "1 курс") { getUrl("HGF/1"); }
            if (selected == "ХГФ" && selectedcourse == "2 курс") { getUrl("HGF/2"); }
            if (selected == "ХГФ" && selectedcourse == "3 курс") { getUrl("HGF/3"); }
            if (selected == "ХГФ" && selectedcourse == "4 курс") { getUrl("HGF/4"); }
            if (selected == "ХГФ" && selectedcourse == "5 курс") { getUrl("HGF/5"); }
            // ЮФ
            if (selected == "ЮФ" && selectedcourse == "1 курс") { getUrl("YF/1"); }
            if (selected == "ЮФ" && selectedcourse == "2 курс") { getUrl("YF/2"); }
            if (selected == "ЮФ" && selectedcourse == "3 курс") { getUrl("YF/3"); }
            if (selected == "ЮФ" && selectedcourse == "4 курс") { getUrl("YF/4"); }
            // ФОИГ
            if (selected == "ФОИГ" && selectedcourse == "1 курс") { getUrl("FOIG/1"); }
            if (selected == "ФОИГ" && selectedcourse == "2 курс") { getUrl("FOIG/2"); }
            if (selected == "ФОИГ" && selectedcourse == "3 курс") { getUrl("FOIG/3"); }
            if (selected == "ФОИГ" && selectedcourse == "4 курс") { getUrl("FOIG/4"); }
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

//                val refresh = Intent(context, MainActivity::class.java) // Перезагрузка фрагмента

                // ?????
                if (linkFXBIGN1 == "" &&
                    linkFXBIGN2 == "" &&
                    linkFXBIGN3 == "" &&
                    linkFXBIGN4 == "" &&
                    linkPF1 == "" &&
                    linkPF2 == "" &&
                    linkPF3 == "" &&
                    linkPF4 == ""
                ) {
                    Toast.makeText(
                        context,
                        "Настройки заданы. Можете заходить в расписание",
                        Toast.LENGTH_SHORT
                    ).show() // Сделать релоад фрагмента на расписание
//                    startActivity(refresh)
                }

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
                            webschedule.reload();
                            webschedule.visibility = View.VISIBLE;
                        }
                    }
                })

                // ФХБИГН
                // Выводит то что надо, но только при задаче первичных настроек
                if (selected == "ФХБИГН" && selectedcourse == "1 курс") {
                    Log.w("firebase", "YES CONTACT")
                    myRef.child("BIO/1").get().addOnSuccessListener {
                        Log.w("firebase", "${it.value}")
                        Log.w("firebase", "YES")
                    }.addOnFailureListener {
                        println("error");
                    }

//                    webschedule.loadUrl(
//                    linkFXBIGN1
//                );

                }
                if (selected == "ФХБИГН" && selectedcourse == "2 курс") {
                    webschedule.loadUrl(
                        linkFXBIGN2
                    );
                }
                if (selected == "ФХБИГН" && selectedcourse == "3 курс") {
                    webschedule.loadUrl(
                        linkFXBIGN3
                    );
                }
                if (selected == "ФХБИГН" && selectedcourse == "4 курс") {
                    webschedule.loadUrl(
                        linkFXBIGN4
                    );
                }
                // ПФ
                if (selected == "ПФ" && selectedcourse == "1 курс") {
                    webschedule.loadUrl(linkPF1);
                }
                if (selected == "ПФ" && selectedcourse == "2 курс") {
                    webschedule.loadUrl(linkPF2);
                }
                if (selected == "ПФ" && selectedcourse == "3 курс") {
                    webschedule.loadUrl(linkPF3);
                }
                if (selected == "ПФ" && selectedcourse == "4 курс") {
                    webschedule.loadUrl(linkPF4);
                }
                // ФМИИТ
                if (selected == "ФМИИТ" && selectedcourse == "1 курс") {
                    webschedule.loadUrl(
                        linkFMIIT1
                    );
                }
                if (selected == "ФМИИТ" && selectedcourse == "2 курс") {
                    webschedule.loadUrl(
                        linkFMIIT2
                    );
                }
                if (selected == "ФМИИТ" && selectedcourse == "3 курс") {
                    webschedule.loadUrl(
                        linkFMIIT3
                    );
                }
                if (selected == "ФМИИТ" && selectedcourse == "4 курс") {
                    webschedule.loadUrl(
                        linkFMIIT4
                    );
                }
                // ФСПИП
                if (selected == "ФСПИП" && selectedcourse == "1 курс") {
                    webschedule.loadUrl(
                        linkFSPIP1
                    );
                }
                if (selected == "ФСПИП" && selectedcourse == "2 курс") {
                    webschedule.loadUrl(
                        linkFSPIP2
                    );
                }
                if (selected == "ФСПИП" && selectedcourse == "3 курс") {
                    webschedule.loadUrl(
                        linkFSPIP3
                    );
                }
                if (selected == "ФСПИП" && selectedcourse == "4 курс") {
                    webschedule.loadUrl(
                        linkFSPIP4
                    );
                }
                // ФФКИС
                if (selected == "ФФКИС" && selectedcourse == "1 курс") {
                    webschedule.loadUrl(
                        linkFKIS1
                    );
                }
                if (selected == "ФФКИС" && selectedcourse == "2 курс") {
                    webschedule.loadUrl(
                        linkFKIS2
                    );
                }
                if (selected == "ФФКИС" && selectedcourse == "3 курс") {
                    webschedule.loadUrl(
                        linkFKIS3
                    );
                }
                if (selected == "ФФКИС" && selectedcourse == "4 курс") {
                    webschedule.loadUrl(
                        linkFKIS4
                    );
                }

                // ФГИЯК
                if (selected == "ФГИЯК") webschedule.setInitialScale(200) // EXPERIMENTAL LARGE BUTTON
                if (selected == "ФГИЯК" && selectedcourse == "1 курс") {
                    webschedule.loadUrl(
                        linkFGIYAK1
                    );
                }
                if (selected == "ФГИЯК" && selectedcourse == "2 курс") {
                    webschedule.loadUrl(
                        linkFGIYAK2
                    );
                }
                if (selected == "ФГИЯК" && selectedcourse == "3 курс") {
                    webschedule.loadUrl(
                        linkFGIYAK3
                    );
                }
                if (selected == "ФГИЯК" && selectedcourse == "4 курс") {
                    webschedule.loadUrl(
                        linkFGIYAK4
                    );
                }
                if (selected == "ФГИЯК" && selectedcourse == "5 курс") {
                    webschedule.loadUrl(
                        linkFGIYAK5
                    );
                }
                // ХГФ
                if (selected == "ХГФ" && selectedcourse == "1 курс") {
                    webschedule.loadUrl(linkHGF1);
                }
                if (selected == "ХГФ" && selectedcourse == "2 курс") {
                    webschedule.loadUrl(linkHGF2);
                }
                if (selected == "ХГФ" && selectedcourse == "3 курс") {
                    webschedule.loadUrl(linkHGF3);
                }
                if (selected == "ХГФ" && selectedcourse == "4 курс") {
                    webschedule.loadUrl(linkHGF4);
                }
                if (selected == "ХГФ" && selectedcourse == "5 курс") {
                    webschedule.loadUrl(linkHGF5);
                }
                // ЮФ
                if (selected == "ЮФ" && selectedcourse == "1 курс") {
                    webschedule.loadUrl(linkYF1);
                }
                if (selected == "ЮФ" && selectedcourse == "2 курс") {
                    webschedule.loadUrl(linkYF2);
                }
                if (selected == "ЮФ" && selectedcourse == "3 курс") {
                    webschedule.loadUrl(linkYF3);
                }
                if (selected == "ЮФ" && selectedcourse == "4 курс") {
                    webschedule.loadUrl(linkYF4);
                }
                // ФОИГ
                if (selected == "ФОИГ" && selectedcourse == "1 курс") {
                    webschedule.loadUrl(linkFOIG1);
                }
                if (selected == "ФОИГ" && selectedcourse == "2 курс") {
                    webschedule.loadUrl(linkFOIG2);
                }
                if (selected == "ФОИГ" && selectedcourse == "3 курс") {
                    webschedule.loadUrl(linkFOIG3);
                }
                if (selected == "ФОИГ" && selectedcourse == "4 курс") {
                    webschedule.loadUrl(linkFOIG4);
                }
            }
        }
    }
}