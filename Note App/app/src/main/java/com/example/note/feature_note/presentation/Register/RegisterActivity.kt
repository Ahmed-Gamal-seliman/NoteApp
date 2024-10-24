package com.example.note.feature_note.presentation.Register

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.note.R
import com.example.note.databinding.ActivityRegisterBinding
import com.example.note.feature_note.data.model.User
import com.example.note.feature_note.presentation.AppViewModel
import com.example.note.feature_note.presentation.Login.LoginActivity
import java.util.Locale

class RegisterActivity : AppCompatActivity() {
    lateinit var binding:ActivityRegisterBinding
    lateinit var viewModel:AppViewModel

    var nameValidation:String?=null
    var emailValidation:String?=null
    var passwordValidation:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = AppViewModel(this.application)
        changeLogInColor()

        onRegisterButtonClicked()

    }

    private fun onRegisterButtonClicked() {
        binding.registerBtn.setOnClickListener{
            /* valdiate on textFileds */
            if(isValidTextFields())
            {
                /* check if the user is already Registered */
                var user: User? = viewModel.getUser(binding.fullNameEt.text.toString(),
                    binding.passwordEt.text.toString())


                if(!isRegisteredUser(user))
                {
                    Log.e("user","not registered")
                    /* User is not registered before then add the user in database*/
                    user= User(name=null,email=null,password=null)
                    addUserData(user)
                    Log.e("user name", user.name!!)
                    Log.e("user email",user.email!!)
                    Log.e("user password",user.password!!)
                    viewModel.insertUser(user)

                    navigateToLoginActivity()

                    finish()
                }
                else
                {
                    /* Alert Dialog User is already Registered*/
                    Toast.makeText(this@RegisterActivity,"User is already registered", Toast.LENGTH_SHORT).show()
                }
            }
            else
            {
                Log.e("in else","false")
                binding.emailLayoutEt.error= emailValidation
                binding.fullNameLayoutEt.error= nameValidation
                binding.passwordLayoutEt.error= passwordValidation

            }

            /* if text fields is not empty and the user is not added before then add the user in database */


        }
    }

    private fun isRegisteredUser(user: User?):Boolean {
        if(user == null)
            return false
        return (user.email?.toLowerCase(Locale.ROOT).equals(binding.emailEt.text.toString().toLowerCase(
            Locale.ROOT))
                && user.password?.toLowerCase(Locale.ROOT).equals(binding.passwordEt.text.toString().toLowerCase(
            Locale.ROOT)))

    }

    private fun navigateToLoginActivity() {
        val intent= Intent(this@RegisterActivity,LoginActivity::class.java)
        startActivity(intent)
    }

    private fun addUserData(user: User?) {
        user?.email = binding.emailEt.text.toString()
        user?.name = binding.fullNameEt.text.toString()
        user?.password = binding.passwordEt.text.toString()
    }

    private fun isValidTextFields(): Boolean {
        nameValidation= validateTextFields(binding.fullNameEt.text.toString())
        emailValidation=validateTextFields(binding.emailEt.text.toString())
        passwordValidation=validateTextFields(binding.passwordEt.text.toString())



        if((nameValidation!=null) || (emailValidation!=null) || (passwordValidation!=null))
            return false


        return true
    }

    private fun validateTextFields(text: String):String? {
            if(text.isNotEmpty())
                return null
            return "Required"
    }

    private fun changeLogInColor() {
        val text = binding.loginText.text.toString()
        val spannableString: SpannableString = SpannableString(text);

        // Find where "Log In" starts and ends
        val start:Int = text.indexOf("Log In");
        val end:Int = start + "Log In".length;

        // Set the color of "Log In" to blue
        spannableString.setSpan(ForegroundColorSpan(Color.BLUE), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set the spannable string to the TextView
        binding.loginText.text = spannableString
    }
}