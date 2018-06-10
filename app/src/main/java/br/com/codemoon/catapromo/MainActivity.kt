package br.com.codemoon.catapromo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var btnMapa: Button
    private lateinit var btnAddProduto: Button
    private lateinit var btnSobre: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.btnMapa = findViewById(R.id.btnMapa)
        this.btnAddProduto = findViewById(R.id.btnAddProduto)
        this.btnSobre = findViewById(R.id.btnSobre)

        this.btnMapa.setOnClickListener({
            mapa()
        })

        this.btnAddProduto.setOnClickListener({
            addProduto()
        })

        this.btnSobre.setOnClickListener({
            sobre()
        })

    }

    fun mapa() {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }

    fun addProduto() {
        val intent = Intent(this, AddProdutoActivity::class.java)
        startActivity(intent)
    }

    fun sobre() {
        val intent = Intent(this, SobreActivity::class.java)
        startActivity(intent)
    }
}
