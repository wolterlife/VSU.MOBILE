package com.vsu.pocket.ui.calls

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.SystemClock.sleep
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.vsu.pocket.R
import kotlinx.android.synthetic.main.fragment_calls.*
import java.lang.Thread.sleep

class CallsFragment : Fragment() {

    private lateinit var callsViewModel: CallsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val root = inflater.inflate(R.layout.fragment_calls, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val prefs : SharedPreferences?= activity?.getPreferences(Context.MODE_PRIVATE);
        prefs?.edit()?.putBoolean("s_map" , false)?.apply();
        var statusimage = prefs?.getBoolean("data_pererivs",false)
        setHasOptionsMenu(false);

        if (statusimage == true) {imageView3.setImageResource(R.drawable.raspis2); statusimage = true}//kartinka other page
        else { imageView3.setImageResource(R.drawable.raspis); statusimage = false;}

        imageView3.setOnClickListener {
        // Переключатель картинки по нажатию
        if (statusimage == false) {imageView3.setImageResource(R.drawable.raspis2); statusimage = true; prefs?.edit()?.putBoolean("data_pererivs", true)?.apply();}//kartinka other page
        else { imageView3.setImageResource(R.drawable.raspis); statusimage = false; prefs?.edit()?.putBoolean("data_pererivs", false)?.apply();}

    }
    }
}


