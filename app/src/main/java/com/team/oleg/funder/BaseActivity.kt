package com.team.oleg.funder

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

open class BaseActivity : AppCompatActivity() {

    protected fun showToast(context: Context, text: String){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    protected fun openFragment(viewId:Int,fragment: androidx.fragment.app.Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(viewId, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    protected fun removeFragment(fragment: androidx.fragment.app.Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.remove(fragment).commit()
    }

}