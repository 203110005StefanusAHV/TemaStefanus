package com.stefanus.example.multiFragmentSample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stefanus.animatedThemeManager.AppTheme
import com.stefanus.animatedThemeManager.ThemeFragment
import com.stefanus.animatedThemeManager.ThemeManager
import com.stefanus.example.databinding.FragmentBinding
import com.stefanus.example.themes.LightTheme
import com.stefanus.example.themes.MyAppTheme
import com.stefanus.example.themes.NightTheme
import com.stefanus.example.themes.PinkTheme

class MyFragment : ThemeFragment() {

    private lateinit var binder: FragmentBinding
    private var number: Int = 0

    companion object {
        @JvmStatic
        fun newInstance(number: Int) = MyFragment().apply {
            arguments = Bundle().apply {
                putInt("NUMBER", number)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("NUMBER")?.let {
            number = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // membuat dan menyatukan views
        binder = FragmentBinding.inflate(inflater)

        // digunakan untuk mengatur fragmentNumber
        binder.fragmentNumber.text = number.toString()

        //digunakan untuk mengatur pemrosesan fragment click
        binder.nextFragmentButton.setOnClickListener {
            (activity as MyFragmentActivity).addNewFragment()
        }

        // digunakan untuk mengatur perubahan pemrosesan theme click untuk tombol
        binder.lightButton.setOnClickListener {
            ThemeManager.instance.changeTheme(LightTheme(), it)
        }
        binder.nightButton.setOnClickListener {
            ThemeManager.instance.changeTheme(NightTheme(), it)
        }
        binder.pinkButton.setOnClickListener {
            ThemeManager.instance.changeTheme(PinkTheme(), it)
        }


        return binder.root
    }

    // untuk menyinkronkan ui dengan tema terpilih
    override fun syncTheme(appTheme: AppTheme) {
        // mengatur perbuahan warna ui dengan new appThem disini

        val myAppTheme = appTheme as MyAppTheme
        context?.let {
            // mengatur warna latar
            binder.root.setBackgroundColor(myAppTheme.activityBackgroundColor(it))

            //mengatur gambar
            binder.image.setImageResource(myAppTheme.activityImageRes(it))

            // mengatur warna icon
            binder.share.setColorFilter(myAppTheme.activityIconColor(it))
            binder.gift.setColorFilter(myAppTheme.activityIconColor(it))

            //mengatur warna huruf
            binder.text.setTextColor(myAppTheme.activityTextColor(it))
            binder.fragmentNumber.setTextColor(myAppTheme.activityTextColor(it))

            //mengarur warna card view
            binder.lightButton.setCardBackgroundColor(appTheme.activityThemeButtonColor(it))
            binder.nightButton.setCardBackgroundColor(appTheme.activityThemeButtonColor(it))
            binder.pinkButton.setCardBackgroundColor(appTheme.activityThemeButtonColor(it))
            binder.nextFragmentButton.setCardBackgroundColor(appTheme.activityThemeButtonColor(it))
        }


        //menyingkronkan StatusBarIconColors
        syncStatusBarIconColors(appTheme)
    }

    private fun syncStatusBarIconColors(theme: MyAppTheme) {
        activity?.let {
            ThemeManager.instance.syncStatusBarIconsColorWithBackground(
                it,
                theme.activityBackgroundColor(it)
            )
        }
        activity?.let {
            ThemeManager.instance.syncNavigationBarButtonsColorWithBackground(
                it,
                theme.activityBackgroundColor(it)
            )
        }
    }
}