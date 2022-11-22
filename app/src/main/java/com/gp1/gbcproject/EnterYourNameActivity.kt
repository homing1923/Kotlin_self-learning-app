package com.gp1.gbcproject

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.gp1.gbcproject.R
import com.gp1.gbcproject.databinding.ActivityEnterYourNameBinding
import java.util.stream.Collectors

class EnterYourNameActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityEnterYourNameBinding
    lateinit var sharedPrefs: SharedPreferences
    lateinit var dataSource: DataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnterYourNameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.continueBtn.setOnClickListener(this)
        sharedPrefs =
            this.getSharedPreferences("com_gp1_gbcproject_PREF", MODE_PRIVATE)
        dataSource = DataSource.getInstance()
        checkUserNameExists()
    }

    override fun onClick(view: View) {
        if (view !== null) {
            when (view.id) {
                R.id.continue_btn -> {
                    if (binding.etName.text.toString().isBlank()) {
                        binding.etName.setError("Please enter your name")
                    } else if (binding.etName.text.toString().isNotBlank() && binding.etName.text.toString().length > 50) {
                        binding.etName.setError("Please enter a maximum of 50 characters")
                    } else {
                        writeUserNameOnPreferences()
                        val intent = Intent(this, LessonList::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        finish()
        System.exit(0)
    }

    private fun writeUserNameOnPreferences() {
        with(sharedPrefs.edit()) {
            putString("KEY_USER_NAME", binding.etName.text.toString())
            dataSource.username = binding.etName.text.toString()
            apply()
        }
    }

    private fun checkUserNameExists() {
        if (sharedPrefs.contains("KEY_USER_NAME")) {
            val userName = sharedPrefs.getString("KEY_USER_NAME", "")
            if (userName != null && userName.isNotEmpty()) {
                val intent = Intent(this, WelcomeBackActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}