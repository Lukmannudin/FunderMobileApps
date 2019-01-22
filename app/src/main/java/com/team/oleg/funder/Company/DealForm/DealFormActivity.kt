package com.team.oleg.funder.Company.DealForm

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.team.oleg.funder.Data.Event
import com.team.oleg.funder.R
import kotlinx.android.synthetic.main.activity_deal_form.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.HashMap


class DealFormActivity : AppCompatActivity(),DealFormContract.View{

    override lateinit var presenter: DealFormContract.Presenter

    var tMoney = 0
    var tGoods = 0
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
                tMoney = 1
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
                tGoods = 1
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

        btnSubmitDealForm.setOnClickListener {
            val transfer :HashMap<String,String> = hashMapOf()
            transfer["total_transfer"] = edtFunding.text.toString()
            transfer["transfer"] = tMoney.toString()
            transfer["goods"] = tGoods.toString()
            transfer["desc"] = descGoods.text.toString()
//            presenter.sendTransfer(transfer)
        }
    }

    private fun initView() {
        numberReq.visibility = View.GONE
        nameEventOrganizer.visibility = View.GONE
        bankName.visibility = View.GONE
        edtFunding.visibility = View.GONE
        descGoods.visibility = View.GONE
    }

    override fun setLoadingIndicator(active: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showEvent(event: Event) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showDialogMessage(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
