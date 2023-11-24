package com.example.mymbti_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2

class TestActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    val QuestionnaireResults = QuestionnaireResults()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = ViewPagerAdapter(this)
        viewPager.isUserInputEnabled = false //화면을 터치로 움직이게 하는거 막기
    }

    fun moveToNextQuestion(){
        if(viewPager.currentItem==3){
            //마지막페이지 결과화면으로 이동
            val intent = Intent(this, ResultActivity::class.java)
            intent.putIntegerArrayListExtra("results", ArrayList(QuestionnaireResults.results))
            startActivity(intent)
        }else{
            val nextItem = viewPager.currentItem +1
            if(nextItem < viewPager.adapter?.itemCount ?: 0){
                viewPager.setCurrentItem(nextItem, true)
            }
        }
    }
}

class QuestionnaireResults{
    val results = mutableListOf<Int>()

    fun addResponses(response: List<Int>){
        val mostFrequent = response.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
        mostFrequent?.let { results.add(it) }
    }

}