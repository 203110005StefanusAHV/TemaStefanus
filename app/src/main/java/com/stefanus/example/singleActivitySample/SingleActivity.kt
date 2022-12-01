package com.stefanus.example.singleActivitySample

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import com.stefanus.animatedThemeManager.AppTheme
import com.stefanus.animatedThemeManager.ThemeActivity
import com.stefanus.animatedThemeManager.ThemeManager
import com.stefanus.example.databinding.ActivitySingleBinding
import com.stefanus.example.themes.LightTheme
import com.stefanus.example.themes.MyAppTheme
import com.stefanus.example.themes.NightTheme
import com.stefanus.example.themes.PinkTheme


class SingleActivity : ThemeActivity() {

    private lateinit var binder: ActivitySingleBinding
    private var number: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //untuk nomor
        number = intent.getIntExtra("number", 1)

        // full screen app
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        // mengatur dan mengikat tampilan
        binder = ActivitySingleBinding.inflate(LayoutInflater.from(this))
        setContentView(binder.root)

        // mengatur aktivitas tulisan nomor
        binder.ActivityNumber.text = number.toString()

        // mengatur perubahan tema dengan tombol
        binder.lightButton.setOnClickListener {
            ThemeManager.instance.changeTheme(LightTheme(), it)
        }
        binder.nightButton.setOnClickListener {
            ThemeManager.instance.changeTheme(NightTheme(), it)
        }
        binder.pinkButton.setOnClickListener {
            ThemeManager.instance.changeTheme(PinkTheme(), it)
        }
        binder.nextActivityBtn.setOnClickListener {
            val myIntent = Intent(this, SingleActivity::class.java)
            myIntent.putExtra("number", number + 1)
            this.startActivity(myIntent)
        }
    }

    // to sync ui with selected theme
    override fun syncTheme(appTheme: AppTheme) {
        // change ui colors with new appThem here

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
        binder.lightButton.setCardBackgroundColor(appTheme.activityThemeButtonColor(this))
        binder.nightButton.setCardBackgroundColor(appTheme.activityThemeButtonColor(this))
        binder.pinkButton.setCardBackgroundColor(appTheme.activityThemeButtonColor(this))
        binder.nextActivityBtn.setCardBackgroundColor(appTheme.activityThemeButtonColor(this))

        //menyingkronkan StatusBarIconColors
        syncStatusBarIconColors(appTheme)
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