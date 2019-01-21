package com.team.oleg.funder.Company.DealForm

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.team.oleg.funder.R
import kotlinx.android.synthetic.main.activity_deal_form.*
import java.text.NumberFormat
import java.util.*


class DealFormActivity : AppCompatActivity(){
    var tMoney = false
    var tGoods = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deal_form)
        backDealForm.setOnClickListener {
            onBackPressed()
        }
        initView()
        tUang.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                numberReq.visibility = View.VISIBLE
                nameEventOrganizer.visibility = View.VISIBLE
                bankName.visibility = View.VISIBLE
                edtFunding.visibility = View.VISIBLE
                tMoney = true
            } else {
                numberReq.visibility = View.GONE
                nameEventOrganizer.visibility = View.GONE
                bankName.visibility = View.GONE
                edtFunding.visibility = View.GONE
            }
        }

        kBarang.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                descGoods.visibility = View.VISIBLE
                tGoods = true
            } else {
                descGoods.visibility = View.GONE
            }
        }


        val localeID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)

        edtFunding.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

//                var moneyTO = formatRupiah.format(s.toString().toString())
//                var editable = SpannableStringBuilder(formatRupiah.format(moneyTO).toString())
//                edtFunding.text = editable
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }

    private fun initView() {
        numberReq.visibility = View.GONE
        nameEventOrganizer.visibility = View.GONE
        bankName.visibility = View.GONE
        edtFunding.visibility = View.GONE
        descGoods.visibility = View.GONE

    }
}
