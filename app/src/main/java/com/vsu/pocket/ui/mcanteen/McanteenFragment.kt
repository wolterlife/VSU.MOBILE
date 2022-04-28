package com.vsu.pocket.ui.mcanteen

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.net.http.SslError
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.vsu.pocket.R
import kotlinx.android.synthetic.main.fragment_link.*
import kotlinx.android.synthetic.main.fragment_mcanteen.*
import kotlinx.android.synthetic.main.fragment_schedule.*


class McanteenFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        val root = inflater.inflate(R.layout.fragment_mcanteen, container, false)
        return root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs : SharedPreferences?= activity?.getPreferences(Context.MODE_PRIVATE);
        prefs?.edit()?.putBoolean("s_map", false)?.apply();
        setHasOptionsMenu(false);
        webid.getSettings().setJavaScriptEnabled(true);
        webid.scrollTo(0, 350); // Проверить оптимизацию на других телефонах (было 700)
        webid.setInitialScale(170)   // Добавить изменение параметра в настройках 150 -> 170;
        // disable scroll on touch
        // disable scroll on touch
        webid.setOnTouchListener(OnTouchListener { v, event -> event.action == MotionEvent.ACTION_MOVE })
        webid.loadUrl("https://vsu.by/platnie-uslygi/menyu-stolovoj.html");

        // settings ### if selected устранение мерцания
        var SMerc = prefs?.getBoolean("data_mercanie", false)
        if (SMerc == true) webid.setLayerType(LAYER_TYPE_SOFTWARE, null)
        else webid.setLayerType(LAYER_TYPE_NONE, null);
        // settings ###

        webid.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                //webid.loadUrl("javascript:document.getElementsByClassName(\"navbar-wrapper\").setAttribute(\"style\",\"display:block;\");")
                //webid.loadUrl("javascript:document.getElementByClassName(\"col-sm-3\").style.display=\"block\";")
                //webid.loadUrl("javascript:document.getElementsByClassName(\"navbar-wrapper\")[0].style.display=\"block\";")
                //webid.loadUrl("javascript:document.getElementsByClassName(\"navbar-right\")[0].style.display=\"block\

                // Соц сверху, поиск, бургер меню
                webid.loadUrl(
                    "javascript:(function() { " +
                            "document.getElementsByClassName('col-sm-3')[0].style.display='none'; })()"
                )

                // ВЕРХНИЙ БЕЛЫЙ ФРАГМЕНТ
                webid.loadUrl(
                    "javascript:(function() { " +
                            "document.getElementsByClassName('navbar-wrapper')[0].style.display='none'; })()"
                ) //

                //
                // Белый фон в основе в центре
                webid.loadUrl(
                    "javascript:(function() { " +
                            "document.getElementsByClassName('row')[0].style.background='white'; })()"
                ) //

                webid.loadUrl(
                    "javascript:(function() { " +
                            "document.getElementsByTagName('h2')[0].style.display='none'; })()"
                ) //

                webid.loadUrl(
                    "javascript:(function() { " +
                            "document.getElementsByTagName('h1')[0].innerHTML = 'ВЫБОР СТОЛОВОЙ'; })()"
                ) //

                //webid.loadUrl("javascript:(function() { " +
                //    "document.getElementsByClassName('navbar-right')[0].style.display='none'; })()")

                webid.loadUrl(
                    "javascript:(function() { " +
                            "document.getElementsByClassName('breadcrumb')[0].style.display='none'; })()"
                )

                webid.loadUrl(
                    "javascript:(function() { " +
                            "document.getElementById('vsu-menu-top-nav').style.display='none';})()"
                );

                webid.loadUrl(
                    "javascript:(function() { " +
                            "document.getElementById('content-blue').style.display='none';})()"
                );

                webid.loadUrl(
                    "javascript:(function() { " +
                            "document.getElementsByClassName('article-info muted')[0].style.display='none'; })()"
                )

                // Бизнес ланчи
                //webid.loadUrl("javascript:(function() { " +
                //        "document.getElementsByClassName('abit-spec-table-div')[0].style.display='none'; })()")

                //webid.loadUrl("javascript:(function() { " +
                //        "document.getElementsByClassName('abit-spec-table-div')[4].style.display='none'; })()")

                //webid.loadUrl("javascript:(function() { " +
                //        "document.getElementsByClassName('abit-spec-table-div')[5].style.display='none'; })()")

                //webid.loadUrl("javascript:(function() { " +
                //        "document.getElementsByClassName('abit-spec-table-div')[6].style.display='none'; })()")
                progressBar3.visibility = View.GONE
                webid.visibility = View.VISIBLE;
                // content-grey id ostavit
                //webid.loadUrl("javascript:document.getElementById(\"vsu-menu-top\").setAttribute(\"style\",\"display:block;\");")
                //webid.loadUrl("javascript:document.getElementByClassName(\"navbar-right\").setAttribute(\"style\",\"display:block;\");")
            }

            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                handler?.proceed();
            }
        }
    }



    override fun onPause() {
        super.onPause()
        webid.stopLoading()
    }
}






