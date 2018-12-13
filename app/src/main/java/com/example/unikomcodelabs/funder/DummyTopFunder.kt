package com.example.unikomcodelabs.funder

import android.util.Log


object DummyTopFunder {
    var data = arrayOf(
        arrayOf(
            "https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwiGmKKuhJ3fAhUVfysKHZMZByUQjRx6BAgBEAU&url=https%3A%2F%2Fwww.learningcrux.com%2Fcourse%2Ffinding-a-sponsor&psig=AOvVaw15r-NTAbH2LksRY8xa7aQX&ust=1544797954884813",
            "Sponsor 1",
            "Lynda Finding a Sponsor"
        ),
        arrayOf(
            "https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwiLlr7Vh53fAhWIWX0KHYOyAh8QjRx6BAgBEAU&url=https%3A%2F%2Fwww.sponsor.fi%2Fen%2F&psig=AOvVaw15r-NTAbH2LksRY8xa7aQX&ust=1544797954884813",
            "Sponsor 2",
            "Sponsor Capital"
        )
    )

    fun getListData(): MutableList<TopFunder> {
        var topFunder: TopFunder? = null
        val list = mutableListOf<TopFunder>()

        for (i in 0 until data.size) {
            Log.i("cek", data[1][i])
//            topFunder?.sponsorImage = data[0][i]
//            topFunder?.sponsorTitle = data[0][i]
//            topFunder?.let { list.add(it) }
        }
        return list
    }
}