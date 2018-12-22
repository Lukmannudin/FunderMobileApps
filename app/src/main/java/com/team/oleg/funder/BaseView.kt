package com.team.oleg.funder

import com.team.oleg.funder.Home.HomeContract

interface BaseView<T>{
    var presenter: HomeContract.Presenter
}