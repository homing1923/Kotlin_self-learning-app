package com.gp1.gbcproject


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.gp1.gbcproject.databinding.LessonRowsTemplateBinding
import com.gp1.gbcproject.databinding.LessonRowsTemplateBinding.inflate

class LessonAdapter(context: Context, LessonArray:ArrayList<Lesson>):ArrayAdapter<Lesson>(context, 0, LessonArray) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val currentitem = getItem(position)

        var binding: LessonRowsTemplateBinding =
        inflate(LayoutInflater.from(context))

        var itemv = binding.root

        if(currentitem != null){
            binding.titleLesson.text = currentitem.title
            binding.lenLesson.text = currentitem.durToStr()
            binding.imgLesson.setImageResource(currentitem.img)
            //TODO if user data array have the lesson id of the lesson, show FIN else NONE
//            binding.finTagLesson.text = "FIN"
        }


        return itemv
    }
}