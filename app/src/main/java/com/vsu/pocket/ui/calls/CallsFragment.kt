package com.vsu.pocket.ui.calls

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.vsu.pocket.R
import kotlinx.android.synthetic.main.fragment_calls.*

class CallsFragment : Fragment() {

    private lateinit var callsViewModel: CallsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val root = inflater.inflate(R.layout.fragment_calls, container, false)
        return root
    }
}


