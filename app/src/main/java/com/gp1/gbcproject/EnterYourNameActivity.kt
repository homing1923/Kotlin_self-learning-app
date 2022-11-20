package com.gp1.gbcproject

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gp1.gbcproject.R
import com.gp1.gbcproject.databinding.ActivityEnterYourNameBinding

class EnterYourNameActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityEnterYourNameBinding
    lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnterYourNameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.continueBtn.setOnClickListener(this)
        sharedPrefs =
            this.getSharedPreferences("com_gp1_gbcproject_USER_NAME", MODE_PRIVATE)
        checkUserNameExists()
    }

    override fun onClick(view: View) {
        if (view !== null) {
            when (view.id) {
                R.id.continue_btn -> {
                    if (binding.etName.text.toString().isEmpty()) {
                        binding.etName.setError("Please enter your name")
                    } else {
                        writeUserNameOnPreferences()
                        val intent = Intent(this, LessonList::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    private fun writeUserNameOnPreferences() {
        with(sharedPrefs.edit()) {
            putString("KEY_USER_NAME", binding.etName.text.toString())
            apply()
        }
    }

    private fun checkUserNameExists() {
        if (sharedPrefs.contains("KEY_USER_NAME")) {
            val userName = sharedPrefs.getString("KEY_USER_NAME", "")
            if (userName != null && userName.isNotEmpty()) {
                val intent = Intent(this, WelcomeBackActivity::class.java)
                startActivity(intent)
            }
        }
    }
}