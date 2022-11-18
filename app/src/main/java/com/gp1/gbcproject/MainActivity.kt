package com.gp1.gbcproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gp1.gbcproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tempgo.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        if(p0!=null){
            when(p0.id){
                binding.tempgo.id -> gotolist()
            }
        }
    }

    private fun gotolist() {
        var intent = Intent(this, LessonList::class.java)
        startActivity(intent)
    }
}