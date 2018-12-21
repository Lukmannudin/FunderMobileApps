package com.team.oleg.funder

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_search_home.*
import org.jetbrains.anko.intentFor

class SearchHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_search_home)

        closeButtonSearchHome.setOnClickListener {
            startActivity(intentFor<MainActivity>())
        }
    }
}