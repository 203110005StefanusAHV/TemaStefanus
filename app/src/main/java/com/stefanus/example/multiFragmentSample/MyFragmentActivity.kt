package com.stefanus.example.multiFragmentSample

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import com.stefanus.animatedThemeManager.AppTheme
import com.stefanus.animatedThemeManager.ThemeActivity
import com.stefanus.example.R
import com.stefanus.example.databinding.ActivityFragmentBinding
import com.stefanus.example.themes.LightTheme


class MyFragmentActivity : ThemeActivity() {

    private var fragmentNumber: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // untuk layar pada aplikasi
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        // membuat dan mengikat tampilan
        val binder = ActivityFragmentBinding.inflate(LayoutInflater.from(this))
        setContentView(binder.root)

        // membuat fragment pertama
        addNewFragment()
    }


    fun addNewFragment() {
        fragmentNumber++
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragmentContainer, MyFragment.newInstance(fragmentNumber))
        if (fragmentNumber != 1) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    override fun syncTheme(appTheme: AppTheme) {
        // there is nothing in activity to sync
        // inner fragments do this
    }

    // to get stat theme
    override fun getStartTheme(): AppTheme {
        return LightTheme()
    }
}