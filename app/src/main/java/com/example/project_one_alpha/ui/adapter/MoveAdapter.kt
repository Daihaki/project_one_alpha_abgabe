package com.example.project_one_alpha.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_one_alpha.databinding.AttackItemBinding
import com.example.project_one_alpha.db.datamodel.MoveType
import com.example.project_one_alpha.db.datamodel.PokemonMove

class MoveAdapter (
   private var moveList: List<MoveType>,
) : RecyclerView.Adapter<MoveAdapter.AttackViewHolder>() {

    inner class AttackViewHolder(val binding: AttackItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttackViewHolder {
        val binding = AttackItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AttackViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return moveList.size
    }

    fun setItems(moveList: List<MoveType>) {
        this.moveList = moveList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: AttackViewHolder, position: Int) {
        val move = moveList[position]
        holder.binding.attackNameTV.text = move.name
        holder.binding.attackTypeTV.text = move.type?.name
        holder.binding.attackTypeTV2.text = move.power?.toString() ?: "0"
    }
}
