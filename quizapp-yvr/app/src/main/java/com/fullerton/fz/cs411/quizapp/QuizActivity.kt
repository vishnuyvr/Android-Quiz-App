package com.fullerton.fz.cs411.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.fullerton.fz.cs411.quizapp.databinding.ActivityQuizBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding

    private lateinit var list: ArrayList<QuestionModel>
    private lateinit var intent:Intent
    private lateinit var chosenQuiz:String

    private var count:Int = 0
    private var score = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //To get what quiz was chosen
        intent=getIntent()
        chosenQuiz=intent.getStringExtra("chosenQuizName")!!

        binding = ActivityQuizBinding.inflate(layoutInflater)
//        setContentView(R.layout.activity_quiz)
        setContentView(binding.root)
        list = ArrayList<QuestionModel>()

        Firebase.firestore.collection(chosenQuiz)
            .get().addOnSuccessListener {
                doct ->
                list.clear()
                for (i in doct.documents){

                    var questionModel = i.toObject(QuestionModel::class.java)
                    list.add(questionModel!!)

                }

                if(list.size>0){
                    binding.question.setText(list.get(0).question)
                    binding.option1.setText(list.get(0).option1)
                    binding.option2.setText(list.get(0).option2)
                    binding.option3.setText(list.get(0).option3)
                    binding.option4.setText(list.get(0).option4)
                }
                else{
                    Toast.makeText(this, "Quiz does not exist.", Toast.LENGTH_LONG).show()
                }

            }

//        list.add(QuestionModel("Who is the President of United States", "Joe Biden", "Ojash Shrestha", "Frank", "Jenny" , "Joe Biden"))
//
//        list.add(QuestionModel("Who is the President of United States", "Ojash Shrestha", "Joe Biden", "Frank", "Jenny" , "Joe Biden"))
//        list.add(QuestionModel("Who is the President of United States", "Joe Biden", "Ojash Shrestha", "Frank", "Jenny" , "Joe Biden"))
//        list.add(QuestionModel("Who is the President of United States", "Ojash Shrestha", "Joe Biden", "Frank", "Jenny" , "Joe Biden"))

//        if(list.size>0){
//            binding.question.setText(list.get(0).question)
//            binding.option1.setText(list.get(0).option1)
//            binding.option2.setText(list.get(0).option2)
//            binding.option3.setText(list.get(0).option3)
//            binding.option4.setText(list.get(0).option4)
//        }


        binding.option1.setOnClickListener {
           nextData(binding.option1.text.toString())
        }

        binding.option2.setOnClickListener {
            nextData(binding.option2.text.toString())
        }
        binding.option3.setOnClickListener {
            nextData(binding.option3.text.toString())
        }
        binding.option4.setOnClickListener {
            nextData(binding.option4.text.toString())
        }




    }

    private fun nextData(i: String) {

        if(count < list.size){
            if(list.get(count).ans.equals(i)) {
                score++
            }

        }



        count++

        if (count>=list.size){
//            Toast.makeText(this@QuizActivity, score.toString(), Toast.LENGTH_LONG).show()
            val intent= Intent(this, ScoreActivity::class.java)
            intent.putExtra("SCORE", score)
            startActivity(intent)
            finish()
        }
        else{
            binding.question.setText(list.get(count).question)
            binding.option1.setText(list.get(count).option1)
            binding.option2.setText(list.get(count).option2)
            binding.option3.setText(list.get(count).option3)
            binding.option4.setText(list.get(count).option4)

        }

    }
}