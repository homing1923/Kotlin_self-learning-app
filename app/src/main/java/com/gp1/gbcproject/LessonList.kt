package com.gp1.gbcproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gp1.gbcproject.databinding.ActivityLessonListBinding
import com.gp1.gbcproject.LessonAdapter

class LessonList : AppCompatActivity(), View.OnClickListener {
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
        super.onStart()
        lessonadapter = LessonAdapter(this, dataSource.arrayoflesson)
        lessonListBinding.lvT1.adapter = lessonadapter
    }

    override fun onClick(p0: View?) {
        if (p0!=null){
            when(p0.id){

            }
        }
    }
}