package com.gp1.gbcproject


class DataSource {
    private constructor(
    ){
        arrayoflesson.add(Lesson("Lesson1", 65, R.drawable.number1,"Demo Lesson 1","http://youtube.com", 1))
        arrayoflesson.add(Lesson("Lesson2", 125, R.drawable.number2,"Demo Lesson 2","http://youtube.com", 2))
        arrayoflesson.add(Lesson("Lesson3", 80, R.drawable.number3,"Demo Lesson 3","http://youtube.com", 3))
        arrayoflesson.add(Lesson("Lesson4", 45, R.drawable.number4,"Demo Lesson 4","http://youtube.com", 4))
        arrayoflesson.add(Lesson("Lesson5", 95, R.drawable.number5,"Demo Lesson 5","http://youtube.com", 5))
        arrayoflesson.add(Lesson("Lesson6", 120, R.drawable.number6,"Demo Lesson 6","http://youtube.com", 6))
        arrayoflesson.add(Lesson("Lesson7", 55, R.drawable.number7,"Demo Lesson 7","http://youtube.com", 7))
    }

    companion object{
        @Volatile
        private lateinit var instance:DataSource

        fun getInstance(): DataSource{
            synchronized(this){
                if(!::instance.isInitialized){
                    instance = DataSource()
                }
                return instance
            }
        }
    }
    var G_username:String? = "NA"
    val arrayoflesson:ArrayList<Lesson> = arrayListOf()
    var milestone:Int = 0
    var currentLesson:Lesson? = null
    var currentLessonid:Int = -1
    var finishedlessonlist:MutableList<Int> = mutableListOf()
    var finishedlessonset:MutableSet<Int> = mutableSetOf()
    var notearray:MutableList<String> = mutableListOf()
}