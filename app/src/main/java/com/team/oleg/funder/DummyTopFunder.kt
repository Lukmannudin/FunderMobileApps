package com.team.oleg.funder


object DummyTopFunder {
    var data = arrayOf(
        TopFunder(
            "https://ecs7.tokopedia.net/img/cache/700/product-1/2018/2/18/0/0_046f8c71-d3c9-49c2-babf-c68c42f0dc71_900_813.jpg",
            "Sponsor 1",
            "Lynda Finding a Sponsor"
        ),
        TopFunder(
            "https://ecs7.tokopedia.net/img/cache/700/product-1/2018/2/18/0/0_046f8c71-d3c9-49c2-babf-c68c42f0dc71_900_813.jpg",
            "Sponsor 2",
            "Sponsor Capital"
        ),
        TopFunder(
            "https://ecs7.tokopedia.net/img/cache/700/product-1/2018/2/18/0/0_046f8c71-d3c9-49c2-babf-c68c42f0dc71_900_813.jpg",
            "Sponsor 2",
            "Sponsor Capital"
        ),
        TopFunder(
            "https://ecs7.tokopedia.net/img/cache/700/product-1/2018/2/18/0/0_046f8c71-d3c9-49c2-babf-c68c42f0dc71_900_813.jpg",
            "Sponsor 2",
            "Sponsor Capital"
        ),
        TopFunder(
            "https://ecs7.tokopedia.net/img/cache/700/product-1/2018/2/18/0/0_046f8c71-d3c9-49c2-babf-c68c42f0dc71_900_813.jpg",
            "Sponsor 1",
            "Lynda Finding a Sponsor"
        ),
        TopFunder(
            "https://ecs7.tokopedia.net/img/cache/700/product-1/2018/2/18/0/0_046f8c71-d3c9-49c2-babf-c68c42f0dc71_900_813.jpg",
            "Sponsor 1",
            "Lynda Finding a Sponsor"
        ),
        TopFunder(
            "https://ecs7.tokopedia.net/img/cache/700/product-1/2018/2/18/0/0_046f8c71-d3c9-49c2-babf-c68c42f0dc71_900_813.jpg",
            "Sponsor 121",
            "Lynda Finding a Sponsor"
        )
    )
    fun getListData(): MutableList<TopFunder> {
        val list = mutableListOf<TopFunder>()
        for (i in 0 until data.size) {
            list.add(data[i])
        }
        return list
    }
}