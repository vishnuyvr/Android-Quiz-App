package com.fullerton.fz.cs411.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SelectQuizActivity : AppCompatActivity(), SelectQuizAdapter.rvEvent {
    var listAllQuiz=ArrayList<QuizInfoModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_quiz)

        val rvQuiz=findViewById<RecyclerView>(R.id.quizrecyclerView)
        val listQuiz= resources.getStringArray(R.array.quiz_collection_names)

        for(i in listQuiz){
           var qmodel= QuizInfoModel(i)
            listAllQuiz.add(qmodel)
        }
        rvQuiz.layoutManager=LinearLayoutManager(this)
        val qadapter = SelectQuizAdapter(listAllQuiz, this)
        rvQuiz.adapter=qadapter


    }

    override fun onClicked(position: Int) {
        val quizChosen = listAllQuiz[position]
        val toStart = quizChosen.quizName
        if(toStart != null){
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("chosenQuizName", toStart)
            Toast.makeText(this, toStart, Toast.LENGTH_LONG).show()
            startActivity(intent)
        }
        else{
            Toast.makeText(this, "Quiz unable to be loaded.", Toast.LENGTH_LONG).show()

        }
    }
}
