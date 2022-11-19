package com.gp1.gbcproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.Toast
import com.gp1.gbcproject.databinding.ActivityLessonListBinding

class LessonList : AppCompatActivity() {
    lateinit var lessonListBinding: ActivityLessonListBinding
    lateinit var lessonadapter: LessonAdapter
    lateinit var dataSource: DataSource
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lessonListBinding = ActivityLessonListBinding.inflate(layoutInflater)
        setContentView(lessonListBinding.root)
        dataSource = DataSource.getInstance()


    }

    override fun onStart() {
        attachadapter()
        attachlisteners()
        super.onStart()
    }

    private fun attachlisteners() {
        lessonListBinding.lvT1.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, pos, id ->
            dataSource.currentLessonid = pos+1
            if(lessonListBinding.tgForceSq.isChecked){
                dataSource.finishedlessonlist.clear()
                for(each in dataSource.finishedlessonset){
                    dataSource.finishedlessonlist.add(each)
                }
                var next:Int = 1
                dataSource.finishedlessonlist.sort()
                if(dataSource.finishedlessonlist.size > 0){
                    if(dataSource.finishedlessonlist.max() == dataSource.finishedlessonlist.size){
                        next = dataSource.finishedlessonlist.max() + 1
                    }else{
                        for((i, each) in dataSource.finishedlessonlist.withIndex()){
                            if(i != each + 1){
                                next = i + 1
                            }
                        }
                    }
                }
                if (dataSource.currentLessonid <= next  || dataSource.finishedlessonset.contains(dataSource.currentLessonid)) {
                    naviagtetodetail(pos)
                } else if(dataSource.currentLessonid != next) {
                    Toast.makeText(this, "Your next course is lesson $next", Toast.LENGTH_SHORT)
                        .show()
                }else{
                    naviagtetodetail(pos)
                }
            }else{
                naviagtetodetail(pos)
            }
        }
    }

    private fun naviagtetodetail(pos:Int){
        dataSource.currentLesson = dataSource.arrayoflesson[pos]
        var intent = Intent(this, LessonDetails::class.java)
        startActivity(intent)
    }

    private fun attachadapter(){
        lessonadapter = LessonAdapter(this, dataSource.arrayoflesson)
        lessonListBinding.lvT1.adapter = lessonadapter
    }
}