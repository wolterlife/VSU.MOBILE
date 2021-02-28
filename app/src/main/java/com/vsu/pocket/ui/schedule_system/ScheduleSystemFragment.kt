package com.vsu.pocket.ui.schedule_system

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.vsu.pocket.R
import kotlinx.android.synthetic.main.fragment_schedule.*


class ScheduleSystemFragment : Fragment() {

    private lateinit var scheduleSystemViewModel: ScheduleSystemViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        scheduleSystemViewModel =
                ViewModelProviders.of(this).get(ScheduleSystemViewModel::class.java)
        val root = inflater.inflate(R.layout.schedule_system, container, false)
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
     //
    }
}