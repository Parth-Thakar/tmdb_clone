package com.example.tmdb_android_clone.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.tmdb_android_clone.R

class SplashActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)

    // delaying the process of Intent Launch using Handler.postDelayed() method till 2000ms i.e. 2 sec
    // Will launch the Intent after 2 sec
    Handler().postDelayed({
      val i = Intent(this, MainActivity::class.java)
      startActivity(i)
      finish()
    }, 2000)
  }
}