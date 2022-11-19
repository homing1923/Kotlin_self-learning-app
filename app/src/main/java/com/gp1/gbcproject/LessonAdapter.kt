package com.gp1.gbcproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.gp1.gbcproject.databinding.LessonRowsTemplateBinding
import com.gp1.gbcproject.databinding.LessonRowsTemplateBinding.inflate

class LessonAdapter(context: Context, LessonArray:ArrayList<Lesson>):ArrayAdapter<Lesson>(context, 0, LessonArray) {
    lateinit var dataSource: DataSource
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val currentitem = getItem(position)

        var binding: LessonRowsTemplateBinding =
        inflate(LayoutInflater.from(context))

        var itemv = binding.root

        dataSource = DataSource.getInstance()

        if(currentitem != null){
            binding.titleLesson.text = currentitem.title
            binding.lenLesson.text = currentitem.durToStr()
            binding.imgLesson.setImageResource(currentitem.img)
            if(dataSource.finishedlessonset.contains(currentitem.lessonid)){
                binding.finTagLesson.setImageResource(R.drawable.ic_finished)
            }
        }


        return itemv
    }
}