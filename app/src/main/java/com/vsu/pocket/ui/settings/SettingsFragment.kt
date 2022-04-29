package com.vsu.pocket.ui.settings

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.vsu.pocket.MainActivity
import com.vsu.pocket.R
import kotlinx.android.synthetic.main.fragment_schedule.*
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.sp_option
import kotlinx.android.synthetic.main.fragment_settings.sp_option2
import kotlinx.coroutines.delay
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.File
import java.io.FileInputStream

class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel
    lateinit var option: Spinner
    lateinit var result: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingsViewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs : SharedPreferences?= activity?.getPreferences(Context.MODE_PRIVATE);
        var merc = prefs?.getBoolean("data_merc", true)
        prefs?.edit()?.putBoolean("s_map", false)?.apply();
        setHasOptionsMenu(false);

        var selected = prefs?.getString("department", "- Не выбран -")
        var selectedcourse = prefs?.getString("department_course", "- Не выбран -")// стринги принятые
        var napomchange = prefs?.getBoolean("data_napom", true)
        var privetstvie = prefs?.getBoolean("data_privetstvie", true)
        var mercanie = prefs?.getBoolean("data_mercanie", false)
        var pererivs = prefs?.getBoolean("data_pererivs", false)
        var sattelite = prefs?.getBoolean("data_sattelite", false)

        // Настройки при активации фрагмента
        if (napomchange != null) switch1.setChecked(napomchange)
        if (privetstvie != null) switch2.setChecked(privetstvie)
//        if (mercanie != null) switch3.setChecked(mercanie)
        if (pererivs != null) switch4.setChecked(pererivs)
        if (sattelite != null) switch5.setChecked(sattelite)

        // </>

        // Check selection
        switch1.setOnCheckedChangeListener { buttonView, isChecked ->
        if (switch1.isChecked) {napomchange = true ; prefs?.edit()?.putBoolean("data_napom", true)?.apply(); }
        else {napomchange = false ; prefs?.edit()?.putBoolean("data_napom", false)?.apply(); }}
        switch2.setOnCheckedChangeListener { buttonView, isChecked ->
        if (switch2.isChecked) {privetstvie = true ; prefs?.edit()?.putBoolean(
            "data_privetstvie",
            true
        )?.apply(); }
        else {privetstvie = false ; prefs?.edit()?.putBoolean("data_privetstvie", false)?.apply(); }}
//        switch3.setOnCheckedChangeListener { buttonView, isChecked ->
//        if (switch3.isChecked) {mercanie = true ; prefs?.edit()?.putBoolean("data_mercanie", true)?.apply(); }
//        else {mercanie = false ; prefs?.edit()?.putBoolean("data_mercanie", false)?.apply(); }}
        switch4.setOnCheckedChangeListener { buttonView, isChecked ->
        if (switch4.isChecked) {pererivs = true ; prefs?.edit()?.putBoolean("data_pererivs", true)?.apply(); }
        else {pererivs = false ; prefs?.edit()?.putBoolean("data_pererivs", false)?.apply(); }}
        switch5.setOnCheckedChangeListener { buttonView, isChecked ->
        if (switch5.isChecked) {sattelite = true ; prefs?.edit()?.putBoolean("data_sattelite", true)?.apply(); }
        else {sattelite = false ; prefs?.edit()?.putBoolean("data_sattelite", false)?.apply(); }}
        // </> Check selection



        if (selected == "ФХБИГН") sp_option.setSelection(1)
        if (selected == "ПФ") sp_option.setSelection(2)
        if (selected == "ФМИИТ") sp_option.setSelection(3)
        if (selected == "ФСПИП") sp_option.setSelection(4)
        if (selected == "ФФКИС") sp_option.setSelection(5)
        if (selected == "ФГИЯК") sp_option.setSelection(6)
        if (selected == "ХГФ") sp_option.setSelection(7)
        if (selected == "ЮФ") sp_option.setSelection(8)
        if (selected == "ФОИГ") sp_option.setSelection(9)


        if (selectedcourse == "1 курс") sp_option2.setSelection(1)
        if (selectedcourse == "2 курс") sp_option2.setSelection(2)
        if (selectedcourse == "3 курс") sp_option2.setSelection(3)
        if (selectedcourse == "4 курс") sp_option2.setSelection(4)
        if (selectedcourse == "5 курс") sp_option2.setSelection(5)



        ///////////////////////////////////////////////////////////////
        button.setOnClickListener {
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


            if ((selectedcourse != "- Не выбран -") && (selected != "- Не выбран -")) {
                if (context?.let { isNetworkConnected(it) } == true) {
                    ///////////////////////////////////////////////////////////////
                    prefs?.edit()?.putString("department", selected)?.apply();
                    prefs?.edit()?.putString("department_course", selectedcourse)?.apply();
                    Toast.makeText(context, "Сохранено", Toast.LENGTH_SHORT).show()

                    //  Скачивание нового документа
                    fun getUrl(url: String) {
                        val storage = Firebase.storage
                        val storageRef = storage.reference;
                        val islandRef = storageRef.child("$url.xls")

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
                        islandRef.getFile(localFile).addOnSuccessListener {
                        }.addOnFailureListener {
                            Toast.makeText(context, "DOWNLOAD ERROR! $url", Toast.LENGTH_LONG).show()
                        }
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
                    //

                    // Отправка данных на FireBase
                    val mFirebaseAnalytics: FirebaseAnalytics =
                        FirebaseAnalytics.getInstance(requireContext())
                    val params = Bundle()
                    params.putString(FirebaseAnalytics.Param.ITEM_ID, selected)
                    params.putString(FirebaseAnalytics.Param.ITEM_NAME, selectedcourse)
                    mFirebaseAnalytics.logEvent("Course_Info", params)
                    //
                } else {
                    Toast.makeText(context,"Проверьте подключение к интернету и повторите попытку",Toast.LENGTH_SHORT).show();
                }
                ///////////////////////////////////////////////////////////////
            }
        }
        buttonDeff.setOnClickListener {
            prefs?.edit()?.putString("path_schedule", "null_when_get_prefs")?.apply();
            prefs?.edit()?.putString("department", "- Не выбран -")?.apply();
            prefs?.edit()?.putString("department_course", "- Не выбран -")?.apply();
            prefs?.edit()?.putBoolean("data_napom", true)?.apply();
            prefs?.edit()?.putBoolean("data_privetstvie", true)?.apply();
            prefs?.edit()?.putBoolean("data_mercanie", false)?.apply();
            prefs?.edit()?.putBoolean("data_pererivs", false)?.apply();
            prefs?.edit()?.putBoolean("data_sattelite", false)?.apply();

            prefs?.edit()?.putBoolean("data_select_univer", true)?.apply();
            prefs?.edit()?.putBoolean("data_select_hostels", true)?.apply();
            prefs?.edit()?.putBoolean("data_select_food", true)?.apply();
            prefs?.edit()?.putBoolean("data_select_stadions", true)?.apply();
            prefs?.edit()?.putBoolean("data_select_garden", true)?.apply();
            Toast.makeText(context, "Сброшено успешно! Перезапуск", Toast.LENGTH_SHORT).show()
            // Диалог подтверждения
            val refresh = Intent(context, MainActivity::class.java)

            startActivity(refresh)
            // Обновление активити
        }
    }
}

class MyDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Выбор есть всегда")
                .setMessage("Выбери пищу")
                .setCancelable(true)
                .setPositiveButton("Вкусная пища") { dialog, id ->
                    Toast.makeText(
                        activity, "Вы сделали правильный выбор",
                        Toast.LENGTH_LONG
                    ).show()
                }
                .setNegativeButton("Здоровая пища",
                    DialogInterface.OnClickListener { dialog, id ->
                        Toast.makeText(
                            activity, "Возможно вы правы",
                            Toast.LENGTH_LONG
                        ).show()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

