package com.fullerton.fz.cs411.quizapp

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class SelectQuizAdapter(private val qList: ArrayList<QuizInfoModel>, private val listener: rvEvent)
    : RecyclerView.Adapter<SelectQuizAdapter.QuizViewHolder>() {
    private var onClickListener:OnClickListener?=null


    inner class QuizViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
        var quizName: TextView = itemView.findViewById(R.id.tvQuizName)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if( position != RecyclerView.NO_POSITION){
                listener.onClicked(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_card,parent, false)
        return QuizViewHolder(view)
    }
    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.quizName.text=qList[position].quizName
    }

    override fun getItemCount(): Int {
        return qList.size
    }
    interface rvEvent{
        fun onClicked(position: Int)
    }
}