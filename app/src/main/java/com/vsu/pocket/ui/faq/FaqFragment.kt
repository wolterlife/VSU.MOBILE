package com.vsu.pocket.ui.faq

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vsu.pocket.R
import kotlinx.android.synthetic.main.fragment_faq.*
import kotlinx.android.synthetic.main.fragment_link.*


class FaqFragment : Fragment() {

    private lateinit var faqViewModel: FaqViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_faq, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs : SharedPreferences?= activity?.getPreferences(Context.MODE_PRIVATE);
        prefs?.edit()?.putBoolean("s_map" , false)?.apply();
        setHasOptionsMenu(false);
        // Кнопки
        var pressed1 = false
        var pressed2 = false
        var pressed3 = false
        var pressed4 = false


        val imgResourceDown: Int = R.drawable.down
        val imgResourceUp: Int = R.drawable.up

        buttonPress1.setOnClickListener {
            if (pressed1 == false) {
                buttonPress1.setCompoundDrawablesWithIntrinsicBounds(imgResourceUp, 0, 0, 0) // Левая иконка
                pressed1 = true  // Переключатель
            } else {
                buttonPress1.setCompoundDrawablesWithIntrinsicBounds(imgResourceDown, 0, 0, 0)
                pressed1 = false
            }
            //
            if (FAQtext3.visibility == View.VISIBLE) {
                FAQtext3.visibility = View.GONE
                buttonPress3.setCompoundDrawablesWithIntrinsicBounds(imgResourceDown, 0, 0 , 0)
                pressed3 = false
            }
            if (FAQtext2.visibility == View.VISIBLE) {
                FAQtext2.visibility = View.GONE
                buttonPress2.setCompoundDrawablesWithIntrinsicBounds(imgResourceDown, 0, 0 , 0)
                pressed2 = false
            }
            if (FAQtext4.visibility == View.VISIBLE) {
                FAQtext4.visibility = View.GONE
                buttonPress4.setCompoundDrawablesWithIntrinsicBounds(imgResourceDown, 0, 0 , 0)
                pressed4 = false
                imageButton.visibility = View.GONE;
            }
            // Выключение остальных надписей выше
            if (FAQtext1.visibility == View.VISIBLE) FAQtext1.visibility = View.GONE;
            else FAQtext1.visibility = View.VISIBLE;
        }
        buttonPress2.setOnClickListener {
            if (pressed2 == false) {
                buttonPress2.setCompoundDrawablesWithIntrinsicBounds(imgResourceUp, 0, 0, 0) // Левая иконка
                pressed2 = true  // Переключатель
            } else {
                buttonPress2.setCompoundDrawablesWithIntrinsicBounds(imgResourceDown, 0, 0, 0)
                pressed2 = false
            }
            //
            if (FAQtext1.visibility == View.VISIBLE) {
                FAQtext1.visibility = View.GONE
                buttonPress1.setCompoundDrawablesWithIntrinsicBounds(imgResourceDown, 0, 0 , 0)
                pressed1 = false
            }
            if (FAQtext3.visibility == View.VISIBLE) {
                FAQtext3.visibility = View.GONE
                buttonPress3.setCompoundDrawablesWithIntrinsicBounds(imgResourceDown, 0, 0 , 0)
                pressed3 = false
            }
            if (FAQtext4.visibility == View.VISIBLE) {
                FAQtext4.visibility = View.GONE
                buttonPress4.setCompoundDrawablesWithIntrinsicBounds(imgResourceDown, 0, 0 , 0)
                pressed4 = false
                imageButton.visibility = View.GONE;
            }
            // Выключение остальных надписей выше
            if (FAQtext2.visibility == View.VISIBLE) FAQtext2.visibility = View.GONE;
            else FAQtext2.visibility = View.VISIBLE;
        }
        buttonPress3.setOnClickListener {
            if (pressed3 == false) {
                buttonPress3.setCompoundDrawablesWithIntrinsicBounds(imgResourceUp, 0, 0, 0) // Левая иконка
                pressed3 = true  // Переключатель
            } else {
                buttonPress3.setCompoundDrawablesWithIntrinsicBounds(imgResourceDown, 0, 0, 0)
                pressed3 = false
            }

            //
            if (FAQtext1.visibility == View.VISIBLE) {
                FAQtext1.visibility = View.GONE
                buttonPress1.setCompoundDrawablesWithIntrinsicBounds(imgResourceDown, 0, 0 , 0)
                pressed1 = false
            }
            if (FAQtext2.visibility == View.VISIBLE) {
                FAQtext2.visibility = View.GONE
                buttonPress2.setCompoundDrawablesWithIntrinsicBounds(imgResourceDown, 0, 0 , 0)
                pressed2 = false
            }
            if (FAQtext4.visibility == View.VISIBLE) {
                FAQtext4.visibility = View.GONE
                buttonPress4.setCompoundDrawablesWithIntrinsicBounds(imgResourceDown, 0, 0 , 0)
                pressed4 = false
                imageButton.visibility = View.GONE;
            }
            // Выключение остальных надписей выше
            if (FAQtext3.visibility == View.VISIBLE) FAQtext3.visibility = View.GONE;
            else FAQtext3.visibility = View.VISIBLE;

        }
        buttonPress4.setOnClickListener {
            if (pressed4 == false) {
                buttonPress4.setCompoundDrawablesWithIntrinsicBounds(imgResourceUp, 0, 0, 0) // Левая иконка
                pressed4 = true  // Переключатель
            } else {
                buttonPress4.setCompoundDrawablesWithIntrinsicBounds(imgResourceDown, 0, 0, 0)
                pressed4 = false
            }
            if (FAQtext1.visibility == View.VISIBLE) {
                FAQtext1.visibility = View.GONE
                buttonPress1.setCompoundDrawablesWithIntrinsicBounds(imgResourceDown, 0, 0 , 0)
                pressed1 = false
                }
            if (FAQtext2.visibility == View.VISIBLE) {
                FAQtext2.visibility = View.GONE
                buttonPress2.setCompoundDrawablesWithIntrinsicBounds(imgResourceDown, 0, 0 , 0)
                pressed2 = false
            }
            if (FAQtext3.visibility == View.VISIBLE) {
                FAQtext3.visibility = View.GONE
                buttonPress3.setCompoundDrawablesWithIntrinsicBounds(imgResourceDown, 0, 0 , 0)
                pressed3 = false
            }
            // Выключение остальных надписей выше
            if (FAQtext4.visibility == View.VISIBLE) FAQtext4.visibility = View.GONE;
            else FAQtext4.visibility = View.VISIBLE;
            if (imageButton.visibility == View.VISIBLE) imageButton.visibility = View.GONE;
            else imageButton.visibility = View.VISIBLE;
        }
        // Кнопка контакты
        imageButton.setOnClickListener{ val contintent = Intent(Intent.ACTION_VIEW, Uri.parse("https://taplink.cc/wolter.life")); startActivity(contintent)}
        //
    }
}