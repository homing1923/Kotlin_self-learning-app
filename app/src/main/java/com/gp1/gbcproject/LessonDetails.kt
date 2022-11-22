package com.gp1.gbcproject

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.i
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.gp1.gbcproject.databinding.ActivityLessonDetailsBinding
import java.util.stream.Collectors.toSet

class LessonDetails : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityLessonDetailsBinding
    lateinit var dataSource: DataSource
    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataSource = DataSource.getInstance()
        sharedPref = this.getSharedPreferences("com_gp1_gbcproject_PREF", MODE_PRIVATE)
    }

    override fun onStart() {
        readFromPreferences()
        loadContents()
        attachListeners()
        super.onStart()
    }

    override fun onStop() {
        Log.d("D1", "P2Stop")
        super.onStop()
    }

    override fun onResume() {
        Log.d("D1", "P2Resumne")
        super.onResume()
    }

    override fun onDestroy() {
        Log.d("D1", "P2Destroy")
        dataSource.currentLesson = null
        super.onDestroy()
    }

    private fun attachListeners() {
        binding.btnFinish.setOnClickListener(this)
        binding.btnSavenote.setOnClickListener(this)
        binding.btnWatch.setOnClickListener(this)
    }

    private fun loadContents() {
        if (dataSource.currentLesson != null) {
            binding.lessonDetailTitle.text = dataSource.currentLesson!!.title
            binding.lessonDetailDuration.text = dataSource.currentLesson!!.durToStr()
            binding.lessonDetailDesc.text = dataSource.currentLesson!!.desc
            if (dataSource.noteArray.size == 0) {
                for (each in dataSource.arrayOfLessons) {
                    dataSource.noteArray.add("")
                }
            } else {
                binding.lessonDetailEdt.setText(dataSource.noteArray[dataSource.currentLessonId - 1])
            }
        }
    }

    override fun onClick(p0: View?) {
        if (p0 != null) {
            when (p0.id) {
                binding.btnWatch.id -> watchIntent()
                binding.btnSavenote.id -> saveNote()
                binding.btnFinish.id -> finishLesson()
            }
        }
    }

    private fun finishLesson() {
        Log.d("asd", "$dataSource")
        if (dataSource.finishedLessonSet.contains(dataSource.currentLesson!!.lessonid)) {
            Toast.makeText(this, "Your have already completed this lesson", Toast.LENGTH_SHORT)
                .show()
        } else {
            dataSource.finishedLessonSet.add(dataSource.currentLesson!!.lessonid)
            val finishedLessonSetString: MutableSet<String> =
                dataSource.finishedLessonSet.stream().map { it.toString() }.collect(toSet())
            val userProgressName = "${dataSource.username!!.lowercase()}_lesson_progress"
            with(sharedPref.edit()) {
                putStringSet(userProgressName, finishedLessonSetString)
                apply()
            }
            finish()
        }
    }

    private fun saveNote() {
        with(sharedPref.edit()) {
            val lessonNoteName: String =
                "${dataSource.username?.lowercase()}_lesson${dataSource.currentLessonId}_note"
            dataSource.noteArray[dataSource.currentLessonId] =
                binding.lessonDetailEdt.text.toString()
            putString(lessonNoteName, binding.lessonDetailEdt.text.toString())
            apply()
        }
        Toast.makeText(this, "Note Saved!", Toast.LENGTH_SHORT)
            .show()
        hideKeyboard()
    }

    private fun readFromPreferences() {
        val userNoteNameSet = mutableSetOf<String>()
        if (dataSource.noteArray.size == 0) {
            for (each in dataSource.arrayOfLessons) {
                dataSource.noteArray.add("")
            }
        }
        for ((i, each) in dataSource.arrayOfLessons.withIndex()) {
            val lessonNoteName = "${dataSource.username?.lowercase()}_lesson${each.lessonid}_note"
            userNoteNameSet.add(lessonNoteName)
            if (sharedPref.contains(lessonNoteName)) {
                dataSource.noteArray[i] = sharedPref.getString(lessonNoteName, "").toString()
            } else {
                with(sharedPref.edit()) {
                    putString(lessonNoteName, "")
                    apply()
                }
                dataSource.noteArray[i] = ""
            }
        }
    }

    private fun watchIntent() {
        if(dataSource.currentLesson != null){
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(dataSource.currentLesson!!.link))
            startActivity(intent)
        }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}