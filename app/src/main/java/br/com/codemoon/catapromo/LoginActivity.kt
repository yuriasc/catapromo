package br.com.codemoon.catapromo

import android.app.DownloadManager
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

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

        val queue = Volley.newRequestQueue(this)
        val url = "http://www.codemoon.com.br/catapromo/usuario/login"

        val email = this.email.text.toString()
        val senha = this.senha.text.toString()

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this,"Email e Senhas obrigatórios", Toast.LENGTH_LONG).show()
            return
        }

        val params = HashMap<String, String>()
        params.put("email", email)
        params.put("senha", senha)
        val parameters = JSONObject(params)

        val stringResult = JsonObjectRequest(Request.Method.POST, url, parameters, Response.Listener<JSONObject> {
            response ->

            val usuario = response.getJSONObject("usuario")
            if(usuario != null) {
                verificarUsuario(usuario)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Email ou senha Inválidos", Toast.LENGTH_LONG).show()
            }

        }, Response.ErrorListener {
            Toast.makeText(this, "Email ou senha Inválidos", Toast.LENGTH_LONG).show()
        })

        queue.add(stringResult)
    }

    fun verificarUsuario(user: JSONObject) {

        val nome: String = user.get("nome").toString()
        val email: String = user.get("email").toString()
        val longitude: String = user.get("longitude").toString()
        val latitude: String = user.get("latitude").toString()

        val usuario = User(nome, email, longitude, latitude)

        val db = DataBaseHandler(this)
        db.verificarUsuario(usuario)

        //progressBar.visibility = View.GONE
    }
}
