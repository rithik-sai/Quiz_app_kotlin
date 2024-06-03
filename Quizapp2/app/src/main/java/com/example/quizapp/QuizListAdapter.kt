package com.example.quizapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.databinding.QuizItemRecyclerRowBinding

class QuizListAdapter(private val  quizModel: List<QuizModel>):
    RecyclerView.Adapter<QuizListAdapter.MyviewHolder>() {



    class MyviewHolder(private val binding: QuizItemRecyclerRowBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(model: QuizModel){
            //bind all items
            binding.apply {
                quizTitleText.text=model.title
                quizSubtitleText.text=model.subtitle
                qiuzTimeText.text=model.time +" mins"
                root.setOnClickListener {
                    val Intent=Intent(root.context,QuizActivity::class.java)
                    QuizActivity.questionmodelList=model.questionList
                    QuizActivity.time=model.time
                    root.context.startActivity(Intent)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        val binding=QuizItemRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyviewHolder(binding)
    }

    override fun getItemCount(): Int {
return quizModel.size
    }

    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        holder.bind(quizModel[position])
    }
}