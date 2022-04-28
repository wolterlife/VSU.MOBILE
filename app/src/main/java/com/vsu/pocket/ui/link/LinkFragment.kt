package com.vsu.pocket.ui.link

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.net.http.SslError
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.LAYER_TYPE_NONE
import android.view.View.LAYER_TYPE_SOFTWARE
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.vsu.pocket.R
import kotlinx.android.synthetic.main.fragment_link.*
import kotlinx.android.synthetic.main.fragment_link.webSdo
import kotlinx.android.synthetic.main.fragment_mcanteen.*
import kotlinx.android.synthetic.main.fragment_schedule.*


class LinkFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        val root = inflater.inflate(R.layout.fragment_link, container, false)
        return root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs : SharedPreferences?= activity?.getPreferences(Context.MODE_PRIVATE);
        prefs?.edit()?.putBoolean("s_map", false)?.apply();
        setHasOptionsMenu(false);
        webSdo.settings.javaScriptEnabled = true;
        webSdo.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                progressBar2?.visibility = View.GONE;
            }

            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                handler?.proceed();
            }
        }
        webSdo.setOnKeyListener { v, keyCode, event -> // Блок настройки кнопки назад
            if (keyCode == KeyEvent.KEYCODE_BACK && webSdo.canGoBack()) {
                webSdo.goBack() // Navigate back to previous web page if there is one
                webSdo.scrollTo(0, 0) // Scroll webview back to top of previous page
            }
            true
        }
        //webSdo.scrollTo(0, 700); // Проверить оптимизацию на других телефонах
        //webSdo.setInitialScale(170)   // Добавить изменение параметра в настройках 150 -> 170;
        webSdo.loadUrl("https://newsdo.vsu.by");
        // settings ### if selected устранение мерцания
        var SMerc = prefs?.getBoolean("data_mercanie", false)
        if (SMerc == true) webSdo.setLayerType(LAYER_TYPE_SOFTWARE, null)
        else webSdo.setLayerType(LAYER_TYPE_NONE, null);
        // settings ###

    }

    override fun onPause() {
        super.onPause()
        webSdo.stopLoading()
    }

    /*  * * * * * * * * * * * * * * * *         OLD LINK FRAGMENT         * * * * * * * * * * * * * * *
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs : SharedPreferences?= activity?.getPreferences(Context.MODE_PRIVATE);
        prefs?.edit()?.putBoolean("s_map" , false)?.apply();
        setHasOptionsMenu(false);

        vsubutton.setOnClickListener{ val vsuintent = Intent(Intent.ACTION_VIEW, Uri.parse("https://vsu.by")); startActivity(vsuintent)}
        sdobutton.setOnClickListener{val sdointent = Intent(Intent.ACTION_VIEW, Uri.parse("https://sdo.vsu.by/")); startActivity(sdointent)}
        youtubebutton.setOnClickListener{val youtubeintent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCo18_krqqaEWSb6_cbHnupQ")); startActivity(youtubeintent)}
        instagrambutton.setOnClickListener{val instagramintent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/tvu.vsu/")); startActivity(instagramintent)}
        vkbutton.setOnClickListener{val vkintent = Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/vsu_vitebsk")); startActivity(vkintent)}
        facebookbutton.setOnClickListener{val facebookintent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/vsu.by/")); startActivity(facebookintent)}
        twitterbutton.setOnClickListener{val twitterintent = Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/VSU_Vitebsk")); startActivity(twitterintent)}
        telegrambutton.setOnClickListener{val telegramintent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/vsumasherov/")); startActivity(telegramintent)}
    } */
}






