package com.vsu.pocket.ui.link

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.vsu.pocket.R
import kotlinx.android.synthetic.main.fragment_link.*




class LinkFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val root = inflater.inflate(R.layout.fragment_link, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vsubutton.setOnClickListener{val vsuintent = Intent(Intent.ACTION_VIEW, Uri.parse("https://vsu.by")); startActivity(vsuintent)}
        sdobutton.setOnClickListener{val sdointent = Intent(Intent.ACTION_VIEW, Uri.parse("https://sdo.vsu.by/")); startActivity(sdointent)}
        youtubebutton.setOnClickListener{val youtubeintent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCo18_krqqaEWSb6_cbHnupQ")); startActivity(youtubeintent)}
        instagrambutton.setOnClickListener{val instagramintent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/tvu.vsu/")); startActivity(instagramintent)}
        vkbutton.setOnClickListener{val vkintent = Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/vsu_vitebsk")); startActivity(vkintent)}
        facebookbutton.setOnClickListener{val facebookintent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/vsu.by/")); startActivity(facebookintent)}
        twitterbutton.setOnClickListener{val twitterintent = Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/VSU_Vitebsk")); startActivity(twitterintent)}
        telegrambutton.setOnClickListener{val telegramintent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/vsumasherov/")); startActivity(telegramintent)}
    }
}






