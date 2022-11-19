package com.gp1.gbcproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.gp1.gbcproject.databinding.ActivityLessonDetailsBinding

class LessonDetails : AppCompatActivity(),View.OnClickListener {

    lateinit var binding: ActivityLessonDetailsBinding
    lateinit var dataSource: DataSource
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataSource = DataSource.getInstance()
    }

    override fun onStart() {
        loadcontents()
        attachlisteners()
        super.onStart()
    }

    private fun attachlisteners() {
        binding.btnFinish.setOnClickListener(this)
        binding.btnSavenote.setOnClickListener(this)
        binding.btnWatch.setOnClickListener(this)
    }

    private fun loadcontents(){
        if(dataSource.currentLesson != null){
            binding.lessonDetailTitle.text = dataSource.currentLesson!!.title
            binding.lessonDetailDuration.text = dataSource.currentLesson!!.durToStr()
            binding.lessonDetailDesc.text = dataSource.currentLesson!!.desc
            if(dataSource.notearray.size ==0){
                for(each in dataSource.arrayoflesson){
                    dataSource.notearray.add("")
                }
            }else{
                binding.lessonDetailEdt.setText(dataSource.notearray[dataSource.currentLessonid])
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
            finish()
        }
    }

    private fun savenote() {
        TODO("Not yet implemented")
    }

    private fun watchintent() {
        var intent = Intent()
            .setAction(Intent.ACTION_SEND)
            .putExtra(Intent.EXTRA_STREAM, dataSource.currentLesson!!.link)
        startActivity(intent)
        TODO("Not yet implemented")
    }
}