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

   // private lateinit var linkViewModel: LinkViewModel // DELL NZ ZACH

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val vsuintent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
        val sdointent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
        val youtubeintent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
        val instagramintent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
        val vkintent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
        val facebookintent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
        val twitterintent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
        val telegramintent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));

       // linkViewModel = ViewModelProviders.of(this).get(LinkViewModel::class.java) // DELL NZ ZACH
        val root = inflater.inflate(R.layout.fragment_link, container, false)

        vsubutton.setOnClickListener {
            startActivity(vsuintent);
        }
        youtubebutton.setOnClickListener {
            startActivity(youtubeintent);
        }
        sdobutton.setOnClickListener {
            startActivity(sdointent);
        }
        instagrambutton.setOnClickListener {
            startActivity(instagramintent);
        }
        vkbutton.setOnClickListener {
            startActivity(vkintent);
        }
        facebookbutton.setOnClickListener {
            startActivity(facebookintent);
        }
        twitterbutton.setOnClickListener {
            startActivity(twitterintent);
        }
        telegrambutton.setOnClickListener {
            startActivity(telegramintent);
        }

        return root
    }
}



/*fun openNewTabWindow(urls: String, context: Context, view: View) {
          val uris = Uri.parse(urls)
          val intents = Intent(Intent.ACTION_VIEW, uris)
          val b = Bundle()
          b.putBoolean("new_window", true)
          intents.putExtras(b)
          context.startActivity(intents)
          }*/

