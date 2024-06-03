package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.quizapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var binding:ActivityLoginBinding
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        firebaseAuth= FirebaseAuth.getInstance()
        setContentView(binding.root)
        binding.textView.setOnClickListener{
            val intent=Intent(this,SignupActivity::class.java)
            startActivity(intent)
        }
        binding.button.setOnClickListener{
            val email=binding.emailEt.text.toString()
            val pass=binding.passET.text.toString()

            if (email.isNotEmpty()&& pass.isNotEmpty()){

                firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
                    if (it.isSuccessful){
                        val intent=Intent(this,MainActivity::class.java)
                        startActivity(intent)

                    }else{
                        Toast.makeText(this,"password dose not Match!",Toast.LENGTH_SHORT).show()

                    }
                }

            }else{
                Toast.makeText(this,"all the field has to filled ",Toast.LENGTH_SHORT).show()
            }

        }
    }
}