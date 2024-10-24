package com.example.note.feature_note.presentation.Login

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.note.R
import com.example.note.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        changeRegisterColor()
    }

    private fun changeRegisterColor() {
        val text = binding.registerText.text.toString()
        val spannableString: SpannableString = SpannableString(text);

        // Find where "Log In" starts and ends
        val start:Int = text.indexOf("Register now");
        val end:Int = start + "Register now".length;

        // Set the color of "Log In" to blue
        spannableString.setSpan(ForegroundColorSpan(Color.BLUE), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set the spannable string to the TextView
        binding.registerText.text = spannableString
    }
}