package com.team.oleg.funder.company.ReviewEO

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import com.team.oleg.funder.R
import com.team.oleg.funder.company.CompanyActivity
import kotlinx.android.synthetic.main.activity_review_eo.*
import org.jetbrains.anko.intentFor

class ReviewEO : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_eo)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp)

        descHapepned.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                countDescription.text = s?.length.toString() + " / 120"
                s?.let {
                    if (s.length > 120) {
                        countDescription.setTextColor(Color.parseColor("#e74c3c"))
                    } else {
                        countDescription.setTextColor(Color.parseColor("#000000"))
                    }
                }
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                startActivity(intentFor<CompanyActivity>())
            }
        }
        return true
    }
}
