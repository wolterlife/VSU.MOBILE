package com.vsu.pocket.ui.faq

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vsu.pocket.R
import kotlinx.android.synthetic.main.fragment_faq.*

class FaqFragment : Fragment() {

    private lateinit var faqViewModel: FaqViewModel

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_faq, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonPress1.setOnClickListener {
            if (FAQtext1.visibility == View.VISIBLE) FAQtext1.visibility = View.GONE;
            else FAQtext1.visibility = View.VISIBLE;
        }
        buttonPress2.setOnClickListener {
            if (FAQtext2.visibility == View.VISIBLE) FAQtext2.visibility = View.GONE;
            else FAQtext2.visibility = View.VISIBLE;
        }
        buttonPress3.setOnClickListener {
            if (FAQtext3.visibility == View.VISIBLE) FAQtext3.visibility = View.GONE;
            else FAQtext3.visibility = View.VISIBLE;
        }
    }
}