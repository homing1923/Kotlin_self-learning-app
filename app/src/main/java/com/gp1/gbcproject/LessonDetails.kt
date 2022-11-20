package com.gp1.gbcproject

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.gp1.gbcproject.databinding.ActivityLessonDetailsBinding
import java.util.stream.Collector
import java.util.stream.Collectors
import java.util.stream.Collectors.toSet

class LessonDetails : AppCompatActivity(),View.OnClickListener,View.OnFocusChangeListener {

    lateinit var binding: ActivityLessonDetailsBinding
    lateinit var dataSource: DataSource
    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataSource = DataSource.getInstance()
        sharedPref = this.getSharedPreferences("com_gp1_gbcproject_PREF",MODE_PRIVATE)
    }

    override fun onStart() {
        readFromPreferences()
        loadContents()
        attachListeners()
        super.onStart()
    }

    private fun attachListeners() {
        binding.btnFinish.setOnClickListener(this)
        binding.btnSavenote.setOnClickListener(this)
        binding.btnWatch.setOnClickListener(this)
        binding.lessonDetailEdt.onFocusChangeListener = this
    }

    private fun loadContents(){
        if(dataSource.currentLesson != null){
            binding.lessonDetailTitle.text = dataSource.currentLesson!!.title
            binding.lessonDetailDuration.text = dataSource.currentLesson!!.durToStr()
            binding.lessonDetailDesc.text = dataSource.currentLesson!!.desc
            if(dataSource.notearray.size ==0){
                for(each in dataSource.arrayoflesson){
                    dataSource.notearray.add("")
                }
            }else{
                binding.lessonDetailEdt.setText(dataSource.notearray[dataSource.currentLessonid - 1])
            }
        }
    }

    override fun onClick(p0: View?) {
        if(p0 != null) {
            when(p0.id){
                binding.btnWatch.id -> watchintent()
                binding.btnSavenote.id -> savenote()
                binding.btnFinish.id -> finishlesson()
            }
        }
    }

    private fun finishlesson() {
        if(dataSource.finishedlessonset.contains(dataSource.currentLesson!!.lessonid)){
            Toast.makeText(this, "Your have already completed this lesson", Toast.LENGTH_SHORT)
                .show()
        }else{
            dataSource.finishedlessonset.add(dataSource.currentLesson!!.lessonid)
            val finisehedLessonSetString:MutableSet<String> = dataSource.finishedlessonset.stream().map{it.toString()}.collect(toSet())
            val userProgressName = "${dataSource.username!!.lowercase()}_lesson_progress"
            Log.d("D1", "$userProgressName, ${userProgressName::class.java}")
            with(sharedPref.edit()){
                putStringSet("${dataSource.username!!.lowercase()}_lesson_progress", finisehedLessonSetString)
                apply()
            }
            finish()
        }
    }

    private fun savenote() {
        with(sharedPref.edit()){
            var lessonNoteName:String = "${dataSource.username?.lowercase()}_lesson${dataSource.currentLessonid}_note"
            dataSource.notearray[dataSource.currentLessonid] = binding.lessonDetailEdt.text.toString()
            putString(lessonNoteName, binding.lessonDetailEdt.text.toString())
            apply()
        }
        Toast.makeText(this, "Note Saved!", Toast.LENGTH_SHORT)
            .show()
        hideKeyboard()

    }

    private fun readFromPreferences(){
        val userNoteNameSet = mutableSetOf<String>()
        if(dataSource.notearray.size == 0){
            for(each in dataSource.arrayoflesson){
                dataSource.notearray.add("")
            }
        }
        for((i, each) in dataSource.arrayoflesson.withIndex()){
            val lessonNoteName = "${dataSource.username?.lowercase()}_lesson${each.lessonid}_note"
            userNoteNameSet.add(lessonNoteName)
            if(sharedPref.contains(lessonNoteName)){
                dataSource.notearray[i] = sharedPref.getString(lessonNoteName, "").toString()
            }else{
                with(sharedPref.edit()){
                    putString(lessonNoteName, "")
                    apply()
                }
                dataSource.notearray[i] = ""
            }
        }
    }

    private fun watchintent() {
        var intent = Intent()
            .setAction(Intent.ACTION_SEND)
            .putExtra(Intent.EXTRA_STREAM, dataSource.currentLesson!!.link)
        startActivity(intent)
        TODO("Not yet implemented")
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }
    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onFocusChange(p0: View?, p1: Boolean) {
        if(p0 != null){
            when(p0.id){
                binding.lessonDetailEdt.id -> hideKeyboard()
            }
        }
    }
}