package com.vsu.pocket.ui.mcanteen

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.LAYER_TYPE_NONE
import android.view.View.LAYER_TYPE_SOFTWARE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vsu.pocket.R
import kotlinx.android.synthetic.main.fragment_mcanteen.*


class McanteenFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val root = inflater.inflate(R.layout.fragment_mcanteen, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs : SharedPreferences?= activity?.getPreferences(Context.MODE_PRIVATE);
        prefs?.edit()?.putBoolean("s_map" , false)?.apply();
        setHasOptionsMenu(false);
        webid.getSettings().setJavaScriptEnabled(true);
        webid.scrollTo(0,700); // Проверить оптимизацию на других телефонах
        webid.setInitialScale(170)   // Добавить изменение параметра в настройках 150 -> 170;
        webid.loadUrl("https://vsu.by/platnie-uslygi/menyu-stolovoj.html");
        // settings ### if selected устранение мерцания
        var SMerc = prefs?.getBoolean("data_mercanie",false)
        if (SMerc == true) webid.setLayerType(LAYER_TYPE_SOFTWARE, null)
        else webid.setLayerType(LAYER_TYPE_NONE,null);
        // settings ###
    }

    override fun onPause() {
        super.onPause()
        webid.stopLoading()
    }
}






