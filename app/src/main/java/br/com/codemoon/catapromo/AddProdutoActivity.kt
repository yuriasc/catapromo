package br.com.codemoon.catapromo

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.text.Editable
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class AddProdutoActivity : AppCompatActivity() {

    private lateinit var produto: EditText
    private lateinit var valor: EditText
    private lateinit var btnSalvar: Button
    private lateinit var locationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_produto)

        this.produto = findViewById(R.id.produto)
        this.valor = findViewById(R.id.valor)
        this.btnSalvar = findViewById(R.id.btnSalvar)

        this.btnSalvar.setOnClickListener({salvar()})
    }

    fun salvar() {

        val queue = Volley.newRequestQueue(this)
        val url = "http://www.codemoon.com.br/catapromo/produto/store"

        val nome = this.produto.text.toString()
        val valor = this.valor.text.toString()

        if (TextUtils.isEmpty(nome) || TextUtils.isEmpty(valor)) {
            Toast.makeText(this,"Produto e Valor Obrigatórios", Toast.LENGTH_LONG).show()
            return
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            val toast = Toast.makeText(applicationContext, "no permission", Toast.LENGTH_LONG)
            toast.show()
            return
        }

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this)
        val provider = locationManager.getBestProvider(Criteria(), true)

        val location = locationManager.getLastKnownLocation(provider)
        val latitude = location.latitude
        val longitude = location.longitude

        val db = DataBaseHandler(this)
        val email = db.getEmailUsuario()
        val usuario = db.getNomeUsuario()

        val params = HashMap<String, String>()
        params.put("nome", nome)
        params.put("valor", valor)
        params.put("usuario", usuario)
        params.put("email", email)
        params.put("latitude", latitude.toString())
        params.put("longitude", longitude.toString())
        val parameters = JSONObject(params)

        println("PARAMS: $parameters")

        val stringResult = JsonObjectRequest(Request.Method.POST, url, parameters, Response.Listener<JSONObject> {
            response ->

            val produto = response.getJSONObject("produto")
            if(produto != null) {
                this.produto.setText("")
                this.valor.setText("")
                Toast.makeText(this, "Produto Cadastrado", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Produto não Cadastrado", Toast.LENGTH_LONG).show()
            }

        }, Response.ErrorListener {
            Toast.makeText(this, "Erro ao Cadastrar Produto", Toast.LENGTH_LONG).show()
        })

        queue.add(stringResult)

    }
}

private fun LocationManager.requestLocationUpdates(gpS_PROVIDER: String, i: Int, i1: Int, addProdutoActivity: AddProdutoActivity) {

}
