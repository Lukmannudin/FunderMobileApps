package com.team.oleg.funder.Dummy

import com.team.oleg.funder.Model.Sponsor


object DummyAuction {
    var data = arrayOf(
        Sponsor(
            10111,
            12123,
            "1",
            "Dinas Pendidikan Kota Bandung secara resmi akan meluncurkan Sistem Seleksi",
            "[sponsor requirements]",
            "https://ecs7.tokopedia.net/img/cache/700/product-1/2018/2/18/0/0_046f8c71-d3c9-49c2-babf-c68c42f0dc71_900_813.jpg",
            "Pemerintah Kota Bandung",
            "12/12/2018",
            ""
        ),
        Sponsor(
            10111,
            12123,
            "2",
            "Dinas Pendidikan Kota Bandung secara resmi akan meluncurkan Sistem Seleksi",
            "[sponsor requirements]",
            "http://www.sinarpaginews.com/media/original/170414061108-pemerintah-kota-bandung-punya-cara-baru-untuk-meningkatkan-geliat-program-kamis-inggris.jpg",
            "Pemerintah Kota Bandung",
            "12/12/2018",
            ""
        ),
        Sponsor(
            10111,
            12123,
            "3",
            "Dinas Pendidikan Kota Bandung secara resmi akan meluncurkan Sistem Seleksi",
            "[sponsor requirements]",
            "http://www.sinarpaginews.com/media/original/170414061108-pemerintah-kota-bandung-punya-cara-baru-untuk-meningkatkan-geliat-program-kamis-inggris.jpg",
            "Pemerintah Kota Bandung",
            "12/12/2018",
            ""
        ),
        Sponsor(
            10111,
            12123,
            "4",
            "Dinas Pendidikan Kota Bandung secara resmi akan meluncurkan Sistem Seleksi",
            "[sponsor requirements]",
            "http://www.sinarpaginews.com/media/original/170414061108-pemerintah-kota-bandung-punya-cara-baru-untuk-meningkatkan-geliat-program-kamis-inggris.jpg",
            "Pemerintah Kota Bandung",
            "12/12/2018",
            ""
        ),
        Sponsor(
            10111,
            12123,
            "5",
            "Dinas Pendidikan Kota Bandung secara resmi akan meluncurkan Sistem Seleksi",
            "[sponsor requirements]",
            "http://www.sinarpaginews.com/media/original/170414061108-pemerintah-kota-bandung-punya-cara-baru-untuk-meningkatkan-geliat-program-kamis-inggris.jpg",
            "Pemerintah Kota Bandung",
            "12/12/2018",
            ""
        ),
        Sponsor(
            10111,
            12123,
            "6",
            "Dinas Pendidikan Kota Bandung secara resmi akan meluncurkan Sistem Seleksi",
            "[sponsor requirements]",
            "http://www.sinarpaginews.com/media/original/170414061108-pemerintah-kota-bandung-punya-cara-baru-untuk-meningkatkan-geliat-program-kamis-inggris.jpg",
            "Pemerintah Kota Bandung",
            "12/12/2018",
            ""
        ),
        Sponsor(
            10111,
            12123,
            "7",
            "Dinas Pendidikan Kota Bandung secara resmi akan meluncurkan Sistem Seleksi",
            "[sponsor requirements]",
            "http://www.sinarpaginews.com/media/original/170414061108-pemerintah-kota-bandung-punya-cara-baru-untuk-meningkatkan-geliat-program-kamis-inggris.jpg",
            "Pemerintah Kota Bandung",
            "12/12/2018",
            ""
        ),
        Sponsor(
            10111,
            12123,
            "BANGSAT",
            "8",
            "[sponsor requirements]",
            "http://www.sinarpaginews.com/media/original/170414061108-pemerintah-kota-bandung-punya-cara-baru-untuk-meningkatkan-geliat-program-kamis-inggris.jpg",
            "Pemerintah Kota Bandung",
            "12/12/2018",
            ""
        ),
        Sponsor(
            10111,
            12123,
            "BANGSAT",
            "9",
            "[sponsor requirements]",
            "http://www.sinarpaginews.com/media/original/170414061108-pemerintah-kota-bandung-punya-cara-baru-untuk-meningkatkan-geliat-program-kamis-inggris.jpg",
            "Pemerintah Kota Bandung",
            "12/12/2018",
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