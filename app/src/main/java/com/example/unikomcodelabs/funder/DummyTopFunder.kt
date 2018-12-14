package com.example.unikomcodelabs.funder

import android.util.Log


object DummyTopFunder {
    var data = arrayOf(
        TopFunder(
            "https://4g92mivec.files.wordpress.com/2014/02/simpati.png",
            "Sponsor 1",
            "Lynda Finding a Sponsor"
        ),
        TopFunder(
            "https://www.telkomsel.com/sites/default/files/2017-06/group_main_description_simPati_desktop_720x405.png",
            "Sponsor 2",
            "Sponsor Capital"
        )
    )
    private lateinit var topFunder: TopFunder
    fun getListData(): MutableList<TopFunder> {
        val list = mutableListOf<TopFunder>()
        for (i in 0 until data.size) {
            list.add(data[i])
        }
        return list
    }
}