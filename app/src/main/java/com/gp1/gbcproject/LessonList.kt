package com.gp1.gbcproject

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.Toast
import com.gp1.gbcproject.databinding.ActivityLessonListBinding
import java.util.stream.Collectors

class LessonList : AppCompatActivity() {
    lateinit var lessonListBinding: ActivityLessonListBinding
    lateinit var lessonadapter: LessonAdapter
    lateinit var dataSource: DataSource
    lateinit var sharedPrefs: SharedPreferences

    override fun onBackPressed() {
        if(!dataSource.username.isNullOrBlank()){
            var intent = Intent(this, WelcomeBackActivity::class.java)
            startActivity(intent)
            finish()
        }
        super.onBackPressed()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lessonListBinding = ActivityLessonListBinding.inflate(layoutInflater)
        setContentView(lessonListBinding.root)
        dataSource = DataSource.getInstance()
        sharedPrefs = this.getSharedPreferences("com_gp1_gbcproject_PREF", MODE_PRIVATE)
        setUsername()
    }

    override fun onStart() {
        attachAdapter()
        attachListeners()
        Log.d("D1", "start")
        super.onStart()
    }

    override fun onResume() {
        Log.d("D1", "resume")
        super.onResume()
    }

    private fun attachListeners() {
        lessonListBinding.lvT1.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, pos, id ->
                dataSource.currentLessonId = pos + 1
                if (lessonListBinding.tgForceSq.isChecked) {
                    dataSource.finishedLessonList.clear()
                    for (each in dataSource.finishedLessonSet) {
                        dataSource.finishedLessonList.add(each)
                    }
                    var next: Int = 1
                    dataSource.finishedLessonList.sort()
                    if (dataSource.finishedLessonList.size > 0) {
                        if (dataSource.finishedLessonList.max() == dataSource.finishedLessonList.size) {
                            next = dataSource.finishedLessonList.max() + 1
                        } else {
                            for ((i, each) in dataSource.finishedLessonList.withIndex()) {
                                if (i != each + 1) {
                                    next = i + 1
                                }
                            }
                        }
                    }
                    if (dataSource.currentLessonId <= next || dataSource.finishedLessonSet.contains(
                            dataSource.currentLessonId
                        )
                    ) {
                        naviagtetodetail(pos)
                    } else if (dataSource.currentLessonId != next) {
                        Toast.makeText(this, "Your next course is lesson $next", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        naviagtetodetail(pos)
                    }
                } else {
                    naviagtetodetail(pos)
                }
            }
    }

    private fun naviagtetodetail(pos: Int) {
        dataSource.currentLesson = dataSource.arrayOfLessons[pos]
        var intent = Intent(this, LessonDetails::class.java)
        startActivity(intent)
    }

    private fun attachAdapter() {
        lessonadapter = LessonAdapter(this, dataSource.arrayOfLessons)
        lessonListBinding.lvT1.adapter = lessonadapter
    }

    private fun setUsername() {
        if (dataSource.username !== null && dataSource.username!!.isEmpty() && sharedPrefs.contains(
                "KEY_USER_NAME"
            )
        ) {
            dataSource.username = sharedPrefs.getString("KEY_USER_NAME", "").toString()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

}