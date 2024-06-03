package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.databinding.ActivityMainBinding
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var quizModel: MutableList<QuizModel>
    lateinit var adapter: QuizListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizModel= mutableListOf()
        getDatatFromFirebase()

    }

    private fun setupRecyclerView(){
        binding.progressBar.visibility=View.GONE

        adapter= QuizListAdapter(quizModel)
        binding.recyclerView.layoutManager=LinearLayoutManager(this)
        binding.recyclerView.adapter=adapter
    }

    private fun getDatatFromFirebase(){
binding.progressBar.visibility=View.VISIBLE
       FirebaseDatabase.getInstance().reference.get()
           .addOnSuccessListener { datasnapshot->
               if (datasnapshot.exists()){
                   for (snapshot in datasnapshot.children){
                       val quizModellist=snapshot.getValue(QuizModel::class.java)
                       if (quizModellist !=null){
                           quizModel.add(quizModellist)
                       }
                   }
               }
               setupRecyclerView()
           }



    }
}