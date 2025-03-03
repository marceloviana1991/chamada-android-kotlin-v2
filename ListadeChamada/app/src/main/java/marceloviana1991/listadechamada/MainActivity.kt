package marceloviana1991.listadechamada

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import marceloviana1991.listadechamada.data.InternoRepository
import marceloviana1991.listadechamada.databinding.ActivityMainBinding
import marceloviana1991.listadechamada.model.Interno

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var repository: InternoRepository
    private val adapter by lazy {
        InternoListAdapter(this, repository.getInternos())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        repository = InternoRepository(this)

        val recyclerView = binding.recyclerView

        recyclerView.adapter = adapter

        adapter.quandoExclui = { id ->
            repository.deleteInterno(id)
            adapter.atualiza(repository.getInternos())
        }

        binding.buttonFinalizar.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Finalizar chamada")
                .setMessage("Deseja realmente finalizar a chamada?")
                .setPositiveButton("CONFIRMAR") { _, _ ->
                    val listaFaltas = adapter.finaliza()
                    listaFaltas.forEach {
                        var interno = repository.get(it.id)
                        interno.faltas++
                        repository.updateInterno(interno)
                    }
                    adapter.atualiza(repository.getInternos())
                    Toast.makeText(this, "Chamada realizada com sucesso!", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("CANCELAR") { _, _ ->
                }
                .show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_adicionar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_adicionar -> {
                val editText = EditText(this)
                AlertDialog.Builder(this)
                    .setTitle("Adicionar interno")
                    .setMessage("Informe o nome do interno que deseja cadastrar")
                    .setView(editText)
                    .setPositiveButton("CONFIRMAR") { _, _ ->
                        if (editText.text.toString().isNotBlank()) {
                            repository.addInterno(Interno(
                                nome=editText.text.toString(), faltas=0))
                            adapter.atualiza(repository.getInternos())
                        } else {
                            Toast.makeText(
                                this,
                                "Insira o nome que deseja cadastrar",
                                Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                    .setNegativeButton("CANCELAR") { _, _ ->

                    }
                    .show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}