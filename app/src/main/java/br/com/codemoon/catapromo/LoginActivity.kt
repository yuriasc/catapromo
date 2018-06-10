package br.com.codemoon.catapromo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class LoginActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var senha: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        this.email = findViewById(R.id.email)
        this.senha = findViewById(R.id.senha)
        this.btnLogin = findViewById(R.id.btnLogin)

        this.btnLogin.setOnClickListener({
            login()
        })
    }

    fun login() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
