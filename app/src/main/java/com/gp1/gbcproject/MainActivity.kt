package com.gp1.gbcproject

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gp1.gbcproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityMainBinding
    lateinit var sharedPref: SharedPreferences
    lateinit var dataSource: DataSource
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tempgo.setOnClickListener(this)
        binding.tempclr.setOnClickListener(this)
        sharedPref = this.getSharedPreferences("com_gp1_gbcproject_PREF",MODE_PRIVATE)
        dataSource = DataSource.getInstance()
        setdummyuserdata()
    }

    override fun onClick(p0: View?) {
        if(p0!=null){
            when(p0.id){
                binding.tempgo.id -> gotolist()
                binding.tempclr.id -> clr()
            }
        }
    }

    private fun clr() {
        val username = dataSource.username!!.lowercase()
        with(sharedPref.edit()){
            remove("${username}_lesson_progress")

            for(each in dataSource.arrayoflesson){
                remove("${username}_lesson${each.lessonid}_note")
            }
            commit()
        }
        dataSource.finishedlessonset.removeAll(dataSource.finishedlessonset)
    }

    private fun gotolist() {
        var intent = Intent(this, LessonList::class.java)
        startActivity(intent)
    }

    private fun setdummyuserdata (){
        dataSource.username = "Ming"
    }
}