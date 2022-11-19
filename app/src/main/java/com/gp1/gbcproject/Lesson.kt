package com.gp1.gbcproject

class Lesson(val title:String, val duration:Int, val img:Int, val desc:String, val link:String, val lessonid:Int) {
    fun durToStr():String{
        val min:Int = duration%60
        return if(duration > 60){
            val hr:Int = duration/60
            "Length: ${hr}hour${min}min"
        }else{
            "Length: ${min}min"
        }
    }
}