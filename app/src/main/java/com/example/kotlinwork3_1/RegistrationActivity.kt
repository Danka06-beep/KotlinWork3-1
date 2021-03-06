package com.example.kotlinwork3_1

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.activity_registration.btnRegistration
import kotlinx.coroutines.launch

class RegistrationActivity : AppCompatActivity() {
    private var dialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        btnRegistration.setOnClickListener {
            lifecycleScope.launch {
                val login = registrationLogin.text?.toString().orEmpty()
                val password = registrationPassword.text?.toString().orEmpty()
                val repeatPassword = repeatPassword.text?.toString().orEmpty()
                if(password == ""){
                    Toast.makeText(this@RegistrationActivity, getString(R.string.password), Toast.LENGTH_LONG).show()
                    Log.d("MyLog","Отработан пароль ")
                }
                if(login == ""){
                    Toast.makeText(this@RegistrationActivity, getString(R.string.login), Toast.LENGTH_LONG).show()
                    Log.d("MyLog","Отработан логин ")
                }
                if(repeatPassword == ""){
                    Toast.makeText(this@RegistrationActivity, getString(R.string.repeatPassword), Toast.LENGTH_LONG).show()
                    Log.d("MyLog","Отработан повтор пароля ")
                }
                when {
                    !isValidUsername(registrationLogin.text.toString()) -> {
                        Toast.makeText(this@RegistrationActivity, getString(R.string.Invalidlogin), Toast.LENGTH_LONG).show()
                    }
                    !isValidPassword(registrationPassword.text.toString()) -> {
                        Toast.makeText(this@RegistrationActivity, getString(R.string.InvalidPassword), Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        dialog = ProgressDialog(this@RegistrationActivity).apply {
                            Toast.makeText(this@RegistrationActivity, getString(R.string.loadData), Toast.LENGTH_LONG).show()
                            setCancelable(false)
                        }
                        try {
                            dialog?.dismiss()
                            val request = Repository.register(login, password)
                            if(request.errorBody() != null){
                                Toast.makeText(this@RegistrationActivity, request.errorBody()!!.string() , Toast.LENGTH_LONG).show()
                            }else{
                                dialog?.dismiss()
                                val intent = Intent(this@RegistrationActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(this@RegistrationActivity, getString(R.string.erorConnect), Toast.LENGTH_LONG).show()
                            dialog?.dismiss()
                        }

                    }
                }
            }
        }

    }

}