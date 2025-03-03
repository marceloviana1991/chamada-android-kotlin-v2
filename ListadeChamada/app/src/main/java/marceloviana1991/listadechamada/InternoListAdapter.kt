package marceloviana1991.listadechamada

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.recyclerview.widget.RecyclerView
import marceloviana1991.listadechamada.data.InternoRepository
import marceloviana1991.listadechamada.databinding.RowInternoListBinding
import marceloviana1991.listadechamada.model.Interno

class InternoListAdapter(
    private val context: Context,
    listaInternosRecebida: List<Interno> = emptyList(),
    var quandoExclui: (id: Int) -> Unit = {}
): RecyclerView.Adapter<InternoListAdapter.InternoViewHolder>() {

    val listaInternos = listaInternosRecebida.toMutableList()
    val listaFaltas = listaInternos.toMutableList()

    class InternoViewHolder(binding: RowInternoListBinding) : RecyclerView.ViewHolder(binding.root) {
        val textView: TextView = binding.textView
        val textViewFaltas: TextView = binding.textViewFaltas
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        val switch: Switch = binding.switchPresenca
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InternoViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = RowInternoListBinding.inflate(inflater, parent, false)
        return InternoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listaInternos.size
    }

    override fun onBindViewHolder(holder: InternoViewHolder, position: Int) {
        val interno = listaInternos[position]
        holder.textView.setText(interno.nome)
        holder.textViewFaltas.setText("Faltas: ${interno.faltas}")
        holder.switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                listaFaltas.remove(interno)
            } else {
                if (!listaFaltas.contains(interno)) {
                    listaFaltas.add(interno)
                }
            }
        }
        holder.switch.isChecked = false
        holder.itemView.setOnLongClickListener {
            AlertDialog.Builder(context)
                .setTitle("Excluir interno")
                .setMessage("Deseja excluir o interno ${interno.nome}")
                .setPositiveButton("CONFIRMAR") { _, _ ->
                    listaInternos.remove(interno)
                    quandoExclui(interno.id)
                }
                .setNegativeButton("CANCELAR") { _, _ ->

                }
                .show()
            true
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun finaliza(): List<Interno> {
        val listaFaltasEnviada = listaFaltas.toMutableList()
        this.listaFaltas.clear()
        this.listaFaltas.addAll(listaInternos)
        notifyDataSetChanged()
        return listaFaltasEnviada
    }

    @SuppressLint("NotifyDataSetChanged")
    fun atualiza(internos: List<Interno>) {
        this.listaInternos.clear()
        this.listaInternos.addAll(internos)
        this.listaFaltas.clear()
        this.listaFaltas.addAll(internos)
        notifyDataSetChanged()
    }
}