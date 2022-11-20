package com.gp1.gbcproject

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.system.Os.remove
import android.util.Log
import android.view.View
import com.gp1.gbcproject.databinding.ActivityWelcomeBackBinding

class WelcomeBackActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityWelcomeBackBinding
    lateinit var sharedPrefs: SharedPreferences
    lateinit var userName: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBackBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.continueBtn.setOnClickListener(this)
        binding.tvReset.setOnClickListener(this)
        sharedPrefs =
            this.getSharedPreferences("com_gp1_gbcproject_USER_NAME", MODE_PRIVATE)
        if (sharedPrefs.contains("KEY_USER_NAME")) {
            userName = sharedPrefs.getString("KEY_USER_NAME", "").toString()
            binding.tvWelcomeBack.setText("Welcome back, $userName")
        }
    }

    override fun onClick(view: View) {
        if (view !== null) {
            when (view.id) {
                R.id.continue_btn -> {
                    val intent = Intent(this, LessonList::class.java)
                    startActivity(intent)
                }
                R.id.tv_reset -> {
                    var removedUser = false
                    with(sharedPrefs.edit()) {
                        if (sharedPrefs.contains("KEY_USER_NAME")) {
                            remove("KEY_USER_NAME")
                            removedUser = true
                            apply()
                        }
                    }
                    if (removedUser) {
                        val intent = Intent(this, EnterYourNameActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}