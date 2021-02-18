package com.vsu.pocket.ui.mcanteen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
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
        webid.getSettings().setJavaScriptEnabled(true);
        webid.scrollTo(0,1000); // Проверить оптимизацию на других телефонах
        webid.loadUrl("https://vsu.by/platnie-uslygi/perechen-platnykh-uslug/menyu-stolovoj.html");

    }
}






