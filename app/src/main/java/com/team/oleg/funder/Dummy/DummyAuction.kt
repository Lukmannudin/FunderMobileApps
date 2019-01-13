package com.team.oleg.funder.Dummy

import com.team.oleg.funder.Data.Sponsor


object DummyAuction {
    var data = arrayOf(

        Sponsor(
            "10111",
            "12123",
            "1",
            "Dinas Pendidikan Kota Bandung secara resmi akan meluncurkan Sistem Seleksi",
            "https://ecs7.tokopedia.net/img/cache/700/product-1/2018/2/18/0/0_046f8c71-d3c9-49c2-babf-c68c42f0dc71_900_813.jpg",
            "2017/12/12",
            "Pemerintah Kota Bandung",
            "12/12/2018",
            "1",
            "coca@gmail.com",
            "",
            "Coca Cola",
            "",
            "",
            ""
        ),
        Sponsor(
            "10111",
            "12123",
            "1",
            "Dinas Pendidikan Kota Bandung secara resmi akan meluncurkan Sistem Seleksi",
            "https://ecs7.tokopedia.net/img/cache/700/product-1/2018/2/18/0/0_046f8c71-d3c9-49c2-babf-c68c42f0dc71_900_813.jpg",
            "2017/12/12",
            "Pemerintah Kota Bandung",
            "12/12/2018",
            "1",
            "coca@gmail.com",
            "",
            "Coca Cola",
            "",
            "",
            ""
        ),
        Sponsor(
            "10111",
            "12123",
            "1",
            "Dinas Pendidikan Kota Bandung secara resmi akan meluncurkan Sistem Seleksi",
            "https://ecs7.tokopedia.net/img/cache/700/product-1/2018/2/18/0/0_046f8c71-d3c9-49c2-babf-c68c42f0dc71_900_813.jpg",
            "2017/12/12",
            "Pemerintah Kota Bandung",
            "12/12/2018",
            "1",
            "coca@gmail.com",
            "",
            "Coca Cola",
            "",
            "",
            ""
        ),
        Sponsor(
            "10111",
            "12123",
            "1",
            "Dinas Pendidikan Kota Bandung secara resmi akan meluncurkan Sistem Seleksi",
            "https://ecs7.tokopedia.net/img/cache/700/product-1/2018/2/18/0/0_046f8c71-d3c9-49c2-babf-c68c42f0dc71_900_813.jpg",
            "2017/12/12",
            "Pemerintah Kota Bandung",
            "12/12/2018",
            "1",
            "coca@gmail.com",
            "",
            "Coca Cola",
            "",
            "",
            ""
        ),
        Sponsor(
            "10111",
            "12123",
            "1",
            "Dinas Pendidikan Kota Bandung secara resmi akan meluncurkan Sistem Seleksi",
            "https://ecs7.tokopedia.net/img/cache/700/product-1/2018/2/18/0/0_046f8c71-d3c9-49c2-babf-c68c42f0dc71_900_813.jpg",
            "2017/12/12",
            "Pemerintah Kota Bandung",
            "12/12/2018",
            "1",
            "coca@gmail.com",
            "",
            "Coca Cola",
            "",
            "",
            ""
        ),
        Sponsor(
            "10111",
            "12123",
            "1",
            "Dinas Pendidikan Kota Bandung secara resmi akan meluncurkan Sistem Seleksi",
            "https://ecs7.tokopedia.net/img/cache/700/product-1/2018/2/18/0/0_046f8c71-d3c9-49c2-babf-c68c42f0dc71_900_813.jpg",
            "2017/12/12",
            "Pemerintah Kota Bandung",
            "12/12/2018",
            "1",
            "coca@gmail.com",
            "",
            "Coca Cola",
            "",
            "",
            ""
        ),
        Sponsor(
            "10111",
            "12123",
            "1",
            "Dinas Pendidikan Kota Bandung secara resmi akan meluncurkan Sistem Seleksi",
            "https://ecs7.tokopedia.net/img/cache/700/product-1/2018/2/18/0/0_046f8c71-d3c9-49c2-babf-c68c42f0dc71_900_813.jpg",
            "2017/12/12",
            "Pemerintah Kota Bandung",
            "12/12/2018",
            "1",
            "coca@gmail.com",
            "",
            "Coca Cola",
            "",
            "",
            ""
        ),
        Sponsor(
            "10111",
            "12123",
            "1",
            "Dinas Pendidikan Kota Bandung secara resmi akan meluncurkan Sistem Seleksi",
            "https://ecs7.tokopedia.net/img/cache/700/product-1/2018/2/18/0/0_046f8c71-d3c9-49c2-babf-c68c42f0dc71_900_813.jpg",
            "2017/12/12",
            "Pemerintah Kota Bandung",
            "12/12/2018",
            "1",
            "coca@gmail.com",
            "",
            "Coca Cola",
            "",
            "",
            ""
        )
    )

    fun getListData(): MutableList<Sponsor> {
        val list = mutableListOf<Sponsor>()
        for (i in 0 until data.size) {
            list.add(data[i])
        }
        return list
    }
}