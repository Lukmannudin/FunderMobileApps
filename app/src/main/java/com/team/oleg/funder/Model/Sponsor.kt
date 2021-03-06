package com.team.oleg.funder.Model

data class Sponsor(
    val sponsorId : Int,
    val companyId: Int,
    val sponsorName: String,
    val sponsorDesc: String,
    val sponsorRequirements: String,
    val sponsorImage: String,
    val sponsorCompany: String,
    val sponsorDate: String,
    val proposal_example: String
)