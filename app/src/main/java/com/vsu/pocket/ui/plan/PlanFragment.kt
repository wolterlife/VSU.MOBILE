package com.vsu.pocket.ui.plan

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.vsu.pocket.R

class PlanFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_plan, container, false)
        val prefs : SharedPreferences?= activity?.getPreferences(Context.MODE_PRIVATE);
        prefs?.edit()?.putBoolean("s_map" , false)?.apply();
        setHasOptionsMenu(false);
        return root
    }
}