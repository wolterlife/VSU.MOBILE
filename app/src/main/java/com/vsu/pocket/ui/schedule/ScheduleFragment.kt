package com.vsu.pocket.ui.schedule

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.vsu.pocket.R
import com.vsu.pocket.ui.schedule_system.ScheduleSystemViewModel
import com.vsu.pocket.ui.settings.SettingsFragment
import kotlinx.android.synthetic.main.fragment_schedule.*


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

        // SETTINGS

        val prefs : SharedPreferences?= activity?.getPreferences(Context.MODE_PRIVATE);
        var selected = prefs?.getString("department","- Не выбран -")
        var selectedcourse = prefs?.getString("department_course","- Не выбран -")// стринги принятые
        var Smerc = prefs?.getBoolean("data_mercanie",false)
        prefs?.edit()?.putBoolean("s_map" , false)?.apply();
        setHasOptionsMenu(false);
        // SETTINGS /

        // settings ### if selected устранение мерцания
        if (Smerc == true) webschedule.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        else webschedule.setLayerType(View.LAYER_TYPE_NONE,null);
        var napomchange = prefs?.getBoolean("data_napom",true)
        // settings ###


        /////////////////////////////////////////// Пременные с ссылками // СДЕЛАТЬ ОБНОВЛЕНИЕ
        var linkFXBIGN1 =   "https://vk.com/doc96103802_589790960?hash=b9da3aba588c7c7281&dl=ff9e9cb31f7ccfeed7"
        var linkFXBIGN2 =   "https://vk.com/doc96103802_589790959?hash=48294487927abdb355&dl=90731d21eeb68ca6de"
        var linkFXBIGN3 =   "https://vk.com/doc96103802_589790958?hash=edf64f492731b2f2b8&dl=2f55621e29948f8b28"
        var linkFXBIGN4 =   "https://vk.com/doc96103802_589790957?hash=664e8c5f6be695d75e&dl=480a2b00467e131738"
        var linkPF =        "https://vk.com/doc96103802_589790956?hash=30307db9d340a6f0f1&dl=aaac3d81200bbabfdd"

        var linkFMIIT =     "https://vk.com/doc96103802_589790955?hash=afdb7fdbb519002ab7&dl=81fdb382461bfd9dd9"

        var linkFSPIP =     "https://vk.com/doc96103802_589790954?hash=78e803f355bda24f32&dl=6429c01f8f0c494f7a"
        var linkFKIS =      "https://vk.com/doc96103802_589790953?hash=72e54cb9f0c6d14246&dl=3c0e3b2b540b19c56d"

        var linkFGIYAK1 =   ""
        var linkFGIYAK2 =   ""
        var linkFGIYAK3 =   ""
        var linkFGIYAK4 =   ""
        var linkFGIYAK5 =   ""
        var linkFGIYAK6 =   ""
        var linkFGIYAK7 =   ""


        var linkART =       "https://vk.com/doc96103802_589790952?hash=83574c1ab67be6d23e&dl=1a3d36fa47d7079af5"
        var linkART5 =      "https://vk.com/doc96103802_589790952?hash=83574c1ab67be6d23e&dl=1a3d36fa47d7079af5"

        var linkLAW1 =      "https://vk.com/doc96103802_589791010?hash=2304ec3d48f7c8f52a&dl=5e744b77c934db763d"
        var linkLAW2 =      "https://vk.com/doc96103802_589791009?hash=aedd41de185ce45a06&dl=0d51795bb79d0fffa7"
        var linkLAW3 =      "https://vk.com/doc96103802_589791007?hash=6c5cd485673791dfa4&dl=49e80a168ed5711301"
        var linkLAW4 =      "https://vk.com/doc96103802_589791007?hash=6c5cd485673791dfa4&dl=49e80a168ed5711301"

        var linkFOIG =      "https://vk.com/doc96103802_589791011?hash=7a84a3bf4da1d72641&dl=ef4d7a6e57625cc009"
        /////////////////////////////////////////////

        sp_option.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View, selectedItemPosition: Int, selectedId: Long
            ) {}

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        if ((selected != "- Не выбран -") && (selectedcourse != "- Не выбран -")){
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
            webschedule.visibility = View.VISIBLE

            webschedule.getSettings().setJavaScriptEnabled(true);
            webschedule.setInitialScale(100)
            if (napomchange == true) Toast.makeText(context, "Факультет и курс можно изменить в настройках приложения", Toast.LENGTH_SHORT).show()

            // ФХБИГН
            if (selected == "ФХБИГН" && selectedcourse == "1 курс") {webschedule.loadUrl(linkFXBIGN1);}
            if (selected == "ФХБИГН" && selectedcourse == "2 курс") {webschedule.loadUrl(linkFXBIGN2);}
            if (selected == "ФХБИГН" && selectedcourse == "3 курс") {webschedule.loadUrl(linkFXBIGN3);}
            if (selected == "ФХБИГН" && selectedcourse == "4 курс") {webschedule.loadUrl(linkFXBIGN4);}
            // ПФ
            if (selected == "ПФ") {webschedule.loadUrl(linkPF);}
            // ФМИИТ
            if (selected == "ФМИИТ") {webschedule.loadUrl(linkFMIIT);}
            // ФСПИП
            if (selected == "ФСПИП") {webschedule.loadUrl(linkFSPIP);}
            // ФФКИС
            if (selected == "ФФКИС") {webschedule.loadUrl(linkFKIS);}
            // ФГИЯК
            if (selected == "ФГИЯК" && selectedcourse == "1 курс") {webschedule.loadUrl(linkFGIYAK1);}
            if (selected == "ФГИЯК" && selectedcourse == "2 курс") {webschedule.loadUrl(linkFGIYAK2);}
            if (selected == "ФГИЯК" && selectedcourse == "3 курс") {webschedule.loadUrl(linkFGIYAK3);}
            if (selected == "ФГИЯК" && selectedcourse == "4 курс") {webschedule.loadUrl(linkFGIYAK4);}
            if (selected == "ФГИЯК" && selectedcourse == "1 курс") {webschedule.loadUrl(linkFGIYAK5);}
            if (selected == "ФГИЯК" && selectedcourse == "2 курс") {webschedule.loadUrl(linkFGIYAK6);}
            if (selected == "ФГИЯК" && selectedcourse == "3 курс") {webschedule.loadUrl(linkFGIYAK7);}
            // ХГФ
            if (selected == "ХГФ" && selectedcourse != "5 курс") {webschedule.loadUrl(linkART);}
            if (selected == "ХГФ" && selectedcourse == "5 курс") {webschedule.loadUrl(linkART5);}
            // ЮФ
            if (selected == "ЮФ" && selectedcourse == "1 курс") {webschedule.loadUrl(linkLAW1);}
            if (selected == "ЮФ" && selectedcourse == "2 курс") {webschedule.loadUrl(linkLAW2);}
            if (selected == "ЮФ" && selectedcourse == "3 курс") {webschedule.loadUrl(linkLAW3);}
            if (selected == "ЮФ" && selectedcourse == "4 курс") {webschedule.loadUrl(linkLAW4);}
            // ФОИГ
            if (selected == "ФОИГ") {webschedule.loadUrl(linkFOIG)}
        }

        buttonSave.setOnClickListener {

            var selected: String = sp_option.getSelectedItem().toString()
            var selectedcourse: String = sp_option2.getSelectedItem().toString()
            if (selected == "- Не выбран -") {
                Toast.makeText(context, "Вы не выбрали факультет!", Toast.LENGTH_LONG).show()
            }
            if (selectedcourse == "- Не выбран -") {
                Toast.makeText(context, "Вы не выбрали курс!", Toast.LENGTH_LONG).show()
            }
            if (selected == "- Не выбран -") {
                Toast.makeText(context, "Вы не выбрали факультет!", Toast.LENGTH_LONG).show()
            }
            if ((selectedcourse == "- Не выбран -") && (selected == "- Не выбран -")) {
                Toast.makeText(context, "Вы не выбрали курс и факультет!", Toast.LENGTH_LONG).show()
            }

            if ((selectedcourse != "- Не выбран -") && (selected != "- Не выбран -")) {
                prefs?.edit()?.putString("department",selected)?.apply(); // Если не было настроек, но они введены успешно
                prefs?.edit()?.putString("department_course",selectedcourse)?.apply();
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
                webschedule.visibility = View.VISIBLE

                webschedule.getSettings().setJavaScriptEnabled(true);
                webschedule.setInitialScale(100)
                if (napomchange == true) Toast.makeText(context, "Факультет можно изменить в настройках приложения", Toast.LENGTH_SHORT).show()

                // ФХБИГН
                if (selected == "ФХБИГН" && selectedcourse == "1 курс") {webschedule.loadUrl(linkFXBIGN1);}
                if (selected == "ФХБИГН" && selectedcourse == "2 курс") {webschedule.loadUrl(linkFXBIGN2);}
                if (selected == "ФХБИГН" && selectedcourse == "3 курс") {webschedule.loadUrl(linkFXBIGN3);}
                if (selected == "ФХБИГН" && selectedcourse == "4 курс") {webschedule.loadUrl(linkFXBIGN4);}
                // ПФ
                if (selected == "ПФ") {webschedule.loadUrl(linkPF);}
                // ФМИИТ
                if (selected == "ФМИИТ") {webschedule.loadUrl(linkFMIIT);}
                // ФСПИП
                if (selected == "ФСПИП") {webschedule.loadUrl(linkFSPIP);}
                // ФФКИС
                if (selected == "ФФКИС") {webschedule.loadUrl(linkFKIS);}
                // ФГИЯК
                if (selected == "ФГИЯК" && selectedcourse == "1 курс") {webschedule.loadUrl(linkFGIYAK1);}
                if (selected == "ФГИЯК" && selectedcourse == "2 курс") {webschedule.loadUrl(linkFGIYAK2);}
                if (selected == "ФГИЯК" && selectedcourse == "3 курс") {webschedule.loadUrl(linkFGIYAK3);}
                if (selected == "ФГИЯК" && selectedcourse == "4 курс") {webschedule.loadUrl(linkFGIYAK4);}
                if (selected == "ФГИЯК" && selectedcourse == "1 курс") {webschedule.loadUrl(linkFGIYAK5);}
                if (selected == "ФГИЯК" && selectedcourse == "2 курс") {webschedule.loadUrl(linkFGIYAK6);}
                if (selected == "ФГИЯК" && selectedcourse == "3 курс") {webschedule.loadUrl(linkFGIYAK7);}
                // ХГФ
                if (selected == "ХГФ" && selectedcourse != "5 курс") {webschedule.loadUrl(linkART);}
                if (selected == "ХГФ" && selectedcourse == "5 курс") {webschedule.loadUrl(linkART5);}
                // ЮФ
                if (selected == "ЮФ" && selectedcourse == "1 курс") {webschedule.loadUrl(linkLAW1);}
                if (selected == "ЮФ" && selectedcourse == "2 курс") {webschedule.loadUrl(linkLAW2);}
                if (selected == "ЮФ" && selectedcourse == "3 курс") {webschedule.loadUrl(linkLAW3);}
                if (selected == "ЮФ" && selectedcourse == "4 курс") {webschedule.loadUrl(linkLAW4);}
                // ФОИГ
                if (selected == "ФОИГ") {webschedule.loadUrl(linkFOIG)}
            }
        }
    }
}