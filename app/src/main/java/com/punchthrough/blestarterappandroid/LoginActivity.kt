package com.punchthrough.blestarterappandroid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.punchthrough.blestarterappandroid.R

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)

        val sharedPref = getSharedPreferences("user_credentials", Context.MODE_PRIVATE)

        btnLogin.setOnClickListener {
            val username = etEmail.text.toString()
            val password = etPassword.text.toString()

            val savedPass = sharedPref.getString(username, null)
            if (savedPass == password) {
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))  // Cambia a tu actividad BLE
                finish()
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }

        btnRegister.setOnClickListener {
            val username = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                if (sharedPref.contains(username)) {
                    Toast.makeText(this, "Usuario ya registrado", Toast.LENGTH_SHORT).show()
                } else {
                    sharedPref.edit().putString(username, password).apply()
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Complete ambos campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
