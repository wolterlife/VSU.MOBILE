package com.vsu.pocket.ui.schedule

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
import kotlinx.android.synthetic.main.fragment_schedule.*
import kotlinx.android.synthetic.main.nav_header_main.*


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

        sp_option.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View, selectedItemPosition: Int, selectedId: Long
            ) {}

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        buttonSave.setOnClickListener {
            val selected: String = sp_option.getSelectedItem().toString()
            if (selected == "- Не выбран -") {
                Toast.makeText(context, "Вы не выбрали факультет!", Toast.LENGTH_LONG).show()
            }
            else {

                Toast.makeText(context, "Типо сохранил данные и переключился экран", Toast.LENGTH_LONG).show() // delete
                // скрывание всего
                imageView2.visibility = View.GONE
                textView9.visibility = View.GONE
                sp_option.visibility = View.GONE
                divider2.visibility = View.GONE
                divider3.visibility = View.GONE
                buttonSave.visibility = View.GONE
                // dell in future


                //Toast.makeText(context, "Факультет можно изменить в настройках приложения", Toast.LENGTH_LONG).show()
                // save process
                // Переход на другое активити

            }
        }
    }
}