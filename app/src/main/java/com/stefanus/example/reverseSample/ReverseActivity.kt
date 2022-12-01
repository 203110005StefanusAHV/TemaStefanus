package com.stefanus.example.reverseSample

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import com.stefanus.animatedThemeManager.AppTheme
import com.stefanus.animatedThemeManager.ThemeActivity
import com.stefanus.animatedThemeManager.ThemeManager
import com.stefanus.example.databinding.ActivityReverseBinding
import com.stefanus.example.themes.LightTheme
import com.stefanus.example.themes.MyAppTheme
import com.stefanus.example.themes.NightTheme


class ReverseActivity : ThemeActivity() {

    private lateinit var binder: ActivityReverseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // full screen app
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        // membuat dan mengikat tampilan
        binder = ActivityReverseBinding.inflate(LayoutInflater.from(this))
        setContentView(binder.root)

        // pengaturan untuk tombol perubahan tema
        updateButtonText()
        binder.button.setOnClickListener {
            if (ThemeManager.instance.getCurrentTheme()
                    ?.id() == NightTheme.ThemeId
            ) {
                ThemeManager.instance.reverseChangeTheme(LightTheme(), it)
            } else if (ThemeManager.instance.getCurrentTheme()
                    ?.id() != NightTheme.ThemeId
            ) {
                ThemeManager.instance.changeTheme(NightTheme(), it)
            }
            updateButtonText()
        }
    }

    // untuk menyinkronkan ui dengan tema terpilih
    override fun syncTheme(appTheme: AppTheme) {
        // mengatur perbuahan warna ui dengan new appThem disini
        val myAppTheme = appTheme as MyAppTheme

        // mengatur warna latar
        binder.root.setBackgroundColor(myAppTheme.activityBackgroundColor(this))

        //mengatur gambar
        binder.image.setImageResource(myAppTheme.activityImageRes(this))

        // mengatur warna icon
        binder.share.setColorFilter(myAppTheme.activityIconColor(this))
        binder.gift.setColorFilter(myAppTheme.activityIconColor(this))

        //mengatur warna huruf
        binder.text.setTextColor(myAppTheme.activityTextColor(this))

        //mengarur warna card view
        binder.button.setCardBackgroundColor(appTheme.activityThemeButtonColor(this))

        //menyingkronkan StatusBarIconColors
        syncStatusBarIconColors(appTheme)
    }

    fun updateButtonText() {
        if (ThemeManager.instance.getCurrentTheme()?.id() == NightTheme.ThemeId) {
            binder.buttonTextView.text = "Light"
        } else {
            binder.buttonTextView.text = "Night"
        }
    }

    // to get stat theme
    override fun getStartTheme(): AppTheme {
        return LightTheme()
    }

    private fun syncStatusBarIconColors(theme: MyAppTheme) {
        ThemeManager.instance.syncStatusBarIconsColorWithBackground(
            this,
            theme.activityBackgroundColor(this)
        )
        ThemeManager.instance.syncNavigationBarButtonsColorWithBackground(
            this,
            theme.activityBackgroundColor(this)
        )
    }
}