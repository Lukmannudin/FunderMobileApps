package com.team.oleg.funder.company.DealForm

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.team.oleg.funder.company.CompanyActivity
import com.team.oleg.funder.Data.Bank
import com.team.oleg.funder.Data.Event
import com.team.oleg.funder.R
import com.team.oleg.funder.Utils.Utils
import kotlinx.android.synthetic.main.activity_deal_form.*
import kotlinx.android.synthetic.main.custom_dialog.view.*
import org.jetbrains.anko.intentFor
import java.text.NumberFormat
import java.util.*


class DealFormActivity : AppCompatActivity(), DealFormContract.View {


    override lateinit var presenter: DealFormContract.Presenter

    var tMoney = 0
    var tGoods = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deal_form)

        val bidderId = intent.getStringExtra(Utils.BIDDER_ID)
        val eoId = intent.getStringExtra(Utils.ID)
        presenter = DealFormPresenter(this)
        presenter.getDataBank(eoId)

        backDealForm.setOnClickListener {
            onBackPressed()
        }

        initView()
        tUang.setOnCheckedChangeListener { _, isChecked ->
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
            val transfer: HashMap<String, String> = hashMapOf()
            transfer["total_transfer"] = edtFunding.text.toString()
            transfer["transfer"] = tMoney.toString()
            transfer["goods"] = tGoods.toString()
            transfer["desc"] = descGoods.text.toString()
            presenter.sendTransfer(bidderId, transfer)
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
    }

    override fun showEvent(event: Event) {
    }

    override fun showDialogMessage(message: String) {
        alertDialog(message)
    }

    override fun onPause() {
        super.onPause()
        presenter.destroy()
    }

    private fun alertDialog(message:String) {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        val view = layoutInflater.inflate(R.layout.custom_dialog, null)
        view.titleFillFormSubmitted.text = message
        view.btnSubmitFillForm.setOnClickListener {
            startActivity(intentFor<CompanyActivity>())
        }
        builder.setView(view)
        builder.show()
    }

    override fun setView(data: Bank) {
        numberReq.setText(data.accountRek)
        nameEventOrganizer.setText(data.accountName)
        bankName.setText(data.bankName)
    }

}
