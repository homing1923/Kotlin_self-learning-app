package com.gp1.gbcproject

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.gp1.gbcproject.databinding.ActivityWelcomeBackBinding
import java.util.stream.Collectors

class WelcomeBackActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityWelcomeBackBinding
    lateinit var sharedPrefs: SharedPreferences
    lateinit var userName: String
    lateinit var dataSource: DataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBackBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataSource = DataSource.getInstance()
        binding.continueBtn.setOnClickListener(this)
        binding.resetBtn.setOnClickListener(this)
        sharedPrefs =
            this.getSharedPreferences("com_gp1_gbcproject_PREF", MODE_PRIVATE)
        if (sharedPrefs.contains("KEY_USER_NAME")) {
            userName = sharedPrefs.getString("KEY_USER_NAME", "").toString()
            dataSource.username = userName
            restoreUserProgress()
            binding.tvWelcomeBack.setText("Welcome back, $userName")
            Log.d("asd", "Luego poraca ${dataSource.arrayOfLessons.size}")
            Log.d("asd", "Luego poraca ${dataSource.finishedLessonSet.size}")
            val progress = (dataSource.finishedLessonSet.size.toDouble() / dataSource.arrayOfLessons.size) * 100
            Log.d("asd", "Luego poraca ${progress}")
            binding.tvProgress.setText("You've completed ${String.format("%.2f", progress)}% of the course!")
            binding.tvCompleted.setText("Lessons Completed: ${(dataSource.finishedLessonSet.size)}")
            val remainingLessons = dataSource.arrayOfLessons.size - dataSource.finishedLessonSet.size
            binding.tvRemaining.setText("Lessons remaining: $remainingLessons")
        }
    }

    override fun onClick(view: View) {
        if (view !== null) {
            when (view.id) {
                R.id.continue_btn -> {
                    val intent = Intent(this, LessonList::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.reset_btn -> {
                    var removedUser = false
                    with(sharedPrefs.edit()) {
                        if (sharedPrefs.contains("KEY_USER_NAME")) {
                            val username = dataSource.username!!.lowercase()
                            remove("KEY_USER_NAME")
                            removedUser = true
                            remove("${username}_lesson_progress")

                            for(each in dataSource.arrayOfLessons){
                                remove("${username}_lesson${each.lessonid}_note")
                            }
                            dataSource.finishedLessonSet.removeAll(dataSource.finishedLessonSet)
                            apply()
                        }
                    }
                    if (removedUser) {
                        val intent = Intent(this, EnterYourNameActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }

    private fun restoreUserProgress() {
        Log.d("asd", "${dataSource.username}")
        if (dataSource.username !== null) {
            val userProgressName = "${dataSource.username!!.lowercase()}_lesson_progress"
            if (sharedPrefs.contains(userProgressName)) {
                dataSource.finishedLessonSet =
                    sharedPrefs.getStringSet(userProgressName, mutableSetOf())!!
                        .stream().map { it -> it.toInt() }.collect(Collectors.toSet())
                Log.d("D1", "${sharedPrefs.getStringSet(userProgressName, mutableSetOf())}")
                Log.d("D1", "${dataSource.finishedLessonSet}")
            } else {
                //New user
                Log.d("D1", "newUser")
                with(sharedPrefs.edit()) {
                    putStringSet(
                        userProgressName,
                        dataSource.finishedLessonSet.stream().map { it -> it.toString() }
                            .collect(Collectors.toSet())
                    )
                    apply()
                }
            }
        }
    }
}