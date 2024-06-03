package com.example.quizapp

import android.content.IntentSender.OnFinished
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.quizapp.databinding.ActivityQuizBinding
import com.example.quizapp.databinding.ScoreDialogBinding
import kotlinx.coroutines.selects.select

class QuizActivity : AppCompatActivity(),View.OnClickListener {

    companion object{
        var questionmodelList:List<QuestionModel> = listOf()
        var time:String=""
    }

    lateinit var binding:ActivityQuizBinding

    var currentQuestionIndex=0;
    var SelectedAnswer=""
    var score=0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            btn0.setOnClickListener(this@QuizActivity)
            btn1.setOnClickListener(this@QuizActivity)
            btn2.setOnClickListener(this@QuizActivity)
            btn3.setOnClickListener(this@QuizActivity)
            nextBtn.setOnClickListener(this@QuizActivity)
        }
        loadQuestions()
        startTimer()
    }

    private fun startTimer(){
        val totalTimeinMillis=time.toInt()*60*1000L
        object :CountDownTimer(totalTimeinMillis,1000L){
            override fun onTick(millsUntillFinished: Long) {
                val seconds=millsUntillFinished/1000
                val minuites=seconds/60
                val remainingSeconds=seconds%60
                binding.timerIndicaterTextview.text=String.format("%02d:%02d",minuites,remainingSeconds)

            }
            override fun onFinish() {
                TODO("Not yet implemented")
            }
        }.start()
    }

    private fun loadQuestions(){
         SelectedAnswer=""
        if (currentQuestionIndex== questionmodelList.size){
            finishQuiz()
            return
        }
        binding.apply {
            questionIndicagtorTextview.text="Question  ${currentQuestionIndex+1}/ ${questionmodelList.size}"
            questionProgressIndicator.progress=(currentQuestionIndex.toFloat()/ questionmodelList.size.toFloat()*100).toInt()
            questionTextview.text= questionmodelList[currentQuestionIndex].question
            btn0.text= questionmodelList[currentQuestionIndex].options[0]
            btn1.text= questionmodelList[currentQuestionIndex].options[1]
            btn2.text= questionmodelList[currentQuestionIndex].options[2]
            btn3.text= questionmodelList[currentQuestionIndex].options[3]
        }

    }

    override fun onClick(view: View?) {

        binding.apply {
            btn0.setBackgroundColor(getColor(R.color.orange))
            btn1.setBackgroundColor(getColor(R.color.orange))
            btn2.setBackgroundColor(getColor(R.color.orange))
            btn3.setBackgroundColor(getColor(R.color.orange))

        }

        val clickdBtn=view as Button

        if (clickdBtn.id==R.id.next_btn){

            if (SelectedAnswer.isEmpty()){
                Toast.makeText(applicationContext,"Select an Option to continue",Toast.LENGTH_SHORT).show()
                return;
            }

            if (SelectedAnswer== questionmodelList[currentQuestionIndex].correct){
                score++
                Log.i("Scror of quiz",score.toString())
            }
            currentQuestionIndex++
            loadQuestions()
        }else{

            SelectedAnswer=clickdBtn.text.toString()

            clickdBtn.setBackgroundColor(getColor(R.color.grey))

        }

    }
    private fun finishQuiz(){
        val totalQuestion= questionmodelList.size
        val percentage= ((score.toFloat()/totalQuestion.toFloat())*100).toInt()

        val dialogBinding=ScoreDialogBinding.inflate(layoutInflater)
        dialogBinding.apply {
            scoreProgressIndicator.progress=percentage
            scoreProgrssText.text="$percentage %"
            if (percentage>60){
                scoreTitle.text="Congrats! you have passed"
                scoreTitle.setTextColor(Color.BLUE)
            }else{
                scoreTitle.text="Oops! you have failed"
                scoreTitle.setTextColor(Color.RED)
            }
            scoreSubtitle.text="$score out of $totalQuestion are correct"
            finishBtn.setOnClickListener{
                finish()
            }
        }

        AlertDialog.Builder(this).setView(dialogBinding.root).setCancelable(false).show()


    }
}