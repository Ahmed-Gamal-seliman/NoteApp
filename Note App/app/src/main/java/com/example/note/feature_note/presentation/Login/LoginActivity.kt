package com.example.note.feature_note.presentation.Login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.note.R
import com.example.note.databinding.ActivityLoginBinding
import com.example.note.feature_note.data.model.User
import com.example.note.feature_note.presentation.AppViewModel
import com.example.note.feature_note.presentation.Constants.USER_KEY
import com.example.note.feature_note.presentation.Register.RegisterActivity
import com.example.note.feature_note.presentation.notes.components.NotesActivity
import java.util.Locale

class LoginActivity : AppCompatActivity() {
    lateinit var binding:ActivityLoginBinding
    lateinit var viewModel:AppViewModel
    var emailValidation:String?= null
    var passwordValidation:String?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = AppViewModel(this.application)

        changeRegisterColor()
        onRegisterNowClicked()
        onLoginButtonClicked()

    }


    private fun isUserFound(user:User?): Boolean {
        Log.e("valid user email",user?.email?.toLowerCase().toString())
        Log.e("valid user et email",binding.emailLoginEt.text.toString())
        Log.e("valid user  password",user?.password?.toLowerCase().toString())
        Log.e("valid user et password",binding.passwordLoginEt.text.toString())
            if(user?.email?.toLowerCase(Locale.ROOT).equals(binding.emailLoginEt.text.toString().toLowerCase(
                    Locale.ROOT))
                &&
                user?.password?.toLowerCase(Locale.ROOT).equals(binding.passwordLoginEt.text.toString().toLowerCase(Locale.ROOT))
                )
                return true

        return false
    }

    private fun onLoginButtonClicked() {
        binding.loginBtn.setOnClickListener{

            /* Validate Text Fields */
            if(isValidTextFields()) {
                val user: User? = viewModel.getUser(
                    binding.emailLoginEt.text.toString(),
                    binding.passwordLoginEt.text.toString()
                )

                if (isUserFound(user))
                {
                    navigateToNotesActivity(user)
                    finish()
                }



            }
            else
            {
                binding.emailLoginEt.error= emailValidation
                binding.passwordLoginEt.error= passwordValidation
            }
        }
    }

    private fun navigateToNotesActivity(user:User?) {
        val intent=Intent(this@LoginActivity,NotesActivity::class.java)
        intent.putExtra(USER_KEY,user)
        startActivity(intent)

    }

    private fun isValidTextFields(): Boolean {
        emailValidation= validateTextFields(binding.emailLoginEt.text.toString())
        passwordValidation= validateTextFields(binding.passwordLoginEt.text.toString())

        if((emailValidation!=null) && (passwordValidation!=null))
            return false
        return true

    }

    private fun validateTextFields(text: String): String? {
            if(text.isNotEmpty())
                return null
        return "Required"

    }

    private fun onRegisterNowClicked() {
        binding.registerText.setOnClickListener{
            navigateToRegisterActivity()
            finish()

        }
    }

    private fun navigateToRegisterActivity() {
        val intent = Intent(this@LoginActivity,RegisterActivity::class.java)
        startActivity(intent)
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