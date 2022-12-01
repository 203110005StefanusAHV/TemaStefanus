package com.stefanus.example

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.stefanus.example.databinding.ActivityStartBinding
import com.stefanus.example.multiFragmentSample.MyFragmentActivity
import com.stefanus.example.reverseSample.ReverseActivity
import com.stefanus.example.singleActivitySample.SingleActivity


class StartActivity : AppCompatActivity() {
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
        val binder = ActivityStartBinding.inflate(LayoutInflater.from(this))
        setContentView(binder.root)

        // mengatur tombol untuk singleActivitySampleButton
        binder.singleActivitySampleButton.setOnClickListener {
            val myIntent = Intent(this, SingleActivity::class.java)
            this.startActivity(myIntent)
        }

        // mengatur tombol untukfragmentSampleButton
        binder.fragmentSampleButton.setOnClickListener {
            val myIntent = Intent(this, MyFragmentActivity::class.java)
            this.startActivity(myIntent)
        }

        // mengatur tombol untuk reverseAnimation
        binder.reverseAnimation.setOnClickListener {
            val myIntent = Intent(this, ReverseActivity::class.java)
            this.startActivity(myIntent)
        }

        // mengatur tombol untuk for github
        binder.github.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://github.com/203110005StefanusAHV/TemaStefanus")
                )
            )
        }
    }
}