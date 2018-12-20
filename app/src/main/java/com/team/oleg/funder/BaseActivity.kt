package com.team.oleg.funder

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

open class BaseActivity : AppCompatActivity() {

    protected fun showToast(context: Context, text: String){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    protected fun openFragment(viewId:Int,fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(viewId, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    protected fun removeFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.remove(fragment).commit()
    }

}