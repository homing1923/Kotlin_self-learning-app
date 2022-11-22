package com.gp1.gbcproject


class DataSource {
    private constructor(
    ) {
        arrayOfLessons.add(
            Lesson(
                "Getting Started",
                65,
                R.drawable.number1,
                "First things first! What is Angular? Why would you want to learn it? This lecture helps answering this question.",
                "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
                1
            )
        )
        arrayOfLessons.add(
            Lesson(
                "The Basics",
                125,
                R.drawable.number2,
                "We saw our first App run in the browser but do you really know how it got there? Angular is all about Components! This lectures takes a closer look and explains what Components really are.",
                "http://youtube.com",
                2
            )
        )
        arrayOfLessons.add(
            Lesson(
                "Debugging",
                80,
                R.drawable.number3,
                "Things don't always go the way you want them to go. Learn how to read Angular's error messages and how incredibly useful can be to debug your app in the browser - learn more in this lecture.",
                "http://youtube.com",
                3
            )
        )
        arrayOfLessons.add(
            Lesson(
                "Components and Data-binding",
                45,
                R.drawable.number4,
                "You're not limited to binding to built-in properties. Indeed, binding to custom property is a key feature of Angular apps. As well, this lecture explains how you may split an existing app into multiple new components.",
                "http://youtube.com",
                4
            )
        )
        arrayOfLessons.add(
            Lesson(
                "Directives",
                95,
                R.drawable.number5,
                "When using directives, you have an easy way of reacting to events on your hosting element. Learn more about it in this lecture.",
                "http://youtube.com",
                5
            )
        )
        arrayOfLessons.add(
            Lesson(
                "Using Services and Dependency Injection",
                120,
                R.drawable.number6,
                "Services are a core concept of Angular - let me explain what you may expect from this section.",
                "http://youtube.com",
                6
            )
        )
        arrayOfLessons.add(
            Lesson(
                "Changing pages with Routing",
                55,
                R.drawable.number7,
                "Thus far, we didn't really hide that we're building a single page application. Let's give the user the feeling of switching pages - with the Angular router!",
                "http://youtube.com",
                7
            )
        )
    }

    companion object {
        @Volatile
        private lateinit var instance: DataSource

        fun getInstance(): DataSource {
            synchronized(this) {
                if (!::instance.isInitialized) {
                    instance = DataSource()
                }
                return instance
            }
        }
    }

    var username: String? = null
    val arrayOfLessons: ArrayList<Lesson> = arrayListOf()
    var milestone: Int = 0
    var currentLesson: Lesson? = null
    var currentLessonId: Int = -1
    var finishedLessonList: MutableList<Int> = mutableListOf()
    var finishedLessonSet: MutableSet<Int> = mutableSetOf()
    var noteArray: MutableList<String> = mutableListOf()

    override fun toString(): String {
        return "DataSource(username=$username, arrayOfLessons=$arrayOfLessons, milestone=$milestone, currentLesson=$currentLesson, currentLessonId=$currentLessonId, finishedLessonList=$finishedLessonList, finishedLessonSet=$finishedLessonSet, noteArray=$noteArray)"
    }


}