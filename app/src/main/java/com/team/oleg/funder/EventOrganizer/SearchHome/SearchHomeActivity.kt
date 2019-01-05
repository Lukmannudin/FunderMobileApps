package com.team.oleg.funder.EventOrganizer.SearchHome

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.team.oleg.funder.Main.MainActivity
import com.team.oleg.funder.R
import kotlinx.android.synthetic.main.search_home.*
import org.jetbrains.anko.intentFor

class SearchHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_home)

        closeButtonSearchHome.setOnClickListener {
            startActivity(intentFor<MainActivity>())
        }
    }
}