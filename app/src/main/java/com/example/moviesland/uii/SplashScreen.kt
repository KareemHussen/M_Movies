package com.example.moviesland.uii

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.moviesland.MainActivity
import com.example.moviesland.R
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

//        supportActionBar!!.hide();

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val anim = AnimationUtils.loadAnimation(this, R.anim.name_anim)

        m_Movies.startAnimation(anim)
        Handler().postDelayed({
            val intent = Intent(this , MainActivity::class.java)
            startActivity(intent)
            finish()
        },3000)




    }
}